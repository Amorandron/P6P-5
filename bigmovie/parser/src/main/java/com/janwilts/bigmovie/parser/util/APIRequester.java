package com.janwilts.bigmovie.parser.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Jan
 */
public class APIRequester {
    // Static method to help with requesting the api for currency needs
    public static String request(String link) {
        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
