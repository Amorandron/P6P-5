package com.janwilts.bigmovie.chatbot.discord;

import com.rivescript.RiveScript;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class MessageListener implements IListener<MessageReceivedEvent> {
    private RiveScript bot;
    
    public MessageListener(RiveScript bot) {
        this.bot = bot;
    }
    
    @Override
    public void handle(MessageReceivedEvent event) {
        String input = event.getMessage().getContent();
        if (event.getMessage().getContent().startsWith(DiscordBot.CLIENT_ID)) {
            
            String message = input.substring(DiscordBot.CLIENT_ID.length(), input.length()).trim();
            if (message.equals("purge")) {
                event.getChannel().bulkDelete();
                bot.clearAllUservars();
            }
            else {
                String response = bot.reply(event.getAuthor().getDisplayName(event.getGuild()), message);
                event.getChannel().sendMessage(response);
            }
        }
    }
}
