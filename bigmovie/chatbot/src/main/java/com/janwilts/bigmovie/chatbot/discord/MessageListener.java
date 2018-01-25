package com.janwilts.bigmovie.chatbot.discord;

import com.rivescript.RiveScript;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.RequestBuffer;

import java.io.File;
import java.io.FileNotFoundException;

public class MessageListener implements IListener<MessageReceivedEvent> {
    private RiveScript bot;
    
    public MessageListener(RiveScript bot) {
        this.bot = bot;
    }
    
    @Override
    public void handle(MessageReceivedEvent event) {
        bot.setUservar(event.getAuthor().getName(),"name", event.getAuthor().getName());
        bot.setUservar(event.getAuthor().getName(), "origMessage", event.getMessage().getContent());
        String input = event.getMessage().getContent();
        if (event.getMessage().getContent().startsWith(DiscordBot.CLIENT_ID)) {
            
            String message = input.substring(DiscordBot.CLIENT_ID.length(), input.length()).trim();
            if (message.equals("purge")) {
                event.getChannel().bulkDelete();
                bot.clearAllUservars();
            }
            else {
                String response = bot.reply(event.getAuthor().getDisplayName(event.getGuild()), message);
                String[] messages = response.split("\\|\\|");
                for(String mess : messages) {
                    if(mess.trim().startsWith("{{file}}")) {
                        try {
                            event.getChannel().sendFile(new File(mess.trim().substring(8)));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        RequestBuffer.request(() -> {
                            try {
                                event.getChannel().sendMessage(mess);
                            }
                            catch (RateLimitException e) {
                                System.out.println("Rate limit reached, retrying..");
                                throw e;
                            }
                        }).get();
                    }
                }
            }
        }
    }
}
