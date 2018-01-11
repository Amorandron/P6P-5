package com.janwilts.bigmovie.parser.parsers;

import com.janwilts.bigmovie.parser.util.RomanNumeral;

import java.io.File;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Sven Mark
 */
public class CountryParser extends Parser {
    public CountryParser(File file) {
        super(file);
    }
    
    @Override
    public void parse() {
        try (PrintWriter writer = new PrintWriter(this.csv, "UTF-8")) {
            //String pattern = "(.*?)\s\((.{4})|(|(.*?))\)(.*?)\s([a-zA-Z].*)";
            String pattern = "(.*?)\\s\\((\\d{4}|[?]{4})(|/(.*?))\\)(|\\s\\((.*?)\\))\\t(.*)";
            Pattern p = Pattern.compile(pattern);
            
            // Initialize variables
            String line;
            String movieName;
            String year;
            String iteration;
            String country;
            String category;
            Boolean foundList = false;
            
            // Amount of lines till first data entry
            int linesBeforeList = 1;
            
            while (((line = this.readLine()) != null)) {
                if (!foundList && line.equals("COUNTRIES LIST")) foundList = true;
                else if (foundList && linesBeforeList != 0) linesBeforeList--;
                else if (foundList && line.startsWith("---------")) return;
                
                else if (linesBeforeList == 0 && line.length() > 0) {
                    // Go the next line, if line contains a series
                    if (line.startsWith(QUOTE)) continue;
                    
                    Matcher m = p.matcher(line);
                    
                    if (m.matches()) {
                        // Set movie name
                        movieName = m.group(1);
                        
                        // Set year variable, if year is unknown return ""
                        if (m.group(2).contains("?")) year = "";
                        else year = m.group(2);
                        
                        // Convert Roman number to integer
                        if (m.group(4) == null) iteration = "0";
                        else iteration = Integer.toString(RomanNumeral.convert(m.group(4)));

                        if (m.group(6) == null) category = "";
                        else if (m.group(6).contains("TV")) category = "TV";
                        else if (m.group(6).contains("V")) category = "V";
                        else if (m.group(6).contains("VG")) category = "VG";
                        else {
                            category = "";
                        }

                        country = m.group(7).trim();
                        // Write all variables to a line in countries.csv
                        writer.println(String.join(DELIMITER, addQuotes(movieName), year, iteration, addQuotes(category),addQuotes(country)));
                    }
//                    else {
//                        System.out.println("Failed: line: " + line);
//                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}