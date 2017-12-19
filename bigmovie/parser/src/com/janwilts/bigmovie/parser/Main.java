package com.janwilts.bigmovie.parser;

import com.janwilts.bigmovie.parser.parsers.ActorParser;
import com.janwilts.bigmovie.parser.parsers.Parser;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        if(args.length != 1)
            return;

        //File dataDirectory = new File(args[0]);

        //if(!dataDirectory.isDirectory())
          //  return;

        ActorParser parser = new ActorParser(new File(args[0]));
        parser.parse();

        //if(checkSets(dataDirectory.listFiles())) {
            // TODO: Implement parser logic
        //}
    }

    private static Boolean checkSets(File[] files) {
        List<String> fileNames = Arrays.stream(files)
                .map(File::toString)
                .collect(Collectors.toList());

        return fileNames.containsAll(Arrays.asList(Parser.REQUIRED_FILES));
    }
}
