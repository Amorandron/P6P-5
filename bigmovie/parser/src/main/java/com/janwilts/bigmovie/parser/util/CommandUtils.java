package com.janwilts.bigmovie.parser.util;

/**
 * @author Jan
 */
public class CommandUtils {
    // Collection of string utilities for use within console messages

    public static void error(String error) {
        String mainUsage = "parser <path> <-a | -f> [file]";
        CommandUtils.error(error, mainUsage);
    }

    public static void error(String error, String usage) {
        System.out.println(String.format("ERROR: %s", error));
        if(usage.length() > 0)
            System.out.println(String.format("USAGE: %s", usage));
    }

    public static void announceStart(String name) {
        System.out.println(String.format("Parsing: %s", name));
    }

    public static void announceDone(String name, int lines, long ms) {
        System.out.println(String.format("Finished Parsing %d lines in file \"%s\" in %dms", lines, name, ms));
    }
}
