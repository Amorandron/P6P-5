package com.janwilts.bigmovie.chatbot;

import com.janwilts.bigmovie.chatbot.discord.DiscordBot;
import com.rivescript.RiveScript;

public class Main {
    public static void main(String[] args) {
        RiveScript bot = RivescriptBot.init();

        String token = args[0];
        DiscordBot discordBot = new DiscordBot(token, bot);
        discordBot.initialize();
    }
}
