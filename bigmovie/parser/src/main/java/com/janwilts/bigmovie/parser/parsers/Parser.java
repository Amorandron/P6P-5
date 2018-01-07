package com.janwilts.bigmovie.parser.parsers;

import com.janwilts.bigmovie.parser.Parsable;

import java.io.*;
import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipInputStream;

/**
 * @author Yannick & Jan
 */
public abstract class Parser {
    private static Parser currentParser;

    protected File file;
    BufferedReader reader;
    File csv;
    private static int lines = 0;

    public static String[] parseFile(File file) {
        int index = Parsable.getList()
                .stream()
                .map(Parsable::toString)
                .collect(Collectors.toList())
                .indexOf(file.getName().substring(0, file.getName().indexOf('.')));

        Parsable parser = Parsable.getList().get(index);


        try {
            currentParser = parser.getParser(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        currentParser.parse();
        return new String[] {file.getName().substring(0, file.getName().indexOf('.')), Integer.toString(Parsable.getList().indexOf(parser) + 1), "10",
        Integer.toString(lines)};
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
