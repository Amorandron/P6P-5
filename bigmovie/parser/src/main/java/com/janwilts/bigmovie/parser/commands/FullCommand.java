package com.janwilts.bigmovie.parser.commands;

import com.janwilts.bigmovie.parser.enums.Parsable;
import com.janwilts.bigmovie.parser.tasks.FileParseTask;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Yannick & Jan
 */
public class FullCommand implements Command
{
    private String directory;

    FullCommand(String directory)
    {
        this.directory = directory;
    }


    @Override
    public void execute()
    {
        File dataDirectory = new File(directory);

        List<File> files = Arrays.asList(Objects.requireNonNull(dataDirectory.listFiles()));

        final List<File> filesFiltered = files.stream()
                .filter(f -> Parsable.getList()
                        .stream()
                        .map(Parsable::toString)
                        .collect(Collectors.toList())
                        .contains(f.getName().substring(0, f.getName().indexOf('.'))))
                        .collect(Collectors.toList());

        for(File f : filesFiltered) {
            new Thread(new FileParseTask(f)).start();
        }
    }
}
