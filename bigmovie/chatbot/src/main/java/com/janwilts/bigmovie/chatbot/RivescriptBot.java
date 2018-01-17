package com.janwilts.bigmovie.chatbot;

import com.janwilts.bigmovie.chatbot.subroutines.*;
import com.rivescript.Config;
import com.rivescript.RiveScript;

public class RivescriptBot {
    
    public static RiveScript init() {
        RiveScript bot = new RiveScript(Config.utf8());
        
        bot.loadDirectory("src/main/resources/rivescript");
        bot.sortReplies();
        bot.setSubroutine("movieEarned", new MovieEarnedSubroutine());
        bot.setSubroutine("movieCost", new MovieCostSubroutine());
        bot.setSubroutine("movieMpaa", new MovieRatingMpaaSubroutine());
        bot.setSubroutine("actorAct", new ActorActSubroutine());
        bot.setSubroutine("actorRole", new ActorRoleSubroutine());
        bot.setSubroutine("soundtrackUsed", new SoundTrackUsedSubroutine());
        bot.setSubroutine("moviecountryEarned", new MovieCountryEarnedSubroutine());
        bot.setSubroutine("moviecountryCount", new MovieCountryCountSubroutine());
        bot.setSubroutine("genrepopularityChange", new GenrePopularityChangeSubroutine());
        bot.setSubroutine("getroleDifficult", new GetRoleDifficultSubroutine());
        
        return bot;
    }
}
