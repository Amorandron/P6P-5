package com.ykapps.bigmovie.models;

import java.math.BigDecimal;
import java.sql.Date;

public class Gross {

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

    public Integer getMovie_id() {
        return movie_id;
    }

    public Integer getCountry_id() {
        return country_id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Date getTransaction_date() {
        return transaction_date;
    }
}
