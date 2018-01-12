package com.janwilts.bigmovie.parser.parsers;

import com.google.common.base.Strings;
import com.janwilts.bigmovie.parser.util.RomanNumeral;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author Jan
 */
public class MpaaParser extends Parser {
    public MpaaParser(File file) {
        super(file);
    }

    @Override
    public void parse() {
        boolean foundList = false;
        boolean prevRating = false;

        try (PrintWriter writer = new PrintWriter(this.csv, "UTF-8")) {
            for (String line; (line = this.readLine()) != null; ) {
                if (!foundList && line.equals("=========================")) foundList = true;
                if (foundList && line.startsWith("------------------")) continue;
                else if (foundList && line.length() > 0) {
                    String title;
                    String year;
                    String type = "";
                    int occurrence = 0;

                    if (line.charAt(0) == 'M') {
                        if (prevRating) {
                            writer.print(QUOTE + "\n");
                        }

                        line = line.substring(4);

                        year = line.substring(line.lastIndexOf('(') + 1, line.lastIndexOf(')'));
                        title = line.substring(0, line.lastIndexOf('(') - 1);

                        if (year.charAt(0) == 'T' || year.charAt(0) == 'V') {
                            type = line.substring(line.lastIndexOf('(') + 1, line.lastIndexOf(')'));
                            line = line.substring(0, line.lastIndexOf('('));
                            title = line.substring(0, line.lastIndexOf('(') - 1);
                            year = line.substring(line.lastIndexOf('(') + 1, line.lastIndexOf(')'));
                        }

                        if (year.contains("/")) {
                            occurrence = RomanNumeral.convert(year.substring(year.indexOf("/") + 1, year.length()));
                            year = year.substring(0, year.indexOf("/"));
                        }

                        writer.print(QUOTE + title.replace(QUOTE, DOUBLE_QUOTE) + QUOTE + "," + year + "," + addQuotes(type) + "," + occurrence + ",");

                    }
                    else if (line.charAt(0) == 'R') {

                        prevRating = true;
                        String rating;
                        StringBuilder reason;

                        if (line.contains(" G ") ||
                                line.contains(" PG ") ||
                                line.contains(" PG-13 ") ||
                                line.contains(" PG -13") ||
                                line.contains(" PG- 13") ||
                                line.contains(" R ") ||
                                line.contains(" NC-17 ") ||
                                line.contains(" NC - 17") ||
                                line.contains("CERTIFICATE") ||
                                line.contains("(Rating Symbol Changed)") ||
                                line.contains("RatPG-13") ||
                                line.contains("Re-Issue")) {
                            line = line.substring(4);

                            Integer[] indexes = {
                                line.indexOf(" G "),
                                line.indexOf(" PG "),
                                line.indexOf(" PG-13 "),
                                line.indexOf(" PG -13"),
                                line.indexOf(" PG- 13"),
                                line.indexOf(" R "),
                                line.indexOf(" NC-17 ")
                            };

                            line = line.substring(Collections.max(Arrays.asList(indexes)) + 1);

                            rating = line.substring(0, line.indexOf(" "));
                            rating = rating.replace(" ", "");

                            if(!rating.equals("G")
                                    && !rating.equals("PG")
                                    && !rating.equals("PG-13")
                                    && !rating.equals("R")
                                    && !rating.equals("NC-17")) {
                                rating = "";
                            }

                            reason = new StringBuilder();

                            if(line.length() > 5) {
                                line = line.substring(Collections.max(Arrays.asList(indexes)) + 1).trim();
                                reason = new StringBuilder(line);
                            }

                            while(!(line = reader.readLine()).equals("")) {
                                if(line.charAt(0) == 'R') {
                                    line = line.substring(4);
                                    reason.append(line);
                                }
                            }

                            writer.print(QUOTE + rating + QUOTE + DELIMITER + QUOTE + reason.toString().replace(QUOTE, DOUBLE_QUOTE));
                        }
                    }
                }
            }
            writer.print(QUOTE);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
