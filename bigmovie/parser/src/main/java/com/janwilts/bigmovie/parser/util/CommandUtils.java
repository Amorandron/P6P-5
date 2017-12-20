package com.janwilts.bigmovie.parser.util;

public class CommandUtils {
    public static void error(String error) {
        CommandUtils.error(error, "");
    }

    public static void error(String error, String usage) {
        System.out.println(String.format("ERROR: %s", error));
        if(usage.length() > 0)
            System.out.println(String.format("USAGE: %s", usage));
    }
}
