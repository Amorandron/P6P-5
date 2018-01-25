package com.janwilts.bigmovie.chatbot.models;

import java.util.List;

public class LUISResponse {

    private String query;

    private LUISTopScoringIntent topScoringIntent;

    private List<LUISEntity> entities;

    public LUISResponse() {

    }

    public LUISResponse(String query, LUISTopScoringIntent topScoringIntent, List<LUISEntity> entities) {
        this.query = query;
        this.topScoringIntent = topScoringIntent;
        this.entities = entities;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<LUISEntity> getEntities() {
        return entities;
    }

    public void setEntities(List<LUISEntity> entities) {
        this.entities = entities;
    }

    public LUISTopScoringIntent getTopScoringIntent() {
        return topScoringIntent;
    }

    public void setTopScoringIntent(LUISTopScoringIntent topScoringIntent) {
        this.topScoringIntent = topScoringIntent;
    }
}
