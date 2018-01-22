package com.ykapps.bigmovie.util;

import com.ykapps.bigmovie.exceptions.RException;
import org.apache.commons.io.FileUtils;
import org.rosuda.JRI.REXP;
import org.rosuda.JRI.Rengine;

import javax.script.ScriptException;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RRunner {
    private static final String[] rArgs = {"--no-save"};
    private Rengine engine;

    private String database;
    private String host;
    private String port;
    private String username;
    private String password;

    public RRunner() throws RException, IOException {
        this.engine = new Rengine(rArgs, false, null);

        if(!engine.waitForR()) {
            throw new RException("R not loaded yet");
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("conf/application.conf")));

        String line;

        while ((line = in.readLine()) != null) {
            if(line.startsWith("db.url")) {
                line = line.substring(line.indexOf('\"'));
                database = line.substring(line.lastIndexOf('/') + 1, line.lastIndexOf('\"'));
                line = line.substring(0, line.lastIndexOf('/'));
                port = line.substring(line.lastIndexOf(':') + 1);
                line = line.substring(0, line.lastIndexOf(':'));
                host = line.substring(line.lastIndexOf('/') + 1);
            }
            if(line.startsWith("db.user")) {
                username = line.substring(line.indexOf('\"') + 1, line.lastIndexOf('\"'));
            }
            if(line.startsWith("db.password")) {
                password = line.substring(line.indexOf('\"') + 1, line.lastIndexOf('\"'));
            }
        }

        try {
            run("libs.R");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<REXP> runDb(String script, String... params) throws IOException, ScriptException, InterruptedException {
        List<REXP> connection = run("connection.R");
        List<REXP> output;
        int[] status = connection.get(0).asIntArray();

        output = run(script, params);

        //run("disconnect.R");

        return output;
    }

    public List<REXP> run(String script, String... params) throws IOException, InterruptedException {
        InputStream in = getClass().getClassLoader().getResourceAsStream("R/" + script);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String bufferLine;
        String R = "";

        while ((bufferLine = reader.readLine()) != null) {
            R += bufferLine;
        }

        String[] input = R.split(";");

        List<String> lines = Arrays.stream(input).filter(i -> i.length() > 0).collect(Collectors.toList());

        int index = 0;
        List<REXP> output = new ArrayList<>();

        for(String line : lines) {
            line = line.replaceAll("\n", "");
            line = line.replaceAll(" {2,}", " ");

            if(line.contains("{{data}}"))
                line = line.replace("{{data}}", String.format("\"%s\"", database));

            if(line.contains("{{host}}"))
                line = line.replace("{{host}}", String.format("\"%s\"", host));

            if(line.contains("{{port}}"))
                line = line.replace("{{port}}", port);

            if(line.contains("{{user}}"))
                line = line.replace("{{user}}", String.format("\"%s\"", username));

            if(line.contains("{{pass}}"))
                line = line.replace("{{pass}}", String.format("\"%s\"", password));


            if(line.contains("{{param}}")) {
                while(line.contains("{{param}}")) {
                    line = line.replaceFirst("\\{\\{param}}", params[index]);
                    index++;
                }
            }

            if(line.contains("{{retrieve}}")) {
                line = line.replace("{{retrieve}}", "");
                output.add(engine.eval(line));
            }
            else {
                REXP temp = engine.eval(line);
            }
        }
        return output;
    }

    public Rengine getEngine() {
        return engine;
    }
}
