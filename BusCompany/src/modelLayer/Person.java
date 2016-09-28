package modelLayer;

import java.util.ArrayList;
import java.util.List;

public class Person {

    // instance variables	
    private String firstName, lastName, email, password, telephone;
    private int id;
    private CardInformation cardInfo; // aggregation 
    private List<Ticket> tickets;
    private List<Reservation> reservations;

    // a parameterized constructor which initializes the Person object
    public Person(String firstName, String lastName, String email, String password, String telephone, CardInformation cardInfo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.telephone = telephone;
        this.cardInfo = cardInfo;
        tickets = new ArrayList<>();
        reservations = new ArrayList<>();
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public String getPassword() {
        return password;
    }

    public Person(String firstName, String lastName, String email, String password, String telephone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.telephone = telephone;
    }

    public Person(String firstName, String lastName, String email, String password, String telephone, int id, CardInformation cardInfo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.telephone = telephone;
        this.id = id;
        this.cardInfo = cardInfo;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public CardInformation getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(CardInformation cardInfo) {
        this.cardInfo = cardInfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // get methods
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getTelephone() {
        return telephone;
    }

    // set methods
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "Person{" + "firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", password=" + password + ", telephone=" + telephone + ", cardInfo=" + cardInfo + ", id=" + id + '}';
    }

}
