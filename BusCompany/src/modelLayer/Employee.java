package modelLayer;

public class Employee extends Person { // inheritance

    //instance variables
    private String position;
    private double salary;

    // a parameterized constructor 
    public Employee(String firstName, String lastName, String email, String telephone, String password, CardInformation cardInfo, String position, double salary,int id) {
        super(firstName, lastName, email, telephone, password, cardInfo);
        this.position = position;
        this.salary = salary;
    }

    public Employee(String position, double salary, String firstName, String lastName, String email, String password, String telephone) {
        super(firstName, lastName, email, password, telephone);
        this.position = position;
        this.salary = salary;
    }

    // get methods
    public String getPosition() {
        return position;
    }

    public double getSalary() {
        return salary;
    }

    // set methods
    public void setPosition(String position) {
        this.position = position;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return super.toString() + "Position: " + position + " Salary: " + salary;
    }

}
