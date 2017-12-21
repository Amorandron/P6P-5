package com.janwilts.bigmovie.parser.commands;

import com.janwilts.bigmovie.parser.util.CommandUtils;

import java.io.File;

public class CommandParser {
    public static void parse(String[] args) {
        Command command = null;

        if (args.length == 0)
            CommandUtils.error("No sources directory set.");
        else if (args.length == 1)
            CommandUtils.error("No option set.", "parser <path> <-a | -f > [file]");
        else if (args.length == 2)
            if (args[1].equals("-f") || args[1].equals("-m"))
                CommandUtils.error("Please specify a file.", "parser <path> <-f | -m> <file>");
            else if (args[1].equals("-a"))
                command = new FullCommand(args[0]);
            else
                CommandUtils.error("Option does not exist");
        else if (args.length == 3)
            if (args[1].equals("-f"))
                command = new SingleCommand(args[0] + args[2]);
            else
                CommandUtils.error("Option does not exist");


        if (args.length > 1) {
            File file = new File(args[0]);
            if (!fileCommandParser(file, "source directory"))
                command = null;
        }

        if (args.length > 2) {
            File file = new File(args[0] + args[2]);
            if (!fileCommandParser(file, "file "))
                command = null;
        }

        if(command != null)
            command.execute();
        else
            System.exit(0);
    }

    private static Boolean fileCommandParser(File file, String name) {
        Boolean result = true;
        if (!file.isDirectory()) {
            CommandUtils.error(String.format("Your %s is a file, not a directory.", name));
            result = false;
        }
        if (!file.isAbsolute()) {
            CommandUtils.error("Your path is relative, not absolute.");
            result = false;
        }
        return result;
    }
}
