package com.janwilts.bigmovie.chatbot;

import com.janwilts.bigmovie.chatbot.discord.DiscordBot;
import com.rivescript.RiveScript;

public class Main {
    public static void main(String[] args) {
        String token = args[0];
        DiscordBot discordBot = new DiscordBot(token);
        RiveScript bot = RivescriptBot.init(discordBot);
        discordBot.setRivescript(bot);
        discordBot.initialize();
    }
}
