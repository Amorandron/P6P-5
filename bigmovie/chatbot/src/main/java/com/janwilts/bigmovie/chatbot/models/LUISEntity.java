package com.janwilts.bigmovie.chatbot.models;

public class LUISEntity {

    private String entity;

    private String type;

    private int startIndex;

    private int endIndex;

    private double score;

    public LUISEntity() {

    }

    public LUISEntity(String entity) {
        this.entity = entity;
        this.type = "";
        this.startIndex = 0;
        this.endIndex = 0;
        this.score = 0;
    }

    public LUISEntity(String entity, String type, int startIndex, int endIndex, double score) {
        this.entity = entity;
        this.type = type;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.score = score;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
