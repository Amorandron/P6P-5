package com.janwilts.bigmovie.parser.parsers;

import com.janwilts.bigmovie.parser.util.RomanNumeral;

import java.io.File;
import java.io.PrintWriter;

/**
 * @author Jan
 */
public class MovieParser extends Parser {
    
    public MovieParser(File file) {
        super(file);
    }
    
    @Override
    public void parse() {
        int linesBeforeList = 2;
        boolean foundList = false;
        
        try (PrintWriter writer = new PrintWriter(this.csv, "UTF-8")) {
            for (String line; (line = this.readLine()) != null; ) {
                if (!foundList && line.equals("===========")) foundList = true;
                if (foundList && linesBeforeList > 0) linesBeforeList--;
                else if (foundList && line.equals("")) return;
                else if (linesBeforeList == 0 && line.length() > 0) {
                    if (line.startsWith("-----------------")) break;
                    if (line.charAt(0) == QUOTE_CHAR) continue;
                    if (line.contains("{{SUSPENDED}}")) continue;
                    
                    String year = line.substring(line.lastIndexOf('(') + 1, line.lastIndexOf(')'));
                    String title = line.substring(0, line.lastIndexOf('(') - 1);
                    String type = "";
                    int occurrence = 0;
                    
                    if (year.charAt(0) == 'T' || year.charAt(0) == 'V') {
                        type = year;
                        line = line.substring(0, line.lastIndexOf('('));
                        title = line.substring(0, line.lastIndexOf('(') - 1);
                        year = line.substring(line.lastIndexOf('(') + 1, line.lastIndexOf(')'));
                        if(type.contains("VG"))
                            continue;
                    }
                    
                    if (year.contains("/")) {
                        occurrence = RomanNumeral.convert(year.substring(year.indexOf("/") + 1, year.length()));
                        year = year.substring(0, year.indexOf("/"));
                    }
                    
                    if (year.equals("????")) year = "";
                    
                    writer.println(String.join(DELIMITER, addQuotes(title.replace(QUOTE, DOUBLE_QUOTE)), year, String.format("%s%s%s", QUOTE, type, QUOTE), occurrence + ""));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
