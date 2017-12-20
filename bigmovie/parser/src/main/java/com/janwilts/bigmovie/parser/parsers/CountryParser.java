package com.janwilts.bigmovie.parser.parsers;

import java.io.File;
import java.io.PrintWriter;

public class CountryParser extends Parser {
    public CountryParser(File file) {
        super(file);
    }

    @Override
    public void parse() {
        try (PrintWriter writer = new PrintWriter(this.csv, "UTF-8"))
        {

            int parsedLines = 0;

            //Amount of lines between COUNTRIES LIST and first data entry
            int linesBeforeList = 1;

            //Indicator whether MOVIES LIST is found
            boolean foundList = false;

            //Go over each line in the file
            for (String line; (line = reader.readLine()) != null; ) {

                //Beginning of the data list
                if (!foundList && line.contains("COUNTRIES LIST")) {
                    foundList = true;
                }

                //Skipping empty lines between caption of list
                else if (foundList && linesBeforeList != 0) {
                    linesBeforeList--;
                }

                //Last line, exits parser
                else if (foundList && line.equals("--------------------------------------------------------------------------------")) {
                    System.out.println("Amount of lines parsed: " + parsedLines);
                    return;
                } else if (linesBeforeList == 0 && line.length() > 0) {

                    //If line starts with tab, skip
                    if (line.charAt(0) != '\t') {
                        parsedLines++;

                        String currentMovieName = "";
                        String currentYear = "";
                        String type = "";
                        String country = "";

                        //skip series
//                        System.out.println(line);
                        if (line.startsWith("\"")) {
                            continue;
                        }

                        //Get movie
                        currentMovieName = line.substring(0, line.indexOf("(")).trim();

                        int length = currentMovieName.length();

                        //Go to the next part of the current line
                        line = line.substring(length);

                        int index = line.indexOf("(");

                        //Get year
                        currentYear = line.substring(index + 1, index + 5).trim().replace("????", "");

                        length = currentYear.length() + 3;
                        line = line.substring(length);

                        if (line.contains("(V)")) {
                            index = line.indexOf("V)");
                            type = line.substring(index, index + 1).trim();
                            length = type.length() + 3;
                            line = line.substring(length);
                        }
                        else if (line.contains("(TV)")) {
                            index = line.indexOf("TV)");
                            type = line.substring(index, index + 2).trim();
                            length = type.length() + 3;
                            line = line.substring(length);
                        }

                        country = line.substring(line.indexOf(')') + 1).replace("\t", "").trim();

                        writer.println("\"" + currentMovieName + "\"" + "," + currentYear + "," + "\"" + type + "\"" + "," + "\"" + country + "\"");
                    }
                }
            }
            writer.flush();
        } catch (
                Exception e)

        {
            e.printStackTrace();
        }
    }
}
