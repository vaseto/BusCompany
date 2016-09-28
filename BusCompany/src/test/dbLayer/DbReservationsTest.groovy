package dbLayer

import modelLayer.Bus
import modelLayer.CardInformation
import modelLayer.Person
import modelLayer.Reservation
import org.junit.Before
import org.junit.Test

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * Created by Vasil Nedelchev on 23.4.2016 г..
 */
class DbReservationsTest extends GroovyTestCase {
    private DbReservations dbReservations;
    private Reservation reservation;
    private DbBusesTest dbBusesTest;
    private Bus bus;
    private Person person;
    private DbPerson dbPerson;

    @Before
    void setUp() {
        super.setUp()
        dbReservations = new DbReservations();
        dbPerson = new DbPerson();
        dbBusesTest = new DbBusesTest();
        bus = dbBusesTest.createBusObject();
        person = new Person("Petyr", "Petrov", "-1","-1", "08933", null);

        int id =  dbPerson.insert(person);
        reservation = new Reservation( date("01, 2, 2015"), date("01, 2, 2015"), 23, bus,person);

    }

    @Test
    void testGetReservationByID() {
        dbBusesTest.testGetBusByID();
    }
    void tearDown() {

    }

    void testCreateReservation1() {
        dbReservations.createReservation(reservation);
    }

    Date date( String string){

        DateFormat format = new SimpleDateFormat("MM, d, yyyy", Locale.ENGLISH);
        java.util.Date date = null;
        try {
            date = format.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
