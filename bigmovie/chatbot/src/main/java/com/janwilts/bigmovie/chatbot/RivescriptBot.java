package com.janwilts.bigmovie.chatbot;

import com.janwilts.bigmovie.chatbot.discord.DiscordBot;
import com.janwilts.bigmovie.chatbot.subroutines.*;
import com.rivescript.Config;
import com.rivescript.RiveScript;

import java.net.URISyntaxException;

public class RivescriptBot {
    
    public static RiveScript init(DiscordBot discordBot) {
        RiveScript bot = new RiveScript(Config.Builder.utf8().unicodePunctuation("[,!?;:]").build());

        String rivescriptDir = "src/main/resources/rivescript";

        try {
            rivescriptDir = Main.class.getResource("/rivescript/").toURI().getRawPath().replaceAll("%20", " ");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        bot.loadDirectory(rivescriptDir);

        bot.sortReplies();
        bot.setSubroutine("movieEarned", new MovieEarnedSubroutine(discordBot));
        bot.setSubroutine("movieCost", new MovieCostSubroutine(discordBot));
        bot.setSubroutine("movieMpaa", new MovieMpaaSubroutine(discordBot));
        bot.setSubroutine("actorAct", new ActorActSubroutine(discordBot));
        bot.setSubroutine("actorRole", new ActorRoleSubroutine(discordBot));
        bot.setSubroutine("soundtrackUsed", new SoundTrackUsedSubroutine(discordBot));
        bot.setSubroutine("moviecountryEarned", new MovieCountryEarnedSubroutine(discordBot));
        bot.setSubroutine("moviecountryCount", new MovieCountryCountSubroutine(discordBot));
        bot.setSubroutine("genrepopularityChange", new GenrePopularityChangeSubroutine(discordBot));
        bot.setSubroutine("getroleDifficult", new GetRoleDifficultSubroutine(discordBot));
        bot.setSubroutine("movieByCountry", new MovieByCountrySubroutine(discordBot));
        bot.setSubroutine("moreMovieInfo", new MoreMovieInfoSubroutine(discordBot));
        bot.setSubroutine("moreActorInfo", new MoreActorInfoSubroutine(discordBot));
        bot.setSubroutine("moreInfo", new MoreInfoSubroutine(discordBot));
        bot.setSubroutine("actorInfo", new ActorInfoSubroutine(discordBot));
        bot.setSubroutine("movieInfo", new MovieInfoSubroutine(discordBot));
        bot.setSubroutine("LUISCall", new LUISCallSubroutine(discordBot));

        return bot;
    }
}
