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
import modelLayer.Payment;

/**
 *
 * @author Delyan
 */
public class DbPayment extends DbClass{
    
    private Connection con;
    private String from = "Payments ";
    public DbPayment(){
        con = getCon();
    }
    
    /**
     * Returns all payments in the database
     * @return
     * @throws SQLException 
     */
    public List<Payment> getAllPayments() throws SQLException {
        return missWhere("");
    }
    
    /**
     * Return all payments for tickets
     * @return
     * @throws SQLException 
     */
    public List<Payment> getAllPaymentsForTickets() throws SQLException{
        return missWhere("type = 2");
    }
    
    /**
     * Return all payments for reservations
     * @return
     * @throws SQLException 
     */
    public List<Payment> getAllPaymentsForReservations() throws SQLException{
        return missWhere("type = 1");
    }

    /**
     * Builds a payment object from the result set
     * @param results the set to be used to build the payment
     * @return the builded object
     * @throws SQLException 
     */
    private Payment buildPayment(ResultSet results) throws SQLException {
       return new Payment(results.getDate(2),results.getDouble(3), results.getInt(1));
    }

    /**
     * 
     * @param wClause
     * @return
     * @throws SQLException 
     */
    private Payment singleWhere(String wClause) throws SQLException {
        ResultSet results = getResultSet(wClause,from);
       results.next();
       return buildPayment(results);
    }
    
    /**
     * Tries to return a list of payments matching the w clause
     * @param wClause the clause to be used to find the payment
     * @return list of all payments
     * @throws SQLException 
     */
    protected List<Payment> missWhere(String wClause) throws SQLException {
        List<Payment> list = new ArrayList<>();
        try{
        ResultSet results = getResultSet(wClause,from);
       
        while (results.next()) {
            list.add(buildPayment(results));
        }
        }catch(Exception e){
            return null;
        }
        return list;
    }

    /**
     * Returns a prepare statement for inserting
     * @return
     * @throws SQLException 
     */
    private PreparedStatement getPrepStatForInsert() throws SQLException {
        return con.prepareStatement("Insert into Payments (date,amount,type) values (?,?,?) Select Scope_Identity()");
    }

    /**
     * Returns a prepare statement for updating
     * @return
     * @throws SQLException 
     */
    private PreparedStatement getPrepStatForUpdate() throws SQLException {
        return con.prepareStatement("Update Payments set date = ?,amount = ? where paymentID = ? ");
    }
    
    /**
     * Returns a payment object with the given id
     * @param id the id to be searched with
     * @return
     * @throws SQLException 
     */
    public Payment getPayment(int id) throws SQLException {
        return singleWhere("paymentID = " + id + "");
    }
    
    /**
     * Returns a sql date 
     * @param uDate the util date to be transfarmed
     * @return  sql date
     */
    private static java.sql.Date convertUtilToSql(java.util.Date uDate) {
        return new java.sql.Date(uDate.getTime()); 
    }
    
    
    /**
     * Insert payment into the database
     * @param payment the payment object
     * @param type the type of the payment
     * @return the payment object
     * @throws SQLException 
     */
    public Payment insert(Payment payment,int type) throws SQLException{
        PreparedStatement prSt = getPrepStatForInsert();
         prSt.setDate(1,convertUtilToSql(payment.getDate()));
        prSt.setDouble(2, payment.getAmount());
        prSt.setString(3, type+"");
        prSt.executeUpdate();
        ResultSet results = prSt.getGeneratedKeys();
        if(results.next()){
          payment.setId(results.getInt(1));
        }
        return payment;
    }
    
    /**
     * Updates a payment record
     * @param payment to be updated
     * @return 1 if successful
     * @throws SQLException 
     */
    public int update(Payment payment) throws SQLException {
        PreparedStatement prSt = getPrepStatForUpdate();
        prSt.setDate(1,convertUtilToSql(payment.getDate()));
        prSt.setDouble(2, payment.getAmount());
        prSt.setInt(3,payment.getId());
        return prSt.executeUpdate();
    }
    
    public int delete(Payment payment) throws SQLException {
        return con.createStatement().executeUpdate("Delete from Payments where paymentID = '" + payment.getId() + "'");
    }
   
}
