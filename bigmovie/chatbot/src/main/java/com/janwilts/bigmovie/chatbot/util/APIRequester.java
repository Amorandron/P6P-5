package com.janwilts.bigmovie.chatbot.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.janwilts.bigmovie.chatbot.models.LUISResponse;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

public class APIRequester {
    private static final String API_LOCATION = "http://localhost:8080";
    private static final String LUIS_API_URL = "https://westeurope.api.cognitive.microsoft.com/luis/v2.0/apps/" +
            "92a3bce7-bf93-4b6d-8ecd-979f71e865d0?subscription-key=f0c91c2bea224f7493ee79cb3053f12e&timezoneOffset=60&q=";

    private ObjectMapper mapper;
    private Class cl;

    public APIRequester(Class cl) {
        this();
        this.cl = cl;
    }

    public APIRequester() {
        this.mapper = new ObjectMapper();
        this.cl = String.class;
    }

    public Object getFromAPI(String input) throws IOException {
        input = input.replaceAll(" ", "+");
        URL url = new URL(API_LOCATION + input);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        return mapper.readValue(reader, cl);
    }

    public <T> List<T> getArrayFromAPI(String input) throws IOException, ClassNotFoundException {
        input = input.replaceAll(" ", "+");
        URL url = new URL(API_LOCATION + input);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        Class<T[]> arrayClass = (Class<T[]>) Class.forName("[L" + cl.getName() + ";");
        T[] objects = mapper.readValue(reader, arrayClass);
        return Arrays.asList(objects);
    }

    public File getImageFromAPI(String input, String fileName) throws IOException {
        input = input.replaceAll(" ", "+");
        URL url = new URL(API_LOCATION + input);

        URL fileLocation = this.getClass().getResource("/images/");
        File file = new File(fileLocation.getPath().replace("%20", " ") + fileName);

        FileUtils.copyURLToFile(url, file);

        return file;
    }

    public LUISResponse getLUIS(String input) throws IOException {
        URL url = new URL(LUIS_API_URL + URLEncoder.encode(input, "UTF-8").replace("+", "%20"));

        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        return mapper.readValue(reader, LUISResponse.class);
    }
}
