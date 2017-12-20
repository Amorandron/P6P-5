package com.janwilts.bigmovie.parser.parsers;

import java.io.*;
import java.util.regex.*;

public class GenreParser extends Parser {
    public GenreParser(File file) {
        super(file);
    }

    @Override
    public void parse() {
        try{
            String pattern = "(.*?)\\s\\((.{4})(|\\/(.*?))\\)(.*?\\).*?|.*?)([a-zA-Z]+\\-?[a-zA-Z]+)";
            Pattern p = Pattern.compile(pattern);

            String line;
            String currentTitle = "";
            String currentYear = "";
            String currentGenre = "";
            String currentRomanNumber = "";

            /*
            regex (series still included!): (.*?)\s\((.{4})(|\/(.*?))\)(.*?\).*?|.*?)([a-zA-Z]+\-?[a-zA-Z]+)
            \1 --> title
            \2 --> year
            \4 --> romans number (only movies with same title as year, but different movies are)
            \6 --> genre or suspended(suspended movie should not be include)
             */

            while(((line = reader.readLine()) != null)){
                Matcher m = p.matcher(line);

                if(m.matches()){
                    //read next line if current is a serie or suspended
                    if(m.group(1).startsWith("\"") || m.group(6).toLowerCase()=="suspended"){
                        continue;
                    }

                    currentTitle = m.group(1);
                    currentYear = m.group(2);
                    currentGenre = m.group(6);
                    if(m.group(4)==null){
                        currentRomanNumber = "0";
                    }else{
                        currentRomanNumber = Integer.toString(convert(m.group(4)));
                    }

                    writer.println(currentTitle + "," + currentYear + "," + currentRomanNumber + "," + currentGenre);
                }
            }

            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private int convertRec(String s) {
        if (s.isEmpty()) return 0;
        if (s.startsWith("M"))  return 1000 + convertRec(s.substring(1));
        else if (s.startsWith("CM")) return 900  + convertRec(s.substring(2));
        else if (s.startsWith("D"))  return 500  + convertRec(s.substring(1));
        else if (s.startsWith("CD")) return 400  + convertRec(s.substring(2));
        else if (s.startsWith("C"))  return 100  + convertRec(s.substring(1));
        else if (s.startsWith("XC")) return 90   + convertRec(s.substring(2));
        else if (s.startsWith("L"))  return 50   + convertRec(s.substring(1));
        else if (s.startsWith("XL")) return 40   + convertRec(s.substring(2));
        else if (s.startsWith("X"))  return 10   + convertRec(s.substring(1));
        else if (s.startsWith("IX")) return 9    + convertRec(s.substring(2));
        else if (s.startsWith("V"))  return 5    + convertRec(s.substring(1));
        else if (s.startsWith("IV")) return 4    + convertRec(s.substring(2));
        else if (s.startsWith("I"))  return 1    + convertRec(s.substring(1));
        throw new IllegalArgumentException("Unexpected roman numerals");
    }

    private int convert(String s) {
        if (s == null || s.isEmpty() || !s.matches("^(M{0,3})(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$"))
            return -1;
        return convertRec(s);
    }
}
