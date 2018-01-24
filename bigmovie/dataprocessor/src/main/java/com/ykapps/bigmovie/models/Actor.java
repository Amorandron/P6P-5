package com.ykapps.bigmovie.models;

import java.sql.Date;

public class Actor {

    private Long actor_id;

    private String name;

    private Integer occurence;

    private String gender;

    private Date birth_date;

    private Date death_date;

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

    public String getName() {
        return name;
    }

    public Integer getOccurence() {
        return occurence;
    }

    public String getGender() {
        return gender;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public Date getDeath_date() { return death_date; }
}
