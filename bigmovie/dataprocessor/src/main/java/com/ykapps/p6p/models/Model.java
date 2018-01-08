package com.ykapps.p6p.models;

import javafx.util.Pair;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.util.List;
import java.util.function.Predicate;

public abstract class Model {
    public DataSource db;

    public Model(DataSource db) {
        this.db = db;
    }

    public abstract List<List<Pair>> get(String whereClause);
}
