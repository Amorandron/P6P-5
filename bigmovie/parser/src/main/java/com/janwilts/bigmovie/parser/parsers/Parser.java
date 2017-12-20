package com.janwilts.bigmovie.parser.parsers;

import com.janwilts.bigmovie.parser.enums.RequiredFile;

import java.io.*;
import java.util.zip.GZIPInputStream;

public abstract class Parser {
    private static Parser currentParser;

    protected File file;
    protected BufferedReader reader;
    protected File csv;

    public static void parseFile(File file) {
        if(checkFileName(file, 0))
            currentParser = new MovieParser(file);
        else if(checkFileName(file, 1) || checkFileName(file, 2))
            currentParser = new ActorParser(file);
        else if(checkFileName(file, 3))
            currentParser = new BiographyParser(file);
        else if(checkFileName(file, 4))
            currentParser = new BusinessParser(file);
        else if(checkFileName(file, 5))
            currentParser = new RatingParser(file);
        else if(checkFileName(file, 6))
            currentParser = new SoundtrackParser(file);
        else if(checkFileName(file, 7))
            currentParser = new CountryParser(file);
        else if(checkFileName(file, 8))
            currentParser = new GenreParser(file);
        else if(checkFileName(file, 9))
            currentParser = new MpaaParser(file);

        currentParser.parse();
    }

    private static Boolean checkFileName(File file, int index) {
        return file.getName().substring(0, file.getName().indexOf('.'))
                .equals(RequiredFile.getList().get(index));
    }

    public Parser(File file)  {
        this.file = file;
        this.csv = new File(file.getName().substring(0, file.getName().indexOf('.')) + ".csv");

        String extension = file.getName().substring(file.getName().lastIndexOf('.')  + 1);

        if(extension.equals("gz")) {
            try {
                this.reader = new BufferedReader(
                        new InputStreamReader(
                                new GZIPInputStream(
                                        new FileInputStream(file)
                                )
                        )
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else  {
            try {
                this.reader = new BufferedReader(
                    new FileReader(file)
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public abstract void parse();
}
