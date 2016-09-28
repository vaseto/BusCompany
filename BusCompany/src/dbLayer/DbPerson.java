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
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelLayer.Person;

/**
 *
 * @author Delyan
 */
public class DbPerson extends DbClass {

    private String from = "Persons";
    private Connection con;

    public DbPerson() {
        con = getCon();
    }

    /**
     * Returns a list of all persons
     *
     * @return
     * @throws SQLException
     */
    public List<Person> getAllPersons() throws SQLException {
        return missWhere("");
    }

    /**
     * Builds a person object with the given result set
     *
     * @param results
     * @return person object
     * @throws SQLException
     */
    private Person buildPerson(ResultSet results) throws SQLException {
        DbCardInfo dbCard = new DbCardInfo();
        Person person = new Person(results.getString(2), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getInt(1),
                dbCard.getCardByNumber(results.getString(7)));

        return person;
    }

    /**
     * Method used when single result is expected
     *
     * @param wClause the clause to be used for searching
     * @return a person object
     * @throws SQLException
     */
    private Person singleWhere(String wClause) throws SQLException {
        ResultSet results = getResultSet(wClause, from);
        results.next();
        return buildPerson(results);
    }

    /**
     * Method used when more than one results is expected
     *
     * @param wClause the clause to be used for searching
     * @return a list of persons matching the wClause
     * @throws SQLException
     */
    private List<Person> missWhere(String wClause) throws SQLException {
        List<Person> list = new ArrayList<>();
        ResultSet results = getResultSet(wClause, from);
        while (results.next()) {
            list.add(buildPerson(results));
        }
            
            return list;
        }
        
        /**
         * Returns a prepared statement for inserting
         *
         * @return
         * @throws SQLException
         */
    private PreparedStatement getPrepStatForInsert() throws SQLException {
        return con.prepareStatement("Insert into Persons (firstName,lastName,[e-mail],password,telephone) values (?,?,?,?,?) Select Scope_Identity()");
    }

    /**
     * Returns a prepared statment for updating
     *
     * @return
     * @throws SQLException
     */
    private PreparedStatement getPrepStatForUpdate() throws SQLException {
        return con.prepareStatement("Update Persons set firstName = ?,lastName = ?,[e-mail] = ?,password = ?,telephone = ?,cardInfoId = ? where pID = ? ");
    }

    /**
     * Returns a prepared statement for update without cardID
     *
     * @return
     * @throws SQLException
     */
    private PreparedStatement getPrepStatForUpdateExcludingCard() throws SQLException {
        return con.prepareStatement("Update Persons set firstName = ?,lastName = ?,[e-mail] = ?,password = ?,telephone = ? where pID = ? ");
    }

    /**
     * Returns a person with the given email
     *
     * @param email
     * @return
     * @throws SQLException
     */
    public Person getPersonByEmail(String email) throws SQLException {
        return singleWhere("[e-mail] = '" + email + "'");
    }

    /**
     * Returns a string representing a person password
     *
     * @param id of the person
     * @return
     * @throws SQLException
     */
    public String getPassword(int id) throws SQLException {
        return getPersonByyId(id).getPassword();
    }

    /**
     * Finds a person by the given id
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public Person getPersonByyId(int id) throws SQLException {
        return singleWhere("pID  = '" + id + "'");
    }

    /**
     * Deletes persons with the name
     *
     * @param name
     * @return the number of people deleted
     * @throws SQLException
     */
    public int deleteByName(String name) throws SQLException {
        return con.createStatement().executeUpdate("Delete from Persons where firstName = '" + name + "'");
    }
    
    public int getPersonIdByEmail(String email) throws SQLException{
       return singleWhere("[e-mail] = " + email).getId();
    }

    /**
     * Returns a s list of people with the first name
     *
     * @param city representing the first name
     * @return
     * @throws SQLException
     */
    public List<Person> getPersonByFirstName(String city) throws SQLException {
        return missWhere("firstName ='" + city + "'");
    }

    /**
     * Returns a prepared statement with set up from the person data
     *
     * @param prSt
     * @param person
     * @return
     * @throws SQLException
     */
    private PreparedStatement getPrivateFields(PreparedStatement prSt, Person person) throws SQLException {
        prSt.setString(1, person.getFirstName());
        prSt.setString(2, person.getLastName());
        prSt.setString(3, person.getEmail());
        prSt.setString(4, person.getPassword());
        prSt.setString(5, person.getTelephone());
        return prSt;
    }

    /**
     * Insert person in the database
     *
     * @param p
     * @return
     * @throws SQLException
     */
    public int insert(Person p) throws SQLException {
        PreparedStatement prSt = getPrivateFields(getPrepStatForInsert(), p);
       prSt.executeUpdate();
         ResultSet results = prSt.getGeneratedKeys();
         if(results.next()){
            return results.getInt(1);
        }
         throw new RuntimeException();
    }

    /**
     * Deletes a person from the database
     *
     * @param person
     * @return
     * @throws SQLException
     */
    public int delete(Person person) throws SQLException {
        return con.createStatement().executeUpdate("Delete from Persons where pID = '" + person.getId() + "'");
    }

    /**
     * Statement for removing a card from a person
     *
     * @param p
     * @return
     * @throws SQLException
     */
    private PreparedStatement removeCardPrepStatement(Person p) throws SQLException {
        return con.prepareStatement("Update Persons set cardInfoId = null where pID = " + p.getId());
    }

    /**
     * Removes a card from a person
     *
     * @param p
     * @return
     * @throws SQLException
     */
    public int removeCard(Person p) throws SQLException {
        return removeCardPrepStatement(p).executeUpdate();
    }

    /**
     * Updates a person
     *
     * @param person
     * @return
     * @throws SQLException
     */
    public int update(Person person) throws SQLException {
        PreparedStatement prSt;
        if (person.getCardInfo() == null) {
            prSt = getPrivateFields(getPrepStatForUpdateExcludingCard(), person);
            prSt.setInt(6, person.getId());
        } else {
            prSt = getPrivateFields(getPrepStatForUpdate(), person);
            prSt.setString(6, person.getCardInfo().getCardNumber());
            prSt.setInt(7, person.getId());
        }

        return prSt.executeUpdate();
    }

    public static void main(String args[]) {
        DbPerson dbPerson = new DbPerson();
        try {
            System.out.println(dbPerson.getPersonByFirstName("Ivan"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
