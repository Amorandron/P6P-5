package com.janwilts.bigmovie.gui.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("Duplicates")
public class APIRequester {
    private static final String API_LOCATION = "http://localhost:8080";
    
    private ObjectMapper mapper;
    private Class cl;
    
    public APIRequester() {
        this(String.class);
    }
    
    public APIRequester(Class cl) {
        this.mapper = new ObjectMapper();
        this.cl = cl;
    }
    
    public <T> T getFromAPI(String input) throws IOException {
        BufferedReader reader = getResponse(input);
        return (T) mapper.readValue(reader, cl);
    }
    
    public <T> List<T> getArrayFromAPI(String input) throws IOException, ClassNotFoundException {
        BufferedReader reader = getResponse(input);
        Class<T[]> arrayClass = (Class<T[]>) Class.forName("[L" + cl.getName() + ";");
        T[] objects = mapper.readValue(reader, arrayClass);
        return Arrays.asList(objects);
    }
    
    public File getImageFromAPI(String input, String fileName) throws IOException {
        URL url = new URL(API_LOCATION + input.replace(" ", "+"));
        
        URL fileLocation = this.getClass().getResource("/images/");
        File file = new File(fileLocation.getPath().replace("%20", " ") + fileName);
        
        FileUtils.copyURLToFile(url, file);
        
        return file;
    }
    
    private BufferedReader getResponse(String input) throws IOException {
        URL url = new URL(API_LOCATION + input.replace(" ", "+"));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        return new BufferedReader(new InputStreamReader(connection.getInputStream()));
    }
}
