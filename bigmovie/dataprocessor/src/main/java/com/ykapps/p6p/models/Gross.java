package com.ykapps.p6p.models;

import java.math.BigDecimal;
import java.util.Date;

public class Gross {

    private int gross_id;

    private int movie_id;

    private int country_id;

    private BigDecimal amount;

    private Date date;

    public Gross(int gross_id, int movie_id, int country_id, BigDecimal amount, Date date) {
        this.gross_id = gross_id;
        this.movie_id = movie_id;
        this.country_id = country_id;
        this.amount = amount;
        this.date = date;
    }

    public int getGross_id() {
        return gross_id;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public int getCountry_id() {
        return country_id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }
}
