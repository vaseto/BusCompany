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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import modelLayer.CardInformation;

/**
 *
 * @author Delyan
 */
public class DbCardInfo extends DbClass {

    private Connection con;
    private String from = "CardsInfo";
    
    public DbCardInfo() {
        con = getCon();
    }
    
    /**
     * 
     * @return list of all cards in the database
     * @throws SQLException 
     */
    public List<CardInformation> getAllCards() throws SQLException {
        return missWhere("");
    }
    /**
     * Tries t build a card from the results set passed. 
     * @param results the set to be used to build the object
     * @return the card object or null if the card cannot be build
     * @throws SQLException 
     */
    private CardInformation buildCard(ResultSet results) throws SQLException {
        try {
            DbCard_Payment dbCardPayment = new DbCard_Payment();
            CardInformation card = new CardInformation(results.getString(2), results.getString(1), results.getInt(3), results.getDate(4));            
            List list = dbCardPayment.getAllPaymentsWithCard(card);
                card.setPayments(list);
            return card;
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Builds a single CardInformation object which matches the given wClause
     * @param wClause to be used to find an record in the query
     * @return CardInformation object 
     * @throws SQLException 
     */
    private CardInformation singleWhere(String wClause) throws SQLException {
        ResultSet results = getResultSet(wClause, from);
        results.next();
        return buildCard(results);
    }
    
    /**
     * Returns list of all CardInformations matching the wClause
     * @param wClause to be used to find records in the database
     * @return list of CardInformation
     * @throws SQLException 
     */
    private List<CardInformation> missWhere(String wClause) throws SQLException {
        List<CardInformation> list = new ArrayList<>();
        ResultSet results = getResultSet(wClause, from);
        
        while (results.next()) {
            list.add(buildCard(results));
        }        
        
        return list;
    }
    
    /**
     * Creates prepared statement for inserting CardInformation objects
     * @return Prepared statement
     * @throws SQLException 
     */
    private PreparedStatement getPrepStatForInsert() throws SQLException {
        return con.prepareStatement("Insert into CardsInfo (cardNumber,bankName,CCV,expDate) values (?,?,?,?)");
    }
    
    /**
     * Creates prepared statement for updating CardInformation objects
     * @return the newly created prepared statement
     * @throws SQLException 
     */
    private PreparedStatement getPrepStatForUpdate() throws SQLException {
        return con.prepareStatement("Update CardsInfo set cardNumber= ?,bankName =?, CCV = ?, expDate = ? where cardNumber = ? ");
    }
    
    /**
     *  
     * @param number the parameter to be searched with
     * @return CardInformation object with the given number
     * @throws SQLException 
     */
    public CardInformation getCardByNumber(String number) throws SQLException {
        return singleWhere("cardNumber = '" + number + "'");
    }
    
    /**
     * Insert CardInformation object into the database
     * @param card the object to be inserted
     * @return 1 if it is successfully created
      * @throws SQLException 
     */
    public int insert(CardInformation card) throws SQLException {
        return getPrivateFields(getPrepStatForInsert(), card).executeUpdate();
    }
    
    /**
     * Set the parameters on the prepared statement with the card data
     * @param prSt to receive the parameters
     * @param card the card which parameters to be used
     * @return the prepare statement 
     * @throws SQLException 
     */
    private PreparedStatement getPrivateFields(PreparedStatement prSt, CardInformation card) throws SQLException {
        prSt.setString(1, card.getBankName());
        prSt.setInt(2, card.getCcv());
        prSt.setDate(3, convertUtilToSql(card.getExpDate()));
        prSt.setString(4, card.getCardNumber());
        return prSt;
    }
    
    /**
     * Deletes the specified card from the database
     * @param card the card to be removed
     * @return 1 if it is successful
     * @throws SQLException 
     */
    public int delete(CardInformation card) throws SQLException {
        return con.createStatement().executeUpdate("Delete from CardsInfo where  cardNumber = '" + card.getCardNumber() + "'");
    }
    
    /**
     * Makes util time to sql time
     * @param uDate the date to be converted
     * @return sql.Date
     */
    private static java.sql.Date convertUtilToSql(java.util.Date uDate) {
        return new java.sql.Date(uDate.getTime());        
    }
    
    /**
     * Updates the record of the card in the database
     * @param card the card to be updated
     * @param idBeforeUpdate the id before it is updated 
     * @return 1 if it is successful
     * @throws SQLException 
     */
    public int update(CardInformation card, String idBeforeUpdate) throws SQLException {
        PreparedStatement prSt = getPrivateFields(getPrepStatForUpdate(), card);
        prSt.setString(6, idBeforeUpdate);
        return prSt.executeUpdate();
    }
    
    public static void main(String args[]) throws ParseException, SQLException {
        /**
         * String string = "01, 2, 2015"; DateFormat format = new
         * SimpleDateFormat("MM, d, yyyy", Locale.ENGLISH); java.util.Date date
         * = null; try{ date = format.parse(string); System.out.println(date);
         * }catch(ParseException e){ System.out.println(e.getMessage()); }
         * DbCardInfo dbCard = new DbCardInfo();
         * System.out.println(dbCard.delete(dbCard.getCardByNumber(41241))); }
    *
         */
        DbCardInfo dbCard = new DbCardInfo();
        //  System.out.println(dbCard.delete(dbCard.getCardByNumber(1243124122)));
    }
}
