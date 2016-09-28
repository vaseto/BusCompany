/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelLayer.Employee;
import modelLayer.Person;

/**
 *
 * @author Delyan
 */
public class DbEmployee extends DbClass{
    
    private Connection con;
    private DbPerson dbPerson;
    private String from = "Employees";
    public DbEmployee(){
        con = getCon();
        dbPerson = new DbPerson();
    }
   
    /**
     * Method used when more than one result is expected
     * @param wClause to be used for the search
     * @return list of all employees matching the clause
     * @throws SQLException 
     */
    private List<Employee> missWhere(String wClause) throws SQLException {
        List<Employee> list = new ArrayList<>();
        ResultSet results = getResultSet(wClause,from);
        while (results.next()) {
            list.add(buildEmployee(results));
        }
        return list;
    }
    
    /**
     * Return all employees from the databases
     * @return
     * @throws SQLException 
     */
    public List<Employee> getAllEmployees() throws SQLException{
        return missWhere("");
    }
    
    /**
     * Returns an employee with the given id
     * @param id
     * @return
     * @throws SQLException 
     */
    public Employee getEmployee(int id) throws SQLException{
        return singleWhere("eID = '"+ id + "'" );
    }
    
    /**
     * Transforms a person into Employee object
     * @param results
     * @return
     * @throws SQLException 
     */
    private Employee buildEmployee(ResultSet results) throws SQLException {
        Person p = dbPerson.getPersonByyId(results.getInt(1));
            return new Employee(p.getFirstName(), p.getLastName(), p.getEmail(), p.getTelephone(),p.getPassword(), p.getCardInfo(), results.getString(2), results.getDouble(3), p.getId());
    }
    
    /**
     * Method used when a single result is expected
     * @param wClause the clause to be used 
     * @return employee object
     * @throws SQLException 
     */
    private Employee singleWhere(String wClause) throws SQLException {
        ResultSet results = getResultSet(wClause,from);
        results.next();
        return buildEmployee(results);
    }
    
    /**
     * Returns prepare statement for insertion employees
     * @return
     * @throws SQLException 
     */
    private PreparedStatement getPrepStatForInsert() throws SQLException {
        return con.prepareStatement("Insert into Employees (eID,position,salary) values (?,?,?)");
    }

    /**
     * Returns prepare statement for updating employees
     * @return
     * @throws SQLException 
     */
    private PreparedStatement getPrepStatForUpdate() throws SQLException {
        return con.prepareStatement("Update Employees set position = ?, salary = ? where eID = ? ");
    }
    
    /**
     * Returns a prepared statement with all employees data set up
     * @param prSt the statement to receive the employee date
     * @param e the employee which data to be get
     * @return Prepare statement
     * @throws SQLException 
     */
    private PreparedStatement getPrivateFields(int eId,PreparedStatement prSt, Employee e) throws SQLException{
        prSt.setInt(1, eId);
        prSt.setString(2,e.getPosition());
        prSt.setDouble(3,e.getSalary());     
        return prSt;
    }
    
    /**
     * Inserts an employee into the database
     * @param e the employee
     * @return 1 if successful
     * @throws SQLException 
     */
    public int insert(Employee e) throws SQLException {
        DbConnection.starTransaction();
        int index = getPrivateFields(dbPerson.insert(e),getPrepStatForInsert(), e).executeUpdate();
        DbConnection.commitTransaction();
        return index;
    }
    
    /**
     * Updates employee 
     * @param e the employee to be updated
     * @return 1 if successful
     * @throws SQLException 
     */
    public int update(Employee e) throws SQLException{
        PreparedStatement prSt = getPrepStatForUpdate();
        DbConnection.starTransaction();
        dbPerson.update(e);
        prSt.setString(1, e.getPosition());
        prSt.setDouble(2, e.getSalary());
        prSt.setInt(3, e.getId());
        int index = prSt.executeUpdate();
        DbConnection.commitTransaction();
        return index;
    }
    
    public static void main(String args[]){
        
    }

    
}
