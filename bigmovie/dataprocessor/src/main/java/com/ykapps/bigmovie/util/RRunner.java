package com.ykapps.bigmovie.util;

import com.ykapps.bigmovie.exceptions.RExcepton;
import org.rosuda.JRI.Rengine;

public class Rrunner {
    private Rengine engine;

    public RRunner() throws Exception {
        this.engine = new Rengine();

        // the engine creates R is a new thread, so we should wait until it's ready
        if (!engine.waitForR()) {
            throw new RExcepton("Unable load R");
        }
    }
}
