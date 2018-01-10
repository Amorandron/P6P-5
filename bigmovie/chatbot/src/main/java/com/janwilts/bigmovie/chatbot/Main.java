package com.janwilts.bigmovie.chatbot;

import com.janwilts.bigmovie.chatbot.discord.DiscordBot;
import com.rivescript.Config;
import com.rivescript.RiveScript;

public class Main {
    public static void main(String[] args) {
        RiveScript bot = new RiveScript(Config.utf8());
        bot.loadDirectory("src/main/resources/rivescript");
        bot.sortReplies();
        bot.setSubroutine("hash", new HashSubroutine());
//        String response = bot.reply("user", "hello bot");
//        System.out.println(response);
//
        DiscordBot discordBot = new DiscordBot(args[0], bot);
        discordBot.initialize();
    }
}
