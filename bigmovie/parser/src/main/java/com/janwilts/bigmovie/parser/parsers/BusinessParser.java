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
            String movie = "";
            String budgetUSD = "";
            
            String line;
            while ((line = reader.readLine()) != null)
            {
                if (line.startsWith("MV: "))
                {
                    movie = line.substring(4, line.length());
                    System.out.println("Movie: " + movie);
                }
                
                if (line.startsWith("BT: "))
                {
                    String[] values = line.split(" ");
                    String currency = values[1];
                    double amount = Double.parseDouble(values[2].replaceAll(",", ""));
                    
                    if (currency.equals("USD")) budgetUSD = amount + "";
                    else budgetUSD = CurrencyConverter.convert(amount, currency, "USD") + "";
                    
                    System.out.println("Budget: $" + budgetUSD);
                }
                
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
