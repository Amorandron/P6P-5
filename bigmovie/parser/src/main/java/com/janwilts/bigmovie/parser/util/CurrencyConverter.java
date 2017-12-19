package com.janwilts.bigmovie.parser.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.janwilts.bigmovie.parser.Main;

public class CurrencyConverter {
    public static double convert(double in, String from, String to) {
        String url = String.format("https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency=%s" +
                "&to_currency=%s&apikey=%s", from, to, Main.dotEnv.get("ALPHA_VANTAGE_KEY"));

        String response = APIRequester.request(url);
        JsonObject json = new Gson().fromJson(response, JsonObject.class);
        JsonObject info = json.get("Realtime Currency Exchange Rate").getAsJsonObject();
        Double rate = info.get("5. Exchange Rate").getAsDouble();
        return in * rate;
    }
}
