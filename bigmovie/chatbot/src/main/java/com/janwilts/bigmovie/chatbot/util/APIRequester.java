package com.janwilts.bigmovie.chatbot.util;

import com.google.gson.Gson;
import com.janwilts.bigmovie.chatbot.models.Model;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIRequester {
    private static final String API_LOCATION = "http://localhost:8080/";

    private Gson gson;
    private Class cl;

    public APIRequester(Class cl) {
        this();
        this.cl = cl;
    }

    public APIRequester() {
        this.gson = new Gson();
        this.cl = String.class;
    }

    public Model getFromAPI(String input) throws IOException {
        URL url = new URL(API_LOCATION + input);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        return (Model) gson.fromJson(reader, cl);
    }

    public File getImageFromAPI(String input, String fileName) throws IOException {
        URL url = new URL(API_LOCATION + input);

        URL fileLocation = this.getClass().getResource("/images/");
        File file = new File(fileLocation.getPath() + fileName);

        FileUtils.copyURLToFile(url, file);

        return file;
    }
}
