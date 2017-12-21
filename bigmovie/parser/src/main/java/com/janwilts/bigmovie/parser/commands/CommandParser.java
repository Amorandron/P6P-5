package com.janwilts.bigmovie.parser.commands;

import com.janwilts.bigmovie.parser.util.CommandUtils;
import io.reactivex.Observable;

import java.io.File;

public class CommandParser {
    public static void parse(String[] args) {
        String mainUsage = "parser <path> <-a | -f> [file]";

        Command command = null;

        if (args.length == 0)
            CommandUtils.error("No sources directory set.", mainUsage);
        else if (args.length == 1)
            CommandUtils.error("No option set.", "parser <path> <-a | -f > [file]");
        else if (args.length == 2)
            if (args[1].equals("-f") || args[1].equals("-m"))
                CommandUtils.error("Please specify a file.", "parser <path> <-f | -m> <file>");
            else if (args[1].equals("-a"))
                command = new FullCommand(args[0]);
            else
                CommandUtils.error("Option does not exist", mainUsage);
        else if (args.length == 3)
            if (args[1].equals("-f"))
                command = new SingleCommand(args[0] + args[2]);
            else
                CommandUtils.error("Option does not exist", mainUsage);


        if (args.length > 1) {
            File file = new File(args[0]);
            if (!fileCommandParser(file, "source directory", mainUsage))
                command = null;
        }

        if (args.length > 2) {
            File file = new File(args[0] + args[2]);
            if (!fileCommandParser(file, "file ", mainUsage))
                command = null;
        }

        if(command != null)
            command.execute();
        else
            System.exit(0);
    }

    private static Boolean fileCommandParser(File file, String name, String usage) {
        Boolean result = true;
        if (!file.isDirectory()) {
            CommandUtils.error(String.format("Your %s is a file, not a directory.", name), usage);
            result = false;
        }
        if (!file.isAbsolute()) {
            CommandUtils.error("Your path is relative, not absolute.", usage);
            result = false;
        }
        return result;
    }
}
