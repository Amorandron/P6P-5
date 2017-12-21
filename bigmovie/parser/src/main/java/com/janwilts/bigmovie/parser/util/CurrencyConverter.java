package com.janwilts.bigmovie.parser.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.janwilts.bigmovie.parser.Main;

import java.util.HashMap;
import java.util.Map;

public class CurrencyConverter
{
    private static Map<String, Double> RATES_TO_USD = new HashMap<>();
    
    static //add hardcoded conversion rates (to USD), as these currencies are obsolete and the API doesn't convert these
    {
        RATES_TO_USD.put("FRF", 0.180632);
        RATES_TO_USD.put("ITL", 0.000611951);
        RATES_TO_USD.put("ROL", 0.0000255917);
        RATES_TO_USD.put("DEM", 0.605884);
        RATES_TO_USD.put("PTE", 0.00591068);
        RATES_TO_USD.put("FIM", 0.199276);
        RATES_TO_USD.put("ESP", 0.00712479);
        RATES_TO_USD.put("BEF", 0.0294496);
        RATES_TO_USD.put("IEP", 1.50883);
        RATES_TO_USD.put("TRL", 0.000000261908);
        RATES_TO_USD.put("SIT", 0.00495879);
        RATES_TO_USD.put("GRD", 0.00348650);
        RATES_TO_USD.put("EEK", 0.0759165);
        RATES_TO_USD.put("YUM", 0.61);
        RATES_TO_USD.put("BGL", 0.607174);
        RATES_TO_USD.put("TMM", 0.0000571258);
        RATES_TO_USD.put("VEB", 0.000100125);
        RATES_TO_USD.put("LTL", 0.343989);
        RATES_TO_USD.put("LUF", 0.0294563);
        RATES_TO_USD.put("SKK", 0.0394285);
        RATES_TO_USD.put("MTL", 2.76688);
        RATES_TO_USD.put("CYP", 2.02951);
        RATES_TO_USD.put("LVL", 1.69013);
        RATES_TO_USD.put("GHC", 0.0000221057);
        RATES_TO_USD.put("AZM", 0.000118365);
        RATES_TO_USD.put("AFA", 0.0144170);
        RATES_TO_USD.put("BYR", 0.0000497056);
        RATES_TO_USD.put("ZWD", 0.00276319);
        RATES_TO_USD.put("SDD", 0.00150150);
        RATES_TO_USD.put("MZM", 0.0000169248);
        RATES_TO_USD.put("IRR", 0.0000279736);
        RATES_TO_USD.put("IMP", 1.33822);
        RATES_TO_USD.put("GGP", 1.33755);
    }
    
    public static double convert(double in, String from, String to)
    {
        if (RATES_TO_USD.containsKey(from) && to.equals("USD")) return in * RATES_TO_USD.get(from);
        
        String url = String.format("https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency=%s" + "&to_currency=%s&apikey=%s", from, to, Main.dotEnv.get("ALPHA_VANTAGE_KEY"));
        
        String response = APIRequester.request(url);
        JsonObject json = new Gson().fromJson(response, JsonObject.class);
        JsonObject info = json.get("Realtime Currency Exchange Rate").getAsJsonObject();
        Double rate = info.get("5. Exchange Rate").getAsDouble();
        if (to.equals("USD")) RATES_TO_USD.put(from, rate); //cache the conversion rates (to USD), so we don't call the API multiple times for the same conversion rates
        return in * rate;
    }
}
