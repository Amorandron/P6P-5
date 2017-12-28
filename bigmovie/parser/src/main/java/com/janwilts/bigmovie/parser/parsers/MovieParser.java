package com.janwilts.bigmovie.parser.parsers;

import com.janwilts.bigmovie.parser.util.RomanNumeral;

import java.io.File;
import java.io.PrintWriter;

/**
 * @author Jan
 */
public class MovieParser extends Parser {

    public MovieParser(File file) {
        super(file); }

    @Override
    public void parse() {
        int linesBeforeList = 2;
        boolean foundList = false;

        try(PrintWriter writer = new PrintWriter(this.csv, "UTF-8"))  {
            for(String line; (line = this.readLine()) != null; ) {
                if(!foundList && line.equals("==========="))
                    foundList = true;
                if(foundList && linesBeforeList > 0)
                    linesBeforeList--;
                else if(foundList && line.equals(""))
                    return;
                else if(linesBeforeList == 0 && line.length() > 0) {
                    if(line.equals("--------------------------------------------------------------------------------"))
                        break;
                    if(line.charAt(0) == '\"')
                        continue;
                    if(line.contains("{{SUSPENDED}}"))
                        continue;

                    String title;
                    String year;
                    int occurrence = 0;

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

                    if(year.equals("????"))
                        year = "";

                    writer.println("\"" + title.replace("\"", "\"\"") + "\"" + "," +
                            year + "," + occurrence);
                }
            }
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
