package com.ykapps.p6p.models;

import javafx.util.Pair;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Movie extends Model{

    public Movie(DataSource db) {
        super(db);
    }


    @Override
    public List<List<Pair>> get(String whereClause) {
        try(Connection conn = db.getConnection()){
            PreparedStatement st = conn.prepareStatement("SELECT id, name FROM newtable WHERE id = 0;");

            List<List<Pair>> results = new ArrayList<>();

            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                Pair idPair = new Pair<String, Integer>("ID", rs.getInt("ID"));
                Pair namePair = new Pair<String, String>("NAME", rs.getString("NAME"));

                List<Pair> result = new ArrayList<>();

                result.add(idPair);
                result.add(namePair);

                results.add(result);
            }

            st.close();

            return results;
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
