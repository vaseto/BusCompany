package modelLayer;

public class Customer extends Person {

    // instance variables
    private String group;

    // a parameterized constructor
    public Customer(String firstName, String lastName, String email, String telephone, String password, CardInformation cardInfo, String group) {
        super(firstName, lastName, email, password, telephone, cardInfo);
        this.group = group;
    }

    public Customer(String group, String firstName, String lastName, String email, String password, String telephone, int id, CardInformation cardInfo) {
        super(firstName, lastName, email, password, telephone, id, cardInfo);
        this.group = group;
    }

    public Customer(String group, String firstName, String lastName, String email, String password, String telephone) {
        super(firstName, lastName, email, password, telephone);
        this.group = group;
    }
    
    

    // get method
    public String getGroup() {
        return group;
    }

    // set method
    public void setGroup(String group) {
        this.group = group;
    }

    @Override // an override of the toString() method 
    public String toString() {
        return super.toString() + "Group: " + group;
    }
}
