package com.janwilts.bigmovie.parser.parsers;

import com.janwilts.bigmovie.parser.util.CurrencyConverter;

import java.io.File;
import java.io.IOException;

public class BusinessParser extends Parser
{
    public BusinessParser(File file)
    {
        super(file);
    }
    
    @Override
    public void parse()
    {
        try
        {
            String MV = ""; //movie
            String BT = ""; //budget in USD
            
            String line = "";
            while ((line = reader.readLine()) != null)
            {
                if (line.trim().isEmpty()) continue;
                if (line.startsWith("MV: ") && !(line.charAt(4) == '"'))
                {
                    MV = line.substring(4, line.length());
                    System.out.println("Movie: " + MV);
                }
                else if (!MV.equals(""))
                {
                    if (line.startsWith("BT: "))
                    {
                        String[] values = line.split(" ");
                        String currency = values[1];
                        double amount = Double.parseDouble(values[2].replaceAll(",", ""));
                        
                        if (currency.equals("USD")) BT = String.format("%.2f", amount);
                        else BT = String.format("%.2f", CurrencyConverter.convert(amount, currency, "USD"));
                        
                        System.out.println("Budget: $" + BT);
                    }
                }
                if (line.equals("-------------------------------------------------------------------------------"))
                {
                    if (!MV.isEmpty())
                    {
                        //TODO use the values before resetting
                        
                        MV = "";
                        BT = "";
                    }
                }
                if (line.equals("                                    =====")) break;
            }
            CurrencyConverter.convert(1, "EUR", "USD");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
