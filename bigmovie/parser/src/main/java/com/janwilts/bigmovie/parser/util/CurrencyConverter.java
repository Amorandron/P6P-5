package com.janwilts.bigmovie.parser.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.janwilts.bigmovie.parser.Main;

import java.util.HashMap;
import java.util.Map;

public class CurrencyConverter
{
    public static Map<String, Double> SPECIAL_CASE_RATES = new HashMap<>();
    
    static
    {
        SPECIAL_CASE_RATES.put("FRF", 0.180632);
        SPECIAL_CASE_RATES.put("ITL", 0.000611951);
        SPECIAL_CASE_RATES.put("ROL", 0.0000255917);
        SPECIAL_CASE_RATES.put("DEM", 0.605884);
        SPECIAL_CASE_RATES.put("PTE", 0.00591068);
        SPECIAL_CASE_RATES.put("FIM", 0.199276);
        SPECIAL_CASE_RATES.put("ESP", 0.00712479);
    }
    
    public static double convert(double in, String from, String to)
    {
        if (SPECIAL_CASE_RATES.containsKey(from) && to.equals("USD")) return in * SPECIAL_CASE_RATES.get(from);
        
        String url = String.format("https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency=%s" + "&to_currency=%s&apikey=%s", from, to, Main.dotEnv.get("ALPHA_VANTAGE_KEY"));
        
        String response = APIRequester.request(url);
        JsonObject json = new Gson().fromJson(response, JsonObject.class);
        JsonObject info = json.get("Realtime Currency Exchange Rate").getAsJsonObject();
        Double rate = info.get("5. Exchange Rate").getAsDouble();
        return in * rate;
    }
}
