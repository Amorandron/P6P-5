package com.janwilts.bigmovie.parser.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.janwilts.bigmovie.parser.Main;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lars & Jan
 */
public class CurrencyConverter
{
    private static final Map<String, Double> RATES_TO_USD = new HashMap<>();
    
    static //add hardcoded conversion rates (to USD), as these currencies are obsolete and the API doesn't convert these
    {
        RATES_TO_USD.put("FJD", 0.48648756);
        RATES_TO_USD.put("MXN", 0.05191973);
        RATES_TO_USD.put("LVL", 1.69013);
        RATES_TO_USD.put("CDF", 6.3984E-4);
        RATES_TO_USD.put("GTQ", 0.13648885);
        RATES_TO_USD.put("CLP", 0.00161212);
        RATES_TO_USD.put("UGX", 2.7721E-4);
        RATES_TO_USD.put("HNL", 0.0424423);
        RATES_TO_USD.put("ZAR", 0.07862559);
        RATES_TO_USD.put("TND", 0.39861044);
        RATES_TO_USD.put("SDD", 0.0015015);
        RATES_TO_USD.put("BSD", 1.0);
        RATES_TO_USD.put("IQD", 8.4157E-4);
        RATES_TO_USD.put("CUP", 0.03921569);
        RATES_TO_USD.put("TWD", 0.03336781);
        RATES_TO_USD.put("DOP", 0.02066329);
        RATES_TO_USD.put("MYR", 0.01719277);
        RATES_TO_USD.put("GEL", 0.39627297);
        RATES_TO_USD.put("UYU", 0.03470446);
        RATES_TO_USD.put("MAD", 0.10635639);
        RATES_TO_USD.put("AZM", 1.18365E-4);
        RATES_TO_USD.put("PGK", 0.31179058);
        RATES_TO_USD.put("OMR", 2.59843522);
        RATES_TO_USD.put("SEK", 0.11896222);
        RATES_TO_USD.put("KES", 0.0097031);
        RATES_TO_USD.put("UAH", 0.03601138);
        RATES_TO_USD.put("BTN", 0.01564718);
        RATES_TO_USD.put("MZM", 1.69248E-5);
        RATES_TO_USD.put("ARS", 0.0563412);
        RATES_TO_USD.put("QAR", 0.27248068);
        RATES_TO_USD.put("IRR", 2.79736E-5);
        RATES_TO_USD.put("NLG", 0.16918813);
        RATES_TO_USD.put("CNY", 0.15185106);
        RATES_TO_USD.put("THB", 0.03055301);
        RATES_TO_USD.put("XPF", 0.00994796);
        RATES_TO_USD.put("BDT", 0.012061);
        RATES_TO_USD.put("PHP", 0.0199106);
        RATES_TO_USD.put("KWD", 3.31057862);
        RATES_TO_USD.put("PYG", 1.781E-4);
        RATES_TO_USD.put("ISK", 0.00950299);
        RATES_TO_USD.put("JMD", 0.00800346);
        RATES_TO_USD.put("BEF", 0.0294496);
        RATES_TO_USD.put("ESP", 0.00712479);
        RATES_TO_USD.put("COP", 3.3758E-4);
        RATES_TO_USD.put("MKD", 0.01929571);
        RATES_TO_USD.put("RUR", 1.709E-5);
        RATES_TO_USD.put("DZD", 0.00868787);
        RATES_TO_USD.put("GGP", 1.33755);
        RATES_TO_USD.put("SGD", 0.74338388);
        RATES_TO_USD.put("VEB", 1.00125E-4);
        RATES_TO_USD.put("KGS", 0.01437636);
        RATES_TO_USD.put("BND", 0.74333194);
        RATES_TO_USD.put("XAF", 0.00181183);
        RATES_TO_USD.put("LRD", 0.00796901);
        RATES_TO_USD.put("ITL", 6.11951E-4);
        RATES_TO_USD.put("CHF", 1.01327697);
        RATES_TO_USD.put("ATS", 0.03553396);
        RATES_TO_USD.put("HRK", 0.15759448);
        RATES_TO_USD.put("ALL", 0.00894055);
        RATES_TO_USD.put("GHC", 2.21057E-5);
        RATES_TO_USD.put("MTL", 2.76688);
        RATES_TO_USD.put("TZS", 4.4555E-4);
        RATES_TO_USD.put("XAU", 1265.72665367);
        RATES_TO_USD.put("VND", 4.404E-5);
        RATES_TO_USD.put("TRL", 2.61908E-7);
        RATES_TO_USD.put("AUD", 0.76630643);
        RATES_TO_USD.put("ILS", 0.28642355);
        RATES_TO_USD.put("BOB", 0.14517848);
        RATES_TO_USD.put("KHR", 2.4847E-4);
        RATES_TO_USD.put("MDL", 0.05817469);
        RATES_TO_USD.put("IDR", 7.374E-5);
        RATES_TO_USD.put("KYD", 1.20381561);
        RATES_TO_USD.put("AMD", 0.00208177);
        RATES_TO_USD.put("CYP", 2.02951);
        RATES_TO_USD.put("LBP", 6.6102E-4);
        RATES_TO_USD.put("JOD", 1.41043525);
        RATES_TO_USD.put("HKD", 0.12782575);
        RATES_TO_USD.put("AED", 0.27230327);
        RATES_TO_USD.put("EUR", 1.18847983);
        RATES_TO_USD.put("DKK", 0.15964571);
        RATES_TO_USD.put("BGL", 0.607174);
        RATES_TO_USD.put("ZWD", 0.00276319);
        RATES_TO_USD.put("CAD", 0.7794536);
        RATES_TO_USD.put("EEK", 0.0759165);
        RATES_TO_USD.put("MMK", 7.3573E-4);
        RATES_TO_USD.put("NOK", 0.11922475);
        RATES_TO_USD.put("MUR", 0.02954472);
        RATES_TO_USD.put("SYP", 0.00194179);
        RATES_TO_USD.put("IMP", 1.33822);
        RATES_TO_USD.put("ROL", 2.55917E-5);
        RATES_TO_USD.put("YUM", 0.61);
        RATES_TO_USD.put("RON", 0.25604955);
        RATES_TO_USD.put("LKR", 0.00654022);
        RATES_TO_USD.put("NGN", 0.00279053);
        RATES_TO_USD.put("IEP", 1.50883);
        RATES_TO_USD.put("CZK", 0.0461977);
        RATES_TO_USD.put("CRC", 0.00177387);
        RATES_TO_USD.put("PKR", 0.00903342);
        RATES_TO_USD.put("GRD", 0.0034865);
        RATES_TO_USD.put("XCD", 0.37002091);
        RATES_TO_USD.put("ANG", 0.56199382);
        RATES_TO_USD.put("AFA", 0.014417);
        RATES_TO_USD.put("SIT", 0.00495879);
        RATES_TO_USD.put("BHD", 2.65248472);
        RATES_TO_USD.put("PTE", 0.00591068);
        RATES_TO_USD.put("KZT", 0.00298655);
        RATES_TO_USD.put("LTL", 0.343989);
        RATES_TO_USD.put("TTD", 0.14906231);
        RATES_TO_USD.put("SAR", 0.26667022);
        RATES_TO_USD.put("YER", 0.0039951);
        RATES_TO_USD.put("MVR", 0.06489346);
        RATES_TO_USD.put("INR", 0.01560671);
        RATES_TO_USD.put("KRW", 9.2449E-4);
        RATES_TO_USD.put("NPR", 0.00978154);
        RATES_TO_USD.put("JPY", 0.00881482);
        RATES_TO_USD.put("MNT", 4.1243E-4);
        RATES_TO_USD.put("PLN", 0.28278943);
        RATES_TO_USD.put("GBP", 1.33824917);
        RATES_TO_USD.put("HUF", 0.00380076);
        RATES_TO_USD.put("BYR", 4.97056E-5);
        RATES_TO_USD.put("LUF", 0.0294563);
        RATES_TO_USD.put("BIF", 5.7122E-4);
        RATES_TO_USD.put("FIM", 0.199276);
        RATES_TO_USD.put("DEM", 0.605884);
        RATES_TO_USD.put("BAM", 0.607131);
        RATES_TO_USD.put("EGP", 0.05591528);
        RATES_TO_USD.put("MOP", 0.12448397);
        RATES_TO_USD.put("NAD", 0.0787166);
        RATES_TO_USD.put("SKK", 0.0394285);
        RATES_TO_USD.put("TMM", 5.71258E-5);
        RATES_TO_USD.put("PEN", 0.30601076);
        RATES_TO_USD.put("NZD", 0.70013499);
        RATES_TO_USD.put("FRF", 0.180632);
        RATES_TO_USD.put("BRL", 0.30324083);
        RATES_TO_USD.put("AWG", 0.55866078);
        RATES_TO_USD.put("RWF", 0.00116852);
        RATES_TO_USD.put("ETB", 0.03661825);
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
