package com.janwilts.bigmovie.parser.parsers;

import com.janwilts.bigmovie.parser.util.RomanNumeral;

import java.io.File;
import java.io.PrintWriter;

/**
 * @author Jan
 */
public class RatingParser extends Parser {
    public RatingParser(File file) {
        super(file);
    }
    
    @Override
    public void parse() {
        int linesBeforeList = 3;
        boolean foundList = false;
        
        try (PrintWriter writer = new PrintWriter(this.csv, "UTF-8")) {
            for (String line; (line = this.readLine()) != null; ) {
                if (!foundList && line.contains("MOVIE RATINGS REPORT")) foundList = true;
                if (foundList && linesBeforeList > 0) linesBeforeList--;
                else if (foundList && line.equals("")) return;
                else if (linesBeforeList == 0 && line.length() > 0) {
                    line = line.substring(16);
                    line = line.trim();
                    
                    if (line.contains(QUOTE)) continue;
                    // Skips suspended movies
                    if (line.contains("{{SUSPENDED}}")) continue;
                    
                    if (line.endsWith("}")) line = line.substring(0, line.lastIndexOf("{"));
                    
                    String[] values = line.split("  ");
                    for (int i = 0; i < values.length; i++) values[i] = values[i].trim();
                    
                    int occurance = 0;
                    String type = "";
                    String title = "";
                    String yearString = "";
                    String year = "";

                    // Gets the type of the movie
                    if (values[2].charAt(values[2].lastIndexOf('(') + 1) == 'V' || values[2].charAt(values[2].lastIndexOf('(') + 1) == 'T') {
                        type = values[2].substring(values[2].lastIndexOf('(') + 1, values[2].lastIndexOf(')'));
                        values[2] = values[2].substring(0, values[2].lastIndexOf('(') - 1);
                        if(type.contains("VG"))
                            continue;
                    }

                    // Gets year
                    if (values[2].lastIndexOf('(') != -1) yearString = values[2].substring(values[2].lastIndexOf('(') + 1, values[2].length() - 1);
                    
                    try {
                        // Gets occurence
                        if (yearString.contains("/")) {
                            occurance = RomanNumeral.convert(values[2].substring(values[2].lastIndexOf('/') + 1, values[2].lastIndexOf(')')));
                            year = yearString.substring(0, yearString.indexOf("/"));
                        }
                        else if (!yearString.equals("????")) year = yearString;
                    }
                    catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                    
                    title = values[2].substring(0, values[2].lastIndexOf('(') - 1);
                    
                    writer.println(String.join(DELIMITER, addQuotes(title.replace(QUOTE, DOUBLE_QUOTE)), year, addQuotes(type), occurance + "", values[0], values[1]));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
