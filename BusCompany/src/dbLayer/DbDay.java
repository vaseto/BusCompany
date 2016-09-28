package dbLayer;

import modelLayer.Day;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vasil Nedelchev on 2.5.2016 г..
 */
public class DbDay {
    private Connection con;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public DbDay() {
        con = DbConnection.getInstance().getDBcon();
    }
    
    /**
     * Method used when multiple results are expected
     * @param wClause
     * @return 
     */
    private List<Day> miscWhere(String wClause) {
        ResultSet resultSet;
        List<Day> days = new ArrayList<>();
        String query = buildQuery(wClause);
//        System.out.println("dbDays" + query);
        try {
            Statement statement = con.createStatement();
            statement.setQueryTimeout(5);
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Day day = buildDay(resultSet);
                days.add(day);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("query exception - select project" + e);
            e.printStackTrace();
        }
        return days;
    }

    /**
     * Builds a day with the given resultset
     * @param resultSet
     * @return
     * @throws SQLException 
     */
    private Day buildDay(ResultSet resultSet) throws SQLException {
        Day day = new Day();

            day.setDayID(resultSet.getInt(1));
            day.setDay(resultSet.getString(2));
        return day;

    }
    
    /**
     * Returns a preparedstatement with the given query
     * @param query
     * @return 
     */
    private PreparedStatement getPreparedStmt(String query) {
        try {
        //    System.out.println("prepareStatement " + query);

            preparedStatement = con.prepareStatement(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return preparedStatement;
    }
    public List<Day> getAllDays(){
        return miscWhere("");
    }

    /**
     * Builds a query for accessing the database
     * @param wClause
     * @return 
     */
    private String buildQuery(String wClause) {
        String query = "SELECT * FROM Days";
        if (wClause.length() > 0) {
            query = query + "where" + wClause;
        }
        return query;
    }
    
    /**
     * Returns a day by its id
     * @param id
     * @return
     * @throws SQLException 
     */
    public Day getDayByID(int id) throws SQLException {
        String query = "SELECT * FROM  Days WHERE dayID = ?";
        preparedStatement = getPreparedStmt(query);
        Day day = new Day();
        ResultSet resultSet;


        preparedStatement.setInt(1, id);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            day = buildDay(resultSet);
        }
        return day;
    }
    
    /**
     * returns a day by its name
     * @param strDay
     * @return
     * @throws SQLException 
     */
    public Integer getDayIDbyDay(String strDay) throws SQLException {
        String query = "if exists(select dayID FROM Days WHERE day= ? )SELECT dayID FROM  Days WHERE day = ?";
        preparedStatement = getPreparedStmt(query);

        ResultSet resultSet;
        preparedStatement.setString(1, strDay);
        preparedStatement.setString(2, strDay);
        boolean test = preparedStatement.execute();
        Integer id = null;
        if(test) {
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        }else {
            Day day = new Day(strDay);
            createDay(day);
            id = day.getDayID();
        }
        return id  ;
    }
    
    /**
     * Remove a day from the database with the id
     * @param id
     * @return
     * @throws SQLException 
     */
    public int deleteDaybyID(int id) throws SQLException {
        String query = " DELETE FROM Days "
                + "WHERE dayID =? ";

        preparedStatement = getPreparedStmt(query);
        preparedStatement.setInt(1, id);
        int test = preparedStatement.executeUpdate();

   //     System.out.println("Day is deleted from table Days with  id: "+ id+" !");
        return test;
    }
    
    /**
     * Inserts a day into the database
     * @param day
     * @throws SQLException 
     */
    public void createDay(Day day) throws SQLException {
        String query = "insert into Days(day) "

                + " Values (?) Select Scope_Identity()";

        preparedStatement = getPreparedStmt(query);

        preparedStatement.setString(1, day.getDay());

        preparedStatement.executeUpdate();
        preparedStatement.getGeneratedKeys();
        resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            day.setDayID((resultSet.getInt(1)));
        }
     //   System.out.println("Record is inserted into Days tables!");
    }

    /**
     * Returns a day with the given day name
     * @param strDay
     * @return
     * @throws SQLException 
     */
    public Day getDayByDay(String strDay) throws SQLException {
        Day day = new Day(strDay);
        day.setDayID(getDayIDbyDay(strDay));
        return day;
    }
}
