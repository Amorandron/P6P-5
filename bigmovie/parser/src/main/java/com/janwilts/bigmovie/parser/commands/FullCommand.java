package com.janwilts.bigmovie.parser.commands;

import com.janwilts.bigmovie.parser.enums.Parsable;
import com.janwilts.bigmovie.parser.parsers.Parser;

import io.reactivex.Observable;

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
                        .contains(f.getName().substring(0, f.getName().indexOf('.'))))
                        .collect(Collectors.toList());

        Observable.fromIterable(filesFiltered)
                .map(Parser::parseFile)
                .doOnSubscribe(disposable -> System.out.println("Parsing.."))
                .doOnNext(result -> System.out.println(result[0] + " (File " + result[1] + "/" + result[2] + ") " + result[3] +
                                                        " lines parsed."))
                .doOnComplete(() -> System.out.println("Done."))
                .doOnError(Throwable::printStackTrace)
                .subscribe();
    }
}
