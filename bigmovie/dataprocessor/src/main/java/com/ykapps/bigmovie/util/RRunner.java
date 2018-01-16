package com.ykapps.bigmovie.util;

import com.ykapps.bigmovie.exceptions.RException;
import org.rosuda.JRI.REXP;
import org.rosuda.JRI.Rengine;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RRunner {
    private Rengine engine;

    public RRunner() throws RException {
        this.engine = new Rengine();

        // the engine creates R is a new thread, so we should wait until it's ready
        if (!engine.waitForR()) {
            throw new RException("Unable to load R");
        }
    }

    public List<REXP> run(String script, String... params) throws IOException {
        URL url = this.getClass().getResource("/R/" + script);
        File RFile = null;
        try {
            RFile = new File(Objects.requireNonNull(url).toURI());
        } catch (URISyntaxException e) {
            RFile = new File(Objects.requireNonNull(url).getPath());
        }
        String R = FileUtils.readFileToString(RFile, "UTF-8");
        String[] lines = R.split("\n");

        int index = 0;
        List<REXP> output = new ArrayList<>();

        for(String line : lines) {
            if(line.contains("{{param}}")) {
                line = line.replace("{{param}}", params[index]);
                index++;
                engine.eval(line);
            }
            else if(line.contains("{{retrieve}}")) {
                line = line.replace("{{retrieve}}", "");
                output.add(engine.eval(line));
            }
            else {
                engine.eval(line);
            }
        }
        return output;
    }
}
