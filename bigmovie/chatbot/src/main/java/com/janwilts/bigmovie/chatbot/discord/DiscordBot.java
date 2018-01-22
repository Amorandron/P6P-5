package com.janwilts.bigmovie.chatbot.discord;

import com.rivescript.RiveScript;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

public class DiscordBot {
    
    public static String CLIENT_ID = "<@!400614142534221824>";
    
    private IDiscordClient discordClient;
    private RiveScript bot;
    
    public DiscordBot(String token) {
        discordClient = getClient(token);
    }

    public void setRivescript(RiveScript bot) {
        this.bot = bot;
    }

    public void initialize() {
        discordClient.getDispatcher().registerListener(new MessageListener(bot));
    }
    
    private IDiscordClient getClient(String token) {
        ClientBuilder clientBuilder = new ClientBuilder(); // Creates the ClientBuilder instance
        clientBuilder.withToken(token); // Adds the login info to the builder
        try {
            return clientBuilder.login(); // Creates the client instance and logs the client in
        }
        catch (DiscordException e) { // This is thrown if there was a problem building the client
            e.printStackTrace();
            return null;
        }
    }
}