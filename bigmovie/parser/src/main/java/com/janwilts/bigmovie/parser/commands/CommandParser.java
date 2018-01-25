package com.janwilts.bigmovie.parser.commands;

import com.janwilts.bigmovie.parser.util.CommandUtils;

import java.io.File;

/**
 * @author Jan
 */
public class CommandParser {
    // Check what parameters where provided with the arguments and sets the program up accordingly
    public static void parse(String[] args) {
        Command command = null;
        Command insertCommand = null;

        if (args.length == 0)
            CommandUtils.error("No sources directory set.");
        else if (args.length == 1)
            CommandUtils.error("No option set.", "parser <path> <-a | -f | -i > [file]");
        else if (args.length == 2)
            if (args[1].equals("-f") || args[1].equals("-m"))
                CommandUtils.error("Please specify a file.", "parser <path> <-a | -f | -i> [file]");
            else if (args[1].equals("-a"))
                command = new FullCommand(args[0]);
            else if (args[1].equals("-ai") || args[1].equals("-ia")) {
                command = new FullCommand(args[0]);
                insertCommand = new InsertionCommand();
            }
            else
                CommandUtils.error("Option does not exist");
        else if (args.length == 3)
            if (args[1].equals("-f"))
                command = new SingleCommand(args[0] + args[2]);
            else
                CommandUtils.error("Option does not exist");

        // If args length is short, then parse every file in the directory
        if (args.length > 1) {
            File file = new File(args[0]);
            if (!directoryCommandParser(file))
                command = null;
        }

        // else only parse a single file
        if (args.length > 2) {
            File file = new File(args[0] + args[2]);
            if (!fileCommandParser(file))
                command = null;
        }

        if(command != null) {
            command.execute();
            // Checks to see if the insert command is set, meaning the data should also be exported to the database
            // defined in the .env file
            if(insertCommand != null)
                insertCommand.execute();
        }
        else
            System.exit(0);
    }

    // Checks to see if target is a directory
    private static Boolean directoryCommandParser(File file) {
        Boolean result = true;
        if (!file.isDirectory()) {
            CommandUtils.error("Your source is a file, not a directory.");
            result = false;
        }
        if (!file.isAbsolute()) {
            CommandUtils.error("Your path is relative, not absolute.");
            result = false;
        }
        return result;
    }

    // checks to see if target is a file
    private static Boolean fileCommandParser(File file) {
        Boolean result = true;
        if (!file.isFile()) {
            CommandUtils.error("Your file is a directory, not a files.");
            result = false;
        }
        if (!file.isAbsolute()) {
            CommandUtils.error("Your path is relative, not absolute.");
            result = false;
        }
        return result;
    }
}
