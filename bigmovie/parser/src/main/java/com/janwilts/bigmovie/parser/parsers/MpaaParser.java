package com.janwilts.bigmovie.parser.parsers;

import com.janwilts.bigmovie.parser.util.RomanNumeral;

import java.io.File;
import java.io.PrintWriter;

/**
 * @author Jan
 */
public class MpaaParser extends Parser{
    public MpaaParser(File file) {
        super(file);
    }

    @Override
    public void parse() {
        boolean foundList = false;
        boolean prevRating = false;

        try(PrintWriter writer = new PrintWriter(this.csv, "UTF-8"))  {

            for(String line; (line = this.readLine()) != null; ) {
                if(!foundList && line.equals("========================="))
                    foundList = true;
                if(foundList && line.equals("-------------------------------------------------------------------------------"))
                    continue;
                else if(foundList && line.length() > 0) {

                    String title;
                    String year;
                    int occurrence = 0;

                    if(line.charAt(0) == 'M') {
                        if(prevRating)
                            writer.print("\"\n");
                        line = line.substring(4);

                        year = line.substring(line.lastIndexOf('(') + 1, line.lastIndexOf(')'));
                        title = line.substring(0, line.lastIndexOf('(')  - 1);

                        if(year.charAt(0) == 'T' || year.charAt(0) == 'V') {
                            line = line.substring(0, line.lastIndexOf('('));
                            title = line.substring(0, line.lastIndexOf('(')  - 1);
                            year = line.substring(line.lastIndexOf('(') + 1, line.lastIndexOf(')'));
                        }

                        if(year.contains("/")) {
                            occurrence = RomanNumeral.convert(year.substring(year.indexOf("/") + 1, year.length()));
                            year = year.substring(0,year.indexOf("/"));
                        }

                        writer.print("\"" + title.replace("\"", "\"\"") + "\"" + "," + year + ","
                        + occurrence + ",");

                    }
                    else if(line.charAt(0) == 'R') {
                        prevRating = true;
                        String rating;
                        String reason;

                        if(line.contains("Rated")) {
                            line = line.substring(10);
                            rating = line.substring(0, line.indexOf(" "));

                            line = line.substring(line.indexOf(" ") + 1).trim();
                            reason = line;

                            writer.print("\"" + rating.replace("\"", "\"\"") + "\"" + "," + "\"" +
                                    reason.replace("\"", "\"\""));
                        }
                        else {
                            reason = line.substring(4);
                            writer.print(reason);
                        }

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
