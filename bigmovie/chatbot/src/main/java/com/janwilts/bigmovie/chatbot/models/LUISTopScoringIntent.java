package com.janwilts.bigmovie.chatbot.models;

public class LUISTopScoringIntent {

    private String intent;

    private double score;

    public LUISTopScoringIntent() {

    }

    public LUISTopScoringIntent(String intent, double score) {
        this.intent = intent;
        this.score = score;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
