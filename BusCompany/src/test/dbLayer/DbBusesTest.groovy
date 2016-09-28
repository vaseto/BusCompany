package dbLayer

import modelLayer.Bus
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

import java.rmi.UnexpectedException
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * Created by Vasil Nedelchev on 23.4.2016 г..
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)// specify execution order using annotations and ordering by method name
class DbBusesTest extends GroovyTestCase {
    DbBuses dbBuses;
    Bus bus;
    @ Before
    void setUp() {
        super.setUp()
        dbBuses = new DbBuses();

    }

    void tearDown() {
    }
    void createBus(){
        dbBuses = new DbBuses();
        dbBuses.createBus(createBusObject());
    }
    void deleteBus(int id){
        dbBuses.deleteBusbyID(id);
    }


    @ Test
    void test1CreateBus() {
        try {
            dbBuses = new DbBuses();
            dbBuses.createBus(createBusObject());
        }catch (Exception e){
            fail(System.out.print("method    Fail"));

        }

    }
    @ Test
    void test2GetBusByID() {
        assertEquals(createBusObject(), dbBuses.getBusByID(-1));
    }
    @Test
    void test3UpdateBusByID() {

        bus = new Bus(-2,"volvo",233 , date("01, 3, 2012") ,false,null );
        dbBuses.updateBusById(-1, bus);
        assertEquals(bus, dbBuses.getBusByID(-2));
    }
    @ Test
    void test4DeleteBusByID() {
        dbBuses = new DbBuses();
        deleteBus(-2);
    }

    Bus createBusObject(){
        dbBuses = new DbBuses();

        bus = new Bus(-1, "scania",23 , date("01, 2, 2015") ,true,null );
        return bus;
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
