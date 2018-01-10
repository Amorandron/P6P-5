package com.janwilts.bigmovie.chatbot.discord;

import com.rivescript.RiveScript;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

public class DiscordBot {
    
    private IDiscordClient discordClient;
    private RiveScript bot;
    
    public DiscordBot(String token, RiveScript bot) {
        discordClient = getClient(token, true);
        this.bot = bot;
    }
    
    public void initialize() {
        discordClient.getDispatcher().registerListener(new MessageListener(bot));
    }
    
    private IDiscordClient getClient(String token, boolean login) {
        ClientBuilder clientBuilder = new ClientBuilder(); // Creates the ClientBuilder instance
        clientBuilder.withToken(token); // Adds the login info to the builder
        try {
            if (login) {
                return clientBuilder.login(); // Creates the client instance and logs the client in
            }
            else {
                return clientBuilder.build(); // Creates the client instance but it doesn't log the client in yet, you would have to call client.login() yourself
            }
        }
        catch (DiscordException e) { // This is thrown if there was a problem building the client
            e.printStackTrace();
            return null;
        }
    }
}