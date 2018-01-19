package com.janwilts.bigmovie.chatbot.models;

import java.math.BigDecimal;
import java.sql.Date;

public class Gross implements Model{

    private Integer gross_id;

    private Integer movie_id;

    private Integer country_id;

    private BigDecimal amount;

    private Date transaction_date;

    public Gross(Integer gross_id, Integer movie_id, Integer country_id, BigDecimal amount, Date transaction_date) {
        this.gross_id = gross_id;
        this.movie_id = movie_id;
        this.country_id = country_id;
        this.amount = amount;
        this.transaction_date = transaction_date;
    }

    public Integer getGross_id() {
        return gross_id;
    }

    public void setGross_id(Integer gross_id) {
        this.gross_id = gross_id;
    }

    public Integer getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(Integer movie_id) {
        this.movie_id = movie_id;
    }

    public Integer getCountry_id() {
        return country_id;
    }

    public void setCountry_id(Integer country_id) {
        this.country_id = country_id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(Date transaction_date) {
        this.transaction_date = transaction_date;
    }
}
