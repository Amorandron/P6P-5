package com.janwilts.bigmovie.parser.tasks;

import com.janwilts.bigmovie.parser.parsers.Parser;

import java.io.File;

/**
 * @author Yannick
 */
public class FileParseTask implements Runnable {

    private File f;

    public FileParseTask(File f) {
        this.f = f;
    }

    @Override
    public void run() {
        // Starts parse and logs time
        long startTime = System.currentTimeMillis();
        String[] result = Parser.parseFile(f);
        long endtime = System.currentTimeMillis();

        // Logs a message
        System.out.println(result[0] + " (File " + result[1] + "/" + result[2] + ") " + result[3] +
                " lines parsed. " + (endtime - startTime) + " ms");
    }
}
