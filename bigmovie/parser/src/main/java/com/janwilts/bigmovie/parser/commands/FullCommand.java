package com.janwilts.bigmovie.parser.commands;

import com.janwilts.bigmovie.parser.Parsable;
import com.janwilts.bigmovie.parser.tasks.FileParseTask;

import java.io.File;
import java.util.ArrayList;
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
    private List<Thread> tasks;

    FullCommand(String directory)
    {
        this.directory = directory;
        this.tasks = new ArrayList<>();
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
            tasks.add(new Thread(new FileParseTask(f)));
        }
        tasks.forEach(Thread::start);

        for(Thread t : tasks) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
