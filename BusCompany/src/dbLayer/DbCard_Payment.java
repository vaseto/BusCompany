/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import modelLayer.CardInformation;
import modelLayer.Payment;

/**
 *
 * @author Delyan
 */
public class DbCard_Payment {

    private Connection con;

    public DbCard_Payment() {
        con = DbConnection.getInstance().getDBcon();
    }
    /**
     * 
     * @param card to be used to find all the payments with it 
     * @return List of all the payments made with this card
     * @throws SQLException 
     */
    public List<Payment> getAllPaymentsWithCard(CardInformation card) throws SQLException{
        DbPayment dbPay = new DbPayment();
       return dbPay.missWhere("cardNumber ='"+ card.getCardNumber() +"'");
    }
    
    /**
     * Creates prepare statement for inserting into the table
     * @return the newly created prepare statement
     * @throws SQLException 
     */
    private PreparedStatement getPrepStatForInsert() throws SQLException {
        return con.prepareStatement("Insert into [Card-Payment] (cardNumber,paymentID) values (?,?) Select Scope_Identity()");
    }

    /**
     * Creates prepare statement for updating using cardNumber
     * @return prepare statement
     * @throws SQLException 
     */
    private PreparedStatement getPrepStatForUpdateWhereCardNumber() throws SQLException {
        return con.prepareStatement("Update [Card-Payment] set cardNumber =?, paymentID = ? where cardNumber = ? ");
    }

    /**
     *  Creates prepare statement for updating using paymentID
     * @return prepare statement
     * @throws SQLException 
     */
    private PreparedStatement getPrepStatForUpdateWherePaymentID() throws SQLException {
        return con.prepareStatement("Update [Card-Payment] set cardNumber =?, paymentID = ? where paymentID = ? ");
    }
    
    /**
     * Inserts a record into the table
     * @param card the card to be used
     * @param payment  the payment to be used
     * @return 1 if it is successful
     * @throws SQLException 
     */
    public int insert(CardInformation card, Payment payment) throws SQLException {
        PreparedStatement prSt = getPrepStatForInsert();
        prSt.setString(1, card.getCardNumber());
        prSt.setInt(2, payment.getId());
        return prSt.executeUpdate();
    }

    /**
     * Updates a record using the card
     * @param card the card to be updated
     * @param payment the payment to be updated
     * @return 1 if successful
     * @throws SQLException 
     */
    public int updateCard(CardInformation card, Payment payment) throws SQLException {
        PreparedStatement prSt = getPrepStatForUpdateWhereCardNumber();
        prSt.setString(1, card.getCardNumber());
        prSt.setInt(2, payment.getId());
        prSt.setString(3, card.getCardNumber());
        return prSt.executeUpdate();
    }

    /**
     * Updates a record using the payment id
     * @param card the card to be updated
     * @param payment the payment to be updated
     * @return 1 if successful
     * @throws SQLException 
     */
    public int updatePayment(CardInformation card, Payment payment) throws SQLException {
        PreparedStatement prSt = getPrepStatForUpdateWherePaymentID();
        prSt.setString(1, card.getCardNumber());
        prSt.setInt(2, payment.getId());
        prSt.setInt(3, payment.getId());
        return prSt.executeUpdate();
    }

    /**
     * Deletes a record using the card number 
     * @param card the card to be used
     * @return 1 if successful
     * @throws SQLException 
     */
    public int delete(CardInformation card) throws SQLException {
        return con.createStatement().executeUpdate("Delete from Card-payment where  cardNumber = '" + card.getCardNumber() + "'");
    }

    /**
     * Deletes a record using the payment id
     * @param payment the payment to be used
     * @return 1 if successful
     * @throws SQLException 
     */
    public int delete(Payment payment) throws SQLException {
        return con.createStatement().executeUpdate("Delete from Card-payment where paymentID = '" + payment.getId() + "'");
    }

}
