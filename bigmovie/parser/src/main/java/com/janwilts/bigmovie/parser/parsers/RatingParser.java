package com.janwilts.bigmovie.parser.parsers;

import com.janwilts.bigmovie.parser.util.RomanNumeral;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RatingParser extends Parser{
    public RatingParser(File file) {
        super(file);
    }

    @Override
    public void parse() {
        int linesBeforeList = 3;
        boolean foundList = false;

        try(PrintWriter writer = new PrintWriter(this.csv, "UTF-8"))  {
            for(String line; (line = this.readLine()) != null; ) {
                if(!foundList && line.contains("MOVIE RATINGS REPORT"))
                    foundList = true;
                if(foundList && linesBeforeList > 0)
                    linesBeforeList--;
                else if(foundList && line.equals(""))
                    return;
                else if(linesBeforeList == 0 && line.length() > 0) {
                    line = line.substring(16);
                    line = line.trim();

                    if(line.contains("\""))
                        continue;

                    String[] values = line.split("  ");
                    for (int i = 0; i < values.length; i++)
                        values[i] = values[i].trim();

                    int occurance = 0;
                    String title = "";
                    String yearString = "";
                    String year = "";

                    if(values[2].charAt(values[2].lastIndexOf('(') + 1) == 'V' || values[2].charAt(values[2].lastIndexOf('(') + 1) == 'T')
                        values[2] = values[2].substring(0, values[2].lastIndexOf('(') - 1);

                    if (values[2].lastIndexOf('(') != -1)
                        yearString = values[2].substring(values[2].lastIndexOf('(') + 1, values[2].length() - 1);

                    if(yearString.contains("/")) {
                        occurance = RomanNumeral.convert(values[2].substring(values[2].lastIndexOf('/') + 1, values[2].lastIndexOf(')')));
                    }
                    else {
                        if(!yearString.equals("????"))
                            year = yearString;
                    }

                    title = values[2].substring(0, values[2].lastIndexOf('(') - 1);

                    writer.println("\"" + title.replace("\"", "\"\"") + "\"" + "," +
                    year + "," + occurance + "," + values[0] + "," + values[1]);
                }
            }
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
