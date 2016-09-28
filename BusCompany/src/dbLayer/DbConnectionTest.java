package dbLayer;

public class DbConnectionTest {

	//@Test
	public static void main(String args[]){
		test();
	}
	
	public static void test() {
		DbConnection dbCon = DbConnection.getInstance();
		if(dbCon != null)
		{
			System.out.println("Conecction to DB is ok");
		}
		else{
		    System.out.println("Can not connect to the DB");
		}
	}

}
