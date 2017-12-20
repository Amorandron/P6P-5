package com.janwilts.bigmovie.parser.parsers;

import com.janwilts.bigmovie.parser.enums.Parsable;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipInputStream;

public abstract class Parser {
    private static Parser currentParser;

    protected File file;
    protected BufferedReader reader;
    protected PrintWriter writer = null;

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
                .equals(Parsable.getList().get(index));
    }

    public Parser(File file) {
        this.file = file;

        File csv = new File("output/" + file.getName().substring(0, file.getName().indexOf('.')) + ".csv");
        csv.getParentFile().mkdirs();

        try {
            this.writer = new PrintWriter(csv, "UTF-8");
            this.reader = selectReader();
        } catch (Exception e) {
            e.printStackTrace();
        }

        parse();
        writer.flush();
    }

    protected String getLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private BufferedReader selectReader() throws Exception {
        String extension = file.getName().substring(file.getName().lastIndexOf('.') + 1);
        if (extension.equals("gz")) {
            return new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(file))));
        } else if(extension.equals("zip")) {
            return new BufferedReader(new InputStreamReader(new ZipInputStream(new FileInputStream(file))));
        } else {
            return new BufferedReader(new FileReader(file));
        }
    }

    public abstract void parse();
}
