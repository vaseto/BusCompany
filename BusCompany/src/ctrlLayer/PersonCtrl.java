package ctrlLayer;

import dbLayer.DbCardInfo;
import dbLayer.DbConnection;
import dbLayer.DbCustomer;
import dbLayer.DbEmployee;
import java.sql.SQLException;
import java.util.List;
import dbLayer.DbPerson;
import java.util.Date;
import modelLayer.CardInformation;
import modelLayer.Customer;
import modelLayer.Employee;
import modelLayer.Person;

public class PersonCtrl {

    private DbPerson dbPerson;
    private DbEmployee dbEmployee;
    private DbCustomer dbCustomer;

    public PersonCtrl() {
        dbPerson = new DbPerson();
        dbEmployee = new DbEmployee();
        dbCustomer = new DbCustomer();
    }

    public int createEmployee(String firstName, String lastName, String email, String password, String telephone, String position, double salary) throws SQLException {
        return dbEmployee.insert(new Employee(position, salary, firstName, lastName, email, password, telephone));
    }

    public int createCustomer(String firstName, String lastName, String email, String password, String telephone, String group) throws SQLException {
        return dbCustomer.insert(new Customer(group, firstName, lastName, email, password, telephone));
    }

    public int assignNewCreditCard(Person p, String bankName, Date expDate, String cardNumber, int CCV) throws SQLException {
        DbConnection.starTransaction();
        DbCardInfo dbCard = new DbCardInfo();
        CardInformation card = new CardInformation(bankName, cardNumber, CCV, expDate);
        dbCard.insert(card);
        p.setCardInfo(card);
        DbConnection.commitTransaction();
        return dbPerson.update(p);
    }
    
    public int removeCreditCard(Person p, CardInformation card) throws SQLException{
       DbConnection.starTransaction();
        dbPerson.removeCard(p);
        updateCustomer(getCustomerById(p.getId()));
        DbCardInfo dbCard = new DbCardInfo();
        int i= dbCard.delete(card);
        DbConnection.closeConnection();
        return i;
    }
    
    
    

    /**
     *
     * @param id the paramater to be used to find a person
     * @return Person object
     * @throws SQLException if not found
     */
    public Person getPersonById(int id) throws SQLException {
        return dbPerson.getPersonByyId(id);
    }
    
    /**
     * Return an employee with the given id
     * @param id
     * @return
     * @throws SQLException 
     */
    public Employee getEmployeeById(int id) throws SQLException{
        return dbEmployee.getEmployee(id);
    }
    
    public Customer getCustomerById(int id) throws SQLException{
        return dbCustomer.getCustomer(id);
    }

    public Person getPersonByEmail(String email) throws SQLException {
        return dbPerson.getPersonByEmail(email);
    }
    
    public void setFirstName(Person p,String name){
        p.setFirstName(name);
    }
    
    public void setLastName(Person p,String lname){
        p.setLastName(lname);
    }
    
    public void setEmail(Person p,String email){
        p.setEmail(email);
    }
    
    public void setPassword(Person p,String password){
        p.setPassword(password);
    }
    
    public void setTelephone(Person p,String telephone){
        p.setTelephone(telephone);
    }

    public String getPassword(Person p) {
        return p.getPassword();
    }
    
    
    public String getPersonFname(Person p){
        return p.getFirstName();
    }   
    
    /**
     * returns the last name of a person
     * @param p
     * @return 
     */
    public String getPersonLname(Person p){
        return p.getLastName();
    }
    
    /**
     * Returns the email of a person
     * @param p
     * @return 
     */
    public String getPersonEmail(Person p){
        return p.getEmail();
    }
    
    /**
     * Returns the telephone of a person
     * @param p
     * @return 
     */
    public String getPersonTelephone(Person p){
        return p.getTelephone(); 
    }
    
    /**
     * Returns the id of a person
     * @param p
     * @return 
     */
    public int getPersonId(Person p){
        return p.getId();
    }
    
    /**
     * Returns the creditCard of the person
     * @param p the person 
     * @return the card
     */
    public CardInformation getPersonCard(Person p){
        return p.getCardInfo();
    }
    
    /**
     *
     * @param firstName to be used for searching in the database
     * @return the last Person found with that name
     * @throws SQLException
     */
    public List<Person> getPersonByFirstName(String firstName) throws SQLException {
        return dbPerson.getPersonByFirstName(firstName);
    }
    
    /**
     * Transforms a person to customer
     * @param p the person to be used
     * @return  the customer object
     */
    public Customer personToCustomer(Person p) throws SQLException{
        Customer c = new Customer("",p.getFirstName(), p.getLastName(),p.getEmail(),p.getPassword(),p.getTelephone(),p.getId(),p.getCardInfo());
          if (p.getReservations() != null) {
            c.setReservations(p.getReservations());
        } else {
            ReservationCtrl reserve = new ReservationCtrl();
            c.setReservations(reserve.getAllReservationsForPerson(p));
        }
        if (p.getTickets() != null) {
            c.setTickets(p.getTickets());
        } else {
           TicketCtrl ticketCtrl = new TicketCtrl();
            c.setTickets(ticketCtrl.getAllTicketsForPerson(p.getId()));
        }
        return c;
    }
    

    /**
     *
     * @param emp The employee to be updated
     * @return 1 if the update is successful
     * @throws SQLException
     */
    public int updateEmployee(Employee emp) throws SQLException {
        int i = -1;
        DbConnection.starTransaction();
        dbEmployee.update(emp);
        i =dbPerson.update(emp);
        DbConnection.commitTransaction();
        return i;
    }
    
    /**
     * Updates a customer
     * @param cust the customer to be updated
     * @return
     * @throws SQLException 
     */
    public int updateCustomer(Customer cust) throws SQLException{
        int i = -1;
        DbConnection.starTransaction();
        dbCustomer.update(cust);
        i = dbPerson.update(cust);
        DbConnection.commitTransaction();
        return i;
    }
    

    /**
     *
     * @param person The person to be removed from the database
     * @return 1 if the person is deleted
     * @throws SQLException
     */
    public int deletePerson(Person person) throws SQLException {
        return dbPerson.delete(person);
    }
    
    /**
     * Returns all employees
     * @return
     * @throws SQLException 
     */
    public List<Employee> getAllEmployees() throws SQLException{
        return dbEmployee.getAllEmployees();
    }
    
    /** 
     * Returns all customers
     * @return
     * @throws SQLException 
     */
    public List<Customer> getAllCustomers() throws SQLException{
        return dbCustomer.getAllCustomers();
    }

    /**
     *
     * @return a list of all People in the database
     * @throws SQLException
     */
    public List<Person> getAllPeople() throws SQLException {
        return dbPerson.getAllPersons();
    }

    public static void main(String args[]) throws SQLException {
        PersonCtrl persCtrl = new PersonCtrl();
    }

}
