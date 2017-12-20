package com.janwilts.bigmovie.parser.parsers;

import com.janwilts.bigmovie.parser.util.RomanNumeral;

import java.io.*;
import java.util.regex.*;

public class GenreParser extends Parser {
    public GenreParser(File file) {
        super(file);
    }

    @Override
    public void parse() {
        try (PrintWriter writer = new PrintWriter(this.csv, "UTF-8")){
            String pattern = "(.*?)\\s\\((.{4})(|\\/(.*?))\\)(.*?\\).*?|.*?)([a-zA-Z]+\\-?[a-zA-Z]+)";
            Pattern p = Pattern.compile(pattern);

            /*
            regex (series still included!): (.*?)\s\((.{4})(|\/(.*?))\)(.*?\).*?|.*?)([a-zA-Z]+?\-?[a-zA-Z]+)
            (.*?) --> first group; matches any character (except for line terminators) --> title
            \s --> matches any whitespace character; de space after the title
            \( --> matches the '(' character; first character before the year
            (.{4}) --> second group; matches any character exactly 4 times; --> year
            (|\/(.*?)) --> matches null or '/' character and (fourth group) matches any character --> romanNumber
            \) --> matches the ')' character; first character after the year
            (.*?\).*?|.*?) --> matches any character, matches the ')' character, matches any character or matches any character;
            fifth group; white space(and extra stuff like '(v)' or '(tv)') between year and genre
            ([a-zA-Z]+?\-?[a-zA-Z]+) --> sixth group; match a character one or more times, match '-' character, matches a character one or more times;
            --> genre (or suspended)

            \1 --> title
            \2 --> year
            \4 --> romans number (only movies with same title as year, but different movies are)
            \6 --> genre or suspended(suspended movie should not be included)
             */

            Boolean foundList = false;
            String line;
            String currentTitle = "";
            String currentYear = "";
            String currentGenre = "";
            String currentRomanNumber = "";

            while(((line = reader.readLine()) != null)) {
                if (!foundList && line.equals("8: THE GENRES LIST")) {
                    foundList = true;
                }
                if (foundList) {

                    //go to next line if it's a serie
                    if (line.startsWith("\"")) {
                        continue;
                    }

                    Matcher m = p.matcher(line);

                    if (m.matches()) {
                        //go to next line if movie suspended
                        if (m.group(6).toLowerCase() == "suspended") {
                            continue;
                        }

                        currentTitle = m.group(1);
                        if (m.group(2).contains("?")) {
                            currentYear = "0";
                        } else {
                            currentYear = m.group(2);
                        }
                        currentGenre = m.group(6);
                        if (m.group(4) == null) {
                            currentRomanNumber = "0";
                        } else {
                            currentRomanNumber = Integer.toString(RomanNumeral.convert(m.group(4)));
                        }

                        writer.println("\"" + currentTitle + "\"," + currentYear + "," + currentRomanNumber + ",\"" + currentGenre + "\"");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
