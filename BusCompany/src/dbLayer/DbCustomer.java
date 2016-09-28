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
import modelLayer.Customer;
import modelLayer.Person;

/**
 *
 * @author Delyan
 */
public class DbCustomer extends DbClass {
    
    private Connection con;
    private DbPerson dbPerson;
    private String from = "Customers";

    public DbCustomer() {
        con = getCon();
        dbPerson = new DbPerson();
    }

    /**
     * Method to be used when more than 1 result is expected
     *
     * @param wClause the clause to be used for searching
     * @return list of customers matching the wClause
     * @throws SQLException
     */
    private List<Customer> missWhere(String wClause) throws SQLException {
        List<Customer> list = new ArrayList<>();
        ResultSet results = getResultSet(wClause, from);
        while (results.next()) {
            list.add(buildCustomer(results));
        }
        return list;
    }

    /**
     *
     * @return all Customers in the database
     * @throws SQLException
     */
    public List<Customer> getAllCustomers() throws SQLException {
        return missWhere("");
    }

    /**
     * Returns a single instance of Customer with that id
     *
     * @param id the id of the customer
     * @return Customer object
     * @throws SQLException
     */
    public Customer getCustomer(int id) throws SQLException {
        return singleWhere("cID = '" + id + "'");
    }

    /**
     * Transforms a Person to Customer
     *
     * @param results the result set to be used to build an object
     * @return Customer instance with the same id as the the person
     * @throws SQLException
     */
    private Customer buildCustomer(ResultSet results) throws SQLException {
        Person p = dbPerson.getPersonByyId(results.getInt(1));
        Customer cust = new Customer(results.getString(2), p.getFirstName(), p.getLastName(), p.getEmail(), p.getPassword(), p.getTelephone(), p.getId(), p.getCardInfo());
        if (p.getReservations() != null) {
            cust.setReservations(p.getReservations());
        } else {
            DbReservations dbReserve = new DbReservations();
            cust.setReservations(dbReserve.getAllReservationsForPerson(p));
        }
        if (p.getTickets() != null) {
            cust.setTickets(p.getTickets());
        } else {
            DbTicket dbTicket = new DbTicket();
            cust.setTickets(dbTicket.getAllTicketsForPerson(p.getId()));
        }
        return cust;
    }

    /**
     * Returns customer object
     *
     * @param wClause the clause to be used to find the customer
     * @return
     * @throws SQLException
     */
    private Customer singleWhere(String wClause) throws SQLException {
        ResultSet results = getResultSet(wClause, from);
        results.next();
        return buildCustomer(results);
    }

    /**
     * Creates a prepared statement for insert
     *
     * @return the newly created prepare statement
     * @throws SQLException
     */
    private PreparedStatement getPrepStatForInsert() throws SQLException {
        return con.prepareStatement("Insert into Customers (cID,groupe) values (?,?)");
    }

    /**
     * Creates a prepared statement for updated
     *
     * @return the newly created prepare statement
     * @throws SQLException
     */
    private PreparedStatement getPrepStatForUpdate() throws SQLException {
        return con.prepareStatement("Update Customers set groupe = ? where cID = ? ");
    }

    /**
     * Sets the prepared statment with the customer's data
     *
     * @param prSt the prepared statement to receive parameters
     * @param c the customer which data to be used
     * @return the prepared statement
     * @throws SQLException
     */
    private PreparedStatement getPrivateFields(PreparedStatement prSt, Customer c) throws SQLException {
        prSt.setInt(1, c.getId());
        prSt.setString(2, c.getGroup());
        return prSt;
    }

    /**
     * Insert customer object into the database
     *
     * @param c the customer to be inserted
     * @return 1 if successful
     * @throws SQLException
     */
    public int insert(Customer c) throws SQLException {
        DbConnection.starTransaction();
        PreparedStatement pr =getPrepStatForInsert();
        int cID = dbPerson.insert(c);
        pr.setInt(1,cID);
        pr.setString(2,c.getGroup());
        int index =pr.executeUpdate();
        DbConnection.commitTransaction();
        return index;
    }

    /**
     * Update customers information in the database
     *
     * @param c the customer to be updated
     * @return 1 if successful
     * @throws SQLException
     */
    public int update(Customer c) throws SQLException {
        PreparedStatement prSt = getPrepStatForUpdate();
        DbConnection.starTransaction();
        dbPerson.update(c);
        prSt.setString(1, c.getGroup());
        prSt.setInt(2, c.getId());
        int index = prSt.executeUpdate();
        DbConnection.commitTransaction();
        return index;
    }
    
}
