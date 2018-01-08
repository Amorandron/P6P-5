package com.janwilts.bigmovie.parser.parsers;

import com.janwilts.bigmovie.parser.util.CurrencyConverter;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Lars
 */
public class BusinessParser extends Parser
{
    public static final String QUOTE = "\"";
    
    private static final Map<String, String> MONTH_TO_NUMBER = new HashMap<>();
    
    static
    {
        MONTH_TO_NUMBER.put("January", "01");
        MONTH_TO_NUMBER.put("February", "02");
        MONTH_TO_NUMBER.put("March", "03");
        MONTH_TO_NUMBER.put("April", "04");
        MONTH_TO_NUMBER.put("May", "05");
        MONTH_TO_NUMBER.put("June", "06");
        MONTH_TO_NUMBER.put("July", "07");
        MONTH_TO_NUMBER.put("August", "08");
        MONTH_TO_NUMBER.put("September", "09");
        MONTH_TO_NUMBER.put("October", "10");
        MONTH_TO_NUMBER.put("November", "11");
        MONTH_TO_NUMBER.put("December", "12");
    }
    
    public BusinessParser(File file)
    {
        super(file);
    }
    
    @Override
    public void parse()
    {
        try (PrintWriter writer = new PrintWriter(this.csv, "UTF-8"))
        {
            String movie = ""; //movie
            double budget = 0; //budget in USD
            
            /*Map<Country, Map<Date, Amount>>*/
            Map<String, Map<String, Double>> grossPerCountry = new HashMap<>();
            
            String line;
            while ((line = this.readLine()) != null)
            {
                if (line.trim().isEmpty()) continue;
                if (line.startsWith("MV: ") && !(line.charAt(4) == '"')) movie = readMovie(line);
                else if (line.startsWith("BT: ")) budget = readBudget(line);
                else if (line.startsWith("GR: ")) readGrossToMap(line, grossPerCountry);
                
                else if (line.equals("-------------------------------------------------------------------------------"))
                {
                    if (!movie.isEmpty())
                    {
                        writeFileToCSV(writer, movie, budget, grossPerCountry);
                        
                        movie = "";
                        budget = 0;
                        grossPerCountry.clear();
                    }
                }
                else if (line.equals("                                    =====")) break;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    private void writeFileToCSV(PrintWriter writer, String movie, double budget, Map<String, Map<String, Double>> grossPerCountry)
    {
        if (grossPerCountry.isEmpty()) writeLineToCSV(writer, movie, budget, "", "", 0);
        else
        {
            for (Map.Entry<String, Map<String, Double>> grossPerCountryEntry : grossPerCountry.entrySet())
            {
                String country = grossPerCountryEntry.getKey();
                for (Map.Entry<String, Double> grossPerDateEntry : grossPerCountryEntry.getValue().entrySet())
                {
                    String date = grossPerDateEntry.getKey();
                    Double gross = grossPerDateEntry.getValue();
                    writeLineToCSV(writer, movie, budget, country, date, gross);
                }
            }
        }
    }
    
    private void writeLineToCSV(PrintWriter writer, String movie, double budget, String country, String date, double gross)
    {
        writer.println(String.join(",", addQuotes(movie), formatDouble(budget), addQuotes(country), date, formatDouble(gross)));
    }
    
    private String addQuotes(String input)
    {
        return "\"" + input + "\"";
    }
    
    private String formatDouble(double amount)
    {
        return amount == 0 ? "" : String.format("%.2f", amount).replace(".00", "");
    }
    
    private String readMovie(String line)
    {
        String movie = line.substring(4, line.length());
        
        movie = movie.replace("(TV)", "");
        movie = movie.replace("(V)", "");
        
        return movie;
    }
    
    private double readBudget(String line)
    {
        String[] values = line.split(" ");
        String currency = values[1];
        double amount = Double.parseDouble(values[2].replaceAll(",", ""));
        
        if (currency.equals("USD")) return amount;
        else return CurrencyConverter.convert(amount, currency, "USD");
    }
    
    private void readGrossToMap(String line, Map<String, Map<String, Double>> map)
    {
        String[] values = line.split("\\(");
        String[] money = values[0].split(" ");
        String currency = money[1];
        double amount = Double.parseDouble(money[2].replaceAll(",", ""));
        double parsedGross;
        
        if (currency.equals("USD")) parsedGross = amount;
        else parsedGross = CurrencyConverter.convert(amount, currency, "USD");
        
        String country = values[1].substring(0, values[1].indexOf(')'));
        String date = (values.length > 2 && Character.isDigit(values[2].charAt(0))) ? values[2].substring(0, values[2].indexOf(')')) : "";
        date = fixDate(date);
        
        if (map.containsKey(country)) map.get(country).put(date, amount);
        else
        {
            Map<String, Double> grossPerDate = new HashMap<>();
            grossPerDate.put(date, parsedGross);
            map.put(country, grossPerDate);
        }
    }
    
    private String fixDate(String date)
    {
        String[] values = date.split(" ");
        if (values.length == 1) return (values[0].isEmpty() ? "" : "0101") + date;
        else
        {
            String day = values[0].length() == 2 ? values[0] : "0" + values[0];
            String month = MONTH_TO_NUMBER.get(values[1]);
            String year = values[2];
            return String.join("", day, month, year);
        }
    }
    
}
