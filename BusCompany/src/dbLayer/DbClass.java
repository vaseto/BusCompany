/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbLayer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class to be used to provide basics functions for all of the dbClasses
 * @author Delyan
 */
abstract class DbClass {

    private Connection con;

    public DbClass() {
        con = DbConnection.getInstance().getDBcon();
    }
    /**
     * 
     * @param wClause the clause to be searched for
     * @param from from which table to be selected
     * @return the result set of the of query
     * @throws SQLException 
     */
    public ResultSet getResultSet(String wClause, String from) throws SQLException {
        Statement statement = con.createStatement();
        statement.setQueryTimeout(5);
        return statement.executeQuery(buildQuery(wClause, from));
    }

    public Connection getCon() {
        return con;
    }
    
    /**
     * Builds a query to be used for searching
     * @param wClause the clause to be searched with
     * @param from the table in which to be searched
     * @return the query to be used
     */
    private String buildQuery(String wClause, String from) {
        String query = "select * from  " + from;
        if (wClause.length() > 0) {
            query = query + " where " + wClause;
        }
        return query;
    }
}
