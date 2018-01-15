package com.janwilts.bigmovie.parser.parsers;

import com.janwilts.bigmovie.parser.util.RomanNumeral;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Yannick
 */
public class BiographyParser extends Parser {
    public static String[] STATIC_TERMS = new String[]{"NM", "OC", "NK", "HT", "BG", "BY", "SP", "TR", "RN", "OW", "QU", "DB", "DD", "AT", "TM", "IT", "PT", "CV", "BO", "PI", "BT", "SA", "WN"};
    
    public BiographyParser(File file) {
        super(file);
    }
    
    @Override
    public void parse() {
        try (PrintWriter writer = new PrintWriter(this.csv, "UTF-8")) {
            HashMap<String, String> terms = new HashMap<>();
            
            int linesBeforeList = 2;
            boolean foundList = false;
            boolean first = true;
            
            for (String line; (line = this.readLine()) != null; ) {
                if (!foundList && line.contains("BIOGRAPHY LIST")) foundList = true;
                else if (foundList && linesBeforeList != 0) linesBeforeList--;
                else if (foundList && line.length() > 0 && line.contains(":")) {
                    String term = line.substring(0, line.indexOf(':'));
                    
                    if (term.equals("NM")) {
                        if (!first) {
                            for (String staticTerm : STATIC_TERMS) {
                                if (!staticTerm.equals("NM")) writer.print(DELIMITER);
                                
                                writer.print(addQuotes(terms.get(staticTerm).replace(QUOTE, DOUBLE_QUOTE)));
                            }
                            writer.print(NEW_LINE);
                        }
                        else first = false;
                        
                        String currentName = line.substring(term.length() + 1);
                        
                        String[] result = RomanNumeral.getFromActorName(currentName);
                        
                        currentName = result[0];
                        String occurance = result[1];
                        
                        terms = new HashMap<>();
                        terms.put("NM", currentName.trim());
                        terms.put("OC", occurance);
                        terms.put("NK", "");
                        terms.put("HT", "");
                        terms.put("BG", "");
                        terms.put("BY", "");
                        terms.put("SP", "");
                        terms.put("TR", "");
                        terms.put("RN", "");
                        terms.put("OW", "");
                        terms.put("QU", "");
                        terms.put("DB", "");
                        terms.put("DD", "");
                        terms.put("AT", "");
                        terms.put("TM", "");
                        terms.put("IT", "");
                        terms.put("PT", "");
                        terms.put("CV", "");
                        terms.put("BO", "");
                        terms.put("PI", "");
                        terms.put("BT", "");
                        terms.put("SA", "");
                        terms.put("WN", "");
                    }
                    else if(term.equals("DB")) {
                        Pattern p = Pattern.compile("(\\d.*\\d{1,4}|\\d{1,4}|[?]{1,4})([B][C])?");
                        if(line.contains(","))
                            line = line.substring(term.length() + 1, line.indexOf(',')).trim();
                        else
                            line = line.substring(term.length() + 1);
                        Matcher m = p.matcher(line);

                        if(m.matches()) {
                            String date = m.group(1);

                            if (m.group(2) != null) {
                                if (!m.group(2).equals("")) {
                                    date = date.substring(0, date.lastIndexOf(" ")) + " -" + date.substring(date.lastIndexOf(" ") + 1);
                                }
                            }

                            if(date.length() <= 5)
                                date = "1 January " + date;

                            terms.put(term, date.trim());
                        }
                        else {
                            terms.put(term, "");
                        }
                    }
                    else {
                        if (terms.get(term).equals("")) terms.put(term, line.substring(term.length() + 1));
                        else terms.put(term, terms.get(term) + line.substring(term.length() + 1));
                    }
                    
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
