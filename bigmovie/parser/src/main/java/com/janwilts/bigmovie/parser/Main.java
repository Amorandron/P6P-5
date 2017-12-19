package com.janwilts.bigmovie.parser;

import com.github.shyiko.dotenv.DotEnv;
import com.janwilts.bigmovie.parser.enums.RequiredFile;
import com.janwilts.bigmovie.parser.parsers.Parser;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Main {
    public static final Map<String, String> dotEnv = DotEnv.load();

    public static void main(String[] args) {
        if (args.length != 1)
            return;

        File dataDirectory = new File(args[0]);

        if (!dataDirectory.isDirectory())
            return;

        List<File> files = Arrays.asList(Objects.requireNonNull(dataDirectory.listFiles()));

        if (checkSets(files)) {
            files.stream()
                    .filter(f -> RequiredFile.getList().contains(f.getName()))
                    .forEach(Parser::parseFile);
        }
    }

    private static Boolean checkSets (List<File> files){
        List<String> fileNames = files.stream()
                .map(File::getName)
                .collect(Collectors.toList());

        return fileNames.containsAll(RequiredFile.getList());
    }
}
