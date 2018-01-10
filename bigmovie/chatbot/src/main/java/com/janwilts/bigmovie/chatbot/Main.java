package com.janwilts.bigmovie.chatbot;

import com.rivescript.Config;
import com.rivescript.RiveScript;

public class Main {
    public static void main(String[] args) {
        RiveScript bot = new RiveScript(Config.utf8());
        bot.loadDirectory("src/main/resources/rivescript");
        bot.sortReplies();
        
        String response = bot.reply("user", "hello bot");
        System.out.println(response);
        
    }
}
