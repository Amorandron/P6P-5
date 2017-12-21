package com.janwilts.bigmovie.parser.commands;

import com.janwilts.bigmovie.parser.enums.Parsable;
import com.janwilts.bigmovie.parser.parsers.Parser;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Flow;
import java.util.stream.Collectors;

public class FullCommand implements Command {
    private String directory;

    FullCommand(String directory) {
        this.directory = directory;
    }

    @Override
    public void execute() {
        File dataDirectory = new File(directory);

        List<File> files = Arrays.asList(Objects.requireNonNull(dataDirectory.listFiles()));

        final List<File> filesFiltered = files.stream()
                .filter(f -> Parsable.getList()
                        .contains(f.getName().substring(0, f.getName().indexOf('.'))))
                        .collect(Collectors.toList());

        Subscriber<int[]> subscriber = new Subscriber<>() {
            @Override
            public void onSubscribe(Subscription s) {
                System.out.println("Parsing..");
            }

            @Override
            public void onNext(int[] ints) {
                System.out.println("File" + ints[0] + "/" + ints[1]);
            }

            @Override
            public void onComplete() {
                System.out.println("Done.");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println(e.getMessage());
            }
        };

        List<Number> testnums = new ArrayList<>();
        testnums.add(1);
        testnums.add(2);
        testnums.add(3);

        Observable.fromIterable(files)
                .map(Parser::parseFile)
                .doOnSubscribe(disposable -> System.out.println("Parsing.."))
                .doOnNext(result -> System.out.println(result[0] + " (File " + result[1] + "/" + result[2] + ")"))
                .doOnComplete(() -> System.out.println("Done."))
                .doOnError(Throwable::printStackTrace)
                .subscribe();
    }
}
