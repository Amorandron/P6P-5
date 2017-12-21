package com.janwilts.bigmovie.parser.parsers;

import com.janwilts.bigmovie.parser.util.RomanNumeral;

import java.io.File;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CountryParser extends Parser {
    public CountryParser(File file) {
        super(file);
    }

    @Override
    public void parse() {
        try (PrintWriter writer = new PrintWriter(this.csv, "UTF-8")) {
            String pattern = "(.*?)\\s\\((.{4})(|\\/(.*?))\\)(.*?)\\s([a-zA-Z].*)";
            Pattern p = Pattern.compile(pattern);

            String line = "";
            String movieName = "";
            String year = "";
            String iteration = "";
            String type = "";
            String country = "";
            Boolean foundList = false;
            int linesBeforeList = 1;

            while (((line = this.readLine()) != null)) {
                if (!foundList && line.equals("COUNTRIES LIST")) {
                    foundList = true;
                } else if (foundList && linesBeforeList != 0) {
                    linesBeforeList--;
                } else if (foundList && line.equals("-----------------------------------------------------------------------------")) {
                    return;
                } else if (linesBeforeList == 0 && line.length() > 0) {

                    //go to next line if it's a series
                    if (line.startsWith("\"")) {
                        continue;
                    }

                    Matcher m = p.matcher(line);

                    if (m.matches()) {
                        //go to next line if movie suspended
                        if (m.group(5).toUpperCase().contains("SUSPENDED")) {
                            continue;
                        }

                        movieName = m.group(1);
                        if (m.group(2).contains("?")) {
                            year = "";
                        } else {
                            year = m.group(2);
                        }
                        if (m.group(4) == null) {
                            iteration = "0";
                        } else {
                            iteration = Integer.toString(RomanNumeral.convert(m.group(4)));
                        }
                        type = m.group(5).replace("(", "").replace(")", "").trim();
                        country = m.group(6);

                        writer.println("\"" + movieName + "\"" + "," + year + "," + "\"" + iteration + "\"" + "," + "\"" + type + "\"" + "," + "\"" + country + "\"");
                        writer.flush();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}