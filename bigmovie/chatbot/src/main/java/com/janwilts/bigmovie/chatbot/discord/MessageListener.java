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
        if (event.getMessage().getMentions().size() == 1 && event.getMessage().getMentions().contains(event.getClient().getOurUser())) {
            
            String message = input.substring(input.indexOf(">") + 1, input.length());
            String response = bot.reply(event.getAuthor().getDisplayName(event.getGuild()), message);
            event.getChannel().sendMessage(response);
        }
    }
}
