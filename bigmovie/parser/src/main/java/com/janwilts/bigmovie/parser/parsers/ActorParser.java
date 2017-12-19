package com.janwilts.bigmovie.parser.parsers;

import java.io.*;

public class ActorParser extends Parser{
    public ActorParser(File file) {
        super(file);
    }

    @Override
    public void parse() {
        try  {
            File csvFile = new File("actors.csv");
            PrintWriter writer = new PrintWriter(csvFile, "UTF-8");

            String currentActorName = "";

            int linesBeforeList = 4;
            boolean foundList = false;

            for(String line; (line = reader.readLine()) != null; ) {
                if(!foundList && line.contains("THE ACTORS LIST")) {
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
                    else if(line.contains("(") && Character.isDigit(line.charAt(line.indexOf("(") + 1)) &&
                            line.indexOf(")", line.indexOf("(") + 1) > -1) {

                        currentFilm = line.substring(line.indexOf("\t"), line.indexOf("(")).trim();
                        currentYear = line.substring(line.indexOf("(") + 1, line.indexOf(")", line.indexOf("(") + 1));
                    }
                    else {
                        currentFilm = line.substring(line.indexOf("\t"));
                    }

                    writer.println(currentActorName + "," + currentFilm + "," + currentYear + "," + currentRole);
                }
            }

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
