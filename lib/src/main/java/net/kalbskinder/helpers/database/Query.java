package net.kalbskinder.helpers.database;

import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;

@RequiredArgsConstructor
public class Query {
    private final Connection connection;

    public Object getSingleValue(String query) throws SQLException {
        var statement = this.connection.createStatement();
        var resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            return resultSet.getObject(1);
        }
        return null;
    }

     public void execute(String query) throws SQLException {
         var statement = this.connection.createStatement();
         statement.execute(query);
     }
}
