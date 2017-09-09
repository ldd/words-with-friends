package model;
import java.sql.* ;

public class DatabaseConnect
{
	Statement myStatement;
	Connection myCon;
    int sqlCode=0;      // Variable to hold SQLCODE
    String sqlState="00000";  // Variable to hold SQLSTATE

	public DatabaseConnect() {
		// Register the driver.  You must register the driver before you can use it.
        try {
        	DriverManager.registerDriver ( new com.ibm.db2.jcc.DB2Driver() ) ;
        }
        
        catch (Exception cnfe){
        	System.out.println("Class not found");
        }

        try{
			myCon = DriverManager.getConnection ("jdbc:db2://db2.cs.mcgill.ca:50000/cs421","cs421g36","wordswithfriends1") ;
			myStatement = myCon.createStatement ( ) ;
		}
		catch (Exception e){
			System.out.println("\nUnable to reach database\n");
			System.exit(-1);
		}
	}

	DatabaseConnect(String url, String username, String password) throws SQLException{
		// Register the driver.  You must register the driver before you can use it.
        try {
        	DriverManager.registerDriver ( new com.ibm.db2.jcc.DB2Driver() ) ;
        }
        
        catch (Exception cnfe){
        	System.out.println("Class not found");
        }

        try{
			myCon = DriverManager.getConnection (url,username,password) ;
			myStatement = myCon.createStatement ( ) ;
		}
		catch (Exception e){
			;
		}
	}
	
	public Connection getCon(){
		return myCon;
	}

	public void setCon(Connection input){
		myCon=input;
	}

    void CloseConnection(){
    	try{
    		myStatement.close() ;
    		myCon.close() ;
    	}
    	
    	catch(Exception e){
    		;
    	}
    }
}

		  
