package com.janwilts.bigmovie.parser.parsers;

import java.io.File;

public class RatingParser extends Parser{
    public RatingParser(File file) {
        super(file);
    }

    @Override
    public void parse() {
        try  {
            for(String line; (line = this.getLine()) != null; ) {
                writer.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
