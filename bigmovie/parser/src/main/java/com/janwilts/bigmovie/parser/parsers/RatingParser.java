package com.janwilts.bigmovie.parser.parsers;

import java.io.File;
import java.io.PrintWriter;

public class RatingParser extends Parser{
    public RatingParser(File file) {
        super(file);
    }

    @Override
    public void parse() {
        try(PrintWriter writer = new PrintWriter(this.csv, "UTF-8"))  {
            for(String line; (line = reader.readLine()) != null; ) {
                writer.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
