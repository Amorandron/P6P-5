package com.janwilts.bigmovie.parser.parsers;

import com.janwilts.bigmovie.parser.enums.Parsable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipInputStream;

public abstract class Parser {
    private static Parser currentParser;

    protected File file;
    protected BufferedReader reader;
    protected File csv;
    protected int lines = 0;

    public static String[] parseFile(File file) {
        int currentFile = 0;

        if(checkFileName(file, 0)) {
            currentParser = new MovieParser(file);
            currentFile = 1;
        }
        else if(checkFileName(file, 1) || checkFileName(file, 2)) {
            currentParser = new ActorParser(file);
            currentFile = 2;
        }
        else if(checkFileName(file, 2)) {
            currentParser = new ActorParser(file);
            currentFile = 3;
        }
        else if(checkFileName(file, 3)) {
            currentParser = new BiographyParser(file);
            currentFile = 4;
        }
        else if(checkFileName(file, 4)) {
            currentParser = new BusinessParser(file);
            currentFile = 5;
        }
        else if(checkFileName(file, 5)) {
            currentParser = new RatingParser(file);
            currentFile = 6;
        }
        else if(checkFileName(file, 6)) {
            currentParser = new SoundtrackParser(file);
            currentFile = 7;
        }
        else if(checkFileName(file, 7)) {
            currentParser = new CountryParser(file);
            currentFile = 8;
        }
        else if(checkFileName(file, 8)) {
            currentParser = new GenreParser(file);
            currentFile = 9;
        }
        else if(checkFileName(file, 9)) {
            currentParser = new MpaaParser(file);
            currentFile = 10;
        }

        currentParser.parse();
        return new String[] {file.getName().substring(0, file.getName().indexOf('.')), Integer.toString(currentFile), "10"};
    }

    private static Boolean checkFileName(File file, int index) {
        return file.getName().substring(0, file.getName().indexOf('.'))
                .equals(Parsable.getList().get(index));
    }

    public Parser(File file) {
        this.file = file;
        this.csv = new File("output/" + file.getName().substring(0, file.getName().indexOf('.')) + ".csv");
        csv.getParentFile().mkdirs();

        try {
            this.reader = getReader();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.parse();
    }

    private BufferedReader getReader() throws IOException {
        String extension = file.getName().substring(file.getName().lastIndexOf('.') + 1);

        switch (extension) {
            case "gz":
                return new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(file)), StandardCharsets.ISO_8859_1));
            case "zip":
                return new BufferedReader(new InputStreamReader(new ZipInputStream(new FileInputStream(file)), StandardCharsets.ISO_8859_1));
            default:
                return new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.ISO_8859_1));
        }
    }

    protected String readLine() throws IOException {
        lines++;
        return this.reader.readLine();
    }

    public abstract void parse();
}
