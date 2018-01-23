package com.janwilts.bigmovie.chatbot.models;

import java.sql.Date;

public class Actor implements Model {

    private Long actor_id;

    private String name;

    private Integer occurence;

    private String gender;

    private Date birth_date;

    private Date death_date;

    public Actor() {
    }

    public Actor(Long actor_id, String name, Integer occurence, String gender, Date birth_date, Date death_date) {
        this.actor_id = actor_id;
        this.name = name;
        this.occurence = occurence;
        this.gender = gender;
        this.birth_date = birth_date;
        this.death_date = death_date;
    }

    public Long getActor_id() {
        return actor_id;
    }

    public void setActor_id(Long actor_id) {
        this.actor_id = actor_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOccurence() {
        return occurence;
    }

    public void setOccurence(Integer occurence) {
        this.occurence = occurence;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public Date getDeath_date() { return death_date; }

    public void setDeath_date(Date death_date) { this.death_date = death_date; }
}
