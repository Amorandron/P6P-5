package com.janwilts.bigmovie.parser.parsers;

import com.janwilts.bigmovie.parser.util.RomanNumeral;

import java.io.File;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Everdien
 */
public class GenreParser extends Parser {
    public GenreParser(File file) {
        super(file);
    }
    
    @Override
    public void parse() {
        try (PrintWriter writer = new PrintWriter(this.csv, "UTF-8")) {
            //String pattern = "(.*?)\\s\\((.{4})(|\\/(.*?))\\)(.*?)\\s([a-zA-Z].*)";
            // fixed error:  (.*?)\s\(([[:digit:]]{4}|[?]{4})(|\/(.*?))\)(.*?)\s([a-zA-Z].*)
            // also takes (tv) etc:
            String pattern = "(.*?)\\s\\((\\d{4}|[?]{4})(|\\/(.*?))\\)\\s(|\\((.*?)\\))(.*?)\\s([a-zA-Z].*)";
            Pattern p = Pattern.compile(pattern);

            /*
            regex (series still included!): (.*?)\s\((\d{4}|[?]{4})(|\/(.*?))\)\s(|\((.*?)\))(.*?)\s([a-zA-Z].*)
            (.*?) --> (first group) matches any character (except for line terminators) --> title
            \s --> matches any whitespace character; de space after the title
            \( --> matches the '(' character; first character before the year
            (\d{4}|[?]{4}) --> (second group) matches any digit or '?' character exactly 4 times; --> year
            (|\/(.*?)) --> (third group) matches null or '/' character and (fourth group) matches any character --> romanNumber
            \) --> matches the ')' character; first character after the year
            \s --> matches the whitespace after the year
            (|\((.*?)\)) --> (fifth group) matches null or '(' and (sixth group) matches any character till ')' --> type (TV/V/VG)
            (.*?) --> (seventh group) matches any character; white space(and extra stuff like {{SUSPENDED}}) between year and genre
            \s --> matches any whitespace character; whitespace character before genre
            ([a-zA-Z].*) --> (eight group) match a character one or more times --> genre

            \1 --> title
            \2 --> year
            \4 --> romans number (only movies with same title as year, but different movies are)
            \6 --> type (TV/V/VG)
            \8 --> genre
             */
            
            Boolean foundList = false;
            String line;
            String currentTitle;
            String currentYear;
            String currentGenre;
            String currentRomanNumber;
            String currentType;
            
            while (((line = this.readLine()) != null)) {
                if (!foundList && line.equals("8: THE GENRES LIST")) foundList = true;
                if (foundList) {
                    // Go to next line if it's a show
                    if (line.startsWith(QUOTE)) continue;
                    
                    Matcher m = p.matcher(line);
                    
                    if (m.matches()) {
                        // Go to next line if movie suspended
                        if (m.group(7).toLowerCase().contains("suspended")){
                            continue;
                        }
                        
                        currentTitle = m.group(1);
                        currentType = m.group(6);
                        currentGenre = m.group(8);

                        if (m.group(2).contains("?")) {
                            currentYear = "";
                        }
                        else {
                            currentYear = m.group(2);
                        }

                        if (m.group(4) == null) {
                            currentRomanNumber = "0";
                        }
                        else {
                            currentRomanNumber = Integer.toString(RomanNumeral.convert(m.group(4)));
                        }
                        
                        writer.println(String.join(DELIMITER, addQuotes(currentTitle), currentYear, addQuotes(currentType), currentRomanNumber, addQuotes(currentGenre)));
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
