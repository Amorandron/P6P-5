package com.janwilts.bigmovie.chatbot.subroutines;

import com.google.common.base.Strings;
import com.janwilts.bigmovie.chatbot.discord.DiscordBot;
import com.janwilts.bigmovie.chatbot.models.Actor;
import com.janwilts.bigmovie.chatbot.util.PrintUtils;
import com.rivescript.RiveScript;

import java.util.Map;

public class MoreActorInfoSubroutine extends Routine {
    private Map<?, ? > source;

    public MoreActorInfoSubroutine(DiscordBot bot, Map<?, ?> source) {
        super(bot);
        this.source = source;
    }

    public MoreActorInfoSubroutine(DiscordBot bot) {
        super(bot);
    }

    @Override
    public String call(RiveScript rs, String[] args) {
        StringBuilder result = new StringBuilder();

        int position = Integer.parseInt(args[0]);
        Actor actor = null;
        if (source != null)
            actor = (Actor) source.get(position);
        else
            actor = focusedActors.get(position);

        String gender = actor.getGender().equals("M") ? "Male" : "Female";

        PrintUtils.blockprint(String.format("Name: %s", actor.getName()));
        PrintUtils.blockprint("----------------");
        if (!Strings.isNullOrEmpty(actor.getGender()))
            PrintUtils.blockprint(String.format("Gender: %s", gender));
        if (actor.getBirth_date() != null)
            PrintUtils.blockprint(String.format("Birth Date: %s", actor.getBirth_date().toString()));
        if (actor.getDeath_date() != null)
            PrintUtils.blockprint(String.format("Death Date: %s", actor.getDeath_date().toString()));

        return PrintUtils.getBlock();
    }
}
