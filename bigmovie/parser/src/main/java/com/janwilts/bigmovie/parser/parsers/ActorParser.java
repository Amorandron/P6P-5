package com.janwilts.bigmovie.parser.parsers;

import java.io.*;
import java.util.regex.Matcher;

public class ActorParser extends Parser{
    public ActorParser(File file) {
        super(file);
    }

    @Override
    public void parse() {
        try (PrintWriter writer = new PrintWriter(this.csv, "UTF-8")) {
            String currentActorName = "";

            char gender = this.csv.getName().equals("actors.csv") ? 'M' : 'F';

            int linesBeforeList = 4;
            boolean foundList = false;

            for(String line; (line = reader.readLine()) != null; ) {
                if(!foundList && line.contains("THE " + file.getName().substring(0, file.getName().indexOf('.')).toUpperCase() + " LIST")) {
                    foundList = true;
                }
                else if(foundList && linesBeforeList != 0) {
                    linesBeforeList--;
                }
                else if(foundList && line.equals("-----------------------------------------------------------------------------")) {
                    return;
                }
                else if(linesBeforeList == 0 && line.length() > 0) {
                    if(line.charAt(0) != '\t') {

                        currentActorName = line.substring(0, line.indexOf("\t")).trim();
                        int length = currentActorName.length();
                        line = line.substring(line.indexOf("\t"));
                    }

                    if(line.contains("\"")) {
                        continue;
                    }

                    String currentFilm = "";
                    String currentRole = "";
                    String currentYear = "";

                    if(line.contains("[")) {
                        currentFilm = line.substring(line.indexOf("\t"), line.indexOf("[")).trim();
                        currentRole = line.substring(line.indexOf("[") + 1, line.indexOf("]", line.indexOf("["))).trim();
                    }
                    else if(line.contains("<")){
                        currentFilm = line.substring(line.indexOf("\t"), line.indexOf("<")).trim();
                    }
                    else {
                        currentFilm = line.substring(line.indexOf("\t"));
                    }

                    String searchLine = line;

                    while(searchLine.contains("(")) {
                        int leftCommaInd = searchLine.indexOf("(");
                        int rightCommaInd = searchLine.indexOf(")", searchLine.indexOf("(") + 1);

                        if(rightCommaInd == -1) {
                            break;
                        }

                        if(searchLine.contains("(") && Character.isDigit(searchLine.charAt(leftCommaInd + 1)) &&
                                (rightCommaInd - leftCommaInd == 5 && Character.isDigit(searchLine.charAt(leftCommaInd + 1)) &&
                                        Character.isDigit(searchLine.charAt(rightCommaInd - 1))) ||
                                (rightCommaInd - leftCommaInd > 5 && Character.isDigit(searchLine.charAt(leftCommaInd + 1)) &&
                                        searchLine.charAt(rightCommaInd - 1) == 'I' || searchLine.charAt(rightCommaInd - 1) == 'V' ||
                                        searchLine.charAt(rightCommaInd - 1) == 'X') || (
                                rightCommaInd - leftCommaInd <= 5 && searchLine.charAt(rightCommaInd - 1) == '?' &&
                                        searchLine.charAt(leftCommaInd + 1) == '?'))
                        {

                            currentFilm = line.substring(line.indexOf("\t"), line.indexOf("(", line.indexOf(searchLine))).trim();

                            currentYear = searchLine.substring(searchLine.indexOf("(") + 1, searchLine.indexOf(")", searchLine.indexOf("(") + 1));
                            break;
                        }
                        else {
                            searchLine = searchLine.substring(searchLine.indexOf("(") + 1);
                        }
                    }

                    writer.println("\"" + currentActorName.replace("\"", "\"\"") + "\"" + "," + gender + "," +
                            "\"" + currentFilm.replace("\"", "\"\"") + "\"" + "," + currentYear
                            + "," + "\"" + currentRole.replace("\"", "\"\"") + "\"");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
