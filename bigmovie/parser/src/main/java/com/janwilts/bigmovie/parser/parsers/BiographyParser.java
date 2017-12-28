package com.janwilts.bigmovie.parser.parsers;

import com.janwilts.bigmovie.parser.util.RomanNumeral;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * @author Yannick
 */
public class BiographyParser extends Parser{
    public BiographyParser(File file) {
        super(file);
    }

    @Override
    public void parse() {
        try(PrintWriter writer = new PrintWriter(this.csv, "UTF-8"))  {

            HashMap<String, String> terms = new HashMap<>();
            String[] staticTerms = new String[] {"NM", "OC", "NK", "HT", "BG", "BY", "SP", "TR", "RN", "OW", "QU", "DB",
                                                 "DD", "AT", "TM", "IT", "PT", "CV", "BO", "PI", "BT", "SA", "WN"};

            int linesBeforeList = 2;
            boolean foundList = false;
            boolean first = true;

            for(String line; (line = this.readLine()) != null; ) {
                if(!foundList && line.contains("BIOGRAPHY LIST")) {
                    foundList = true;
                }
                else if(foundList && linesBeforeList != 0) {
                    linesBeforeList--;
                }
                else if(foundList && line.length() > 0 && line.contains(":")) {
                    String term = line.substring(0, line.indexOf(':'));

                    if(term.equals("NM")) {
                        if(!first) {
                            for(String staticTerm : staticTerms) {
                                if(!staticTerm.equals("NM")) {
                                    writer.print(",");
                                }
                                writer.print("\"" + terms.get(staticTerm).replace("\"", "\"\"") + "\"");
                            }
                            writer.print("\n");
                        }
                        else {
                            first = false;
                        }

                        String currentName = line.substring(term.length() + 1);

                        String[] result = RomanNumeral.getFromActorName(currentName);

                        currentName = result[0];
                        String occurance = result[1];

                        terms = new HashMap<>();
                        terms.put("NM", currentName);
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
                    else {
                        if(terms.get(term).equals("")) {
                            terms.put(term, line.substring(term.length() + 1));
                        }
                        else {
                            terms.put(term, terms.get(term) + line.substring(term.length() + 1));
                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
