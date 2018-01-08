package com.ykapps.p6p.models;

import java.util.Date;

public class Actor {

    private int actor_id;

    private String name;

    private int occurance;

    private char gender;

    private Date birth_date;

    public Actor(int actor_id, String name, int occurance, char gender, Date birth_date) {
        this.actor_id = actor_id;
        this.name = name;
        this.occurance = occurance;
        this.gender = gender;
        this.birth_date = birth_date;
    }

    public int getActor_id() {
        return actor_id;
    }

    public String getName() {
        return name;
    }

    public int getOccurance() {
        return occurance;
    }

    public char getGender() {
        return gender;
    }

    public Date getBirth_date() {
        return birth_date;
    }
}
