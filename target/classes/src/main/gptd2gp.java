//*********************************************************************
//
//  File:       gptd2gp.java
//  Header:     none
//  Purpose:    External tabel based on a JDBC conection to teradata with these arg
//              arg[0] select to be executed
//              arg[1] ip of the Teradata host
//
//
//*********************************************************************

import java.sql.*;
import java.io.*;
import java.util.*;

public class gptd2gp
{
    // Name of the user able to create, drop, and manipulate tables by default taking superuser of td
    public static String sUser = "dbc";
    public static String sPassword = "dbc";

    //public static void main(String args[])
    public static void main(String[] argv) 
    throws ClassNotFoundException
    {
        // Creation of URL to be passed to the JDBC driver
        String url = "jdbc:teradata://"+ argv[1] +" /TMODE=ANSI,CHARSET=UTF8";

        // Query statement selecting all columns of the table. Using this query,
        // result set column metadata can be retrieved.
        //
        //String sSelAll = " select databasename,creatorname From dbc.databases;";
        //
	 String sSelAll = argv[0];
        try
        {
            //System.out.println(" Looking for the Teradata JDBC driver... ");
            // Loading the Teradata JDBC driver
            Class.forName("com.teradata.jdbc.TeraDriver");
            //System.out.println(" JDBC driver loaded. \n");

            // Attempting to connect to Teradata
            //System.out.println(" Attempting to connect to Teradata via" +
                          //     " the JDBC driver...");

            // Creating a connection object
            Connection con = DriverManager.getConnection(url, sUser, sPassword);
            //System.out.println(" User " + sUser + " connected.");
            //System.out.println(" Connection to Teradata established. \n");

            try
            {
                // Creating a statement object from an active connection
                Statement stmt = con.createStatement();
                //System.out.println(" Statement object created. \n");

                try
                {
                    ResultSet rset = stmt.executeQuery(sSelAll);
                    ResultSetMetaData rsmd = rset.getMetaData();

                    // Retrieve the number of columns returned using metada info rsmd TODO not used now
                    int colCount = rsmd.getColumnCount();
                    //System.out.println(" This table has " + colCount + " columns.\n");

          	ArrayList<String> arr = new ArrayList<String>();
          	int c=1;
          	while(c<=rsmd.getColumnCount())
			{
				arr.add(rsmd.getColumnName(c));
				c=c+1;
          	}
          	
          	while(rset.next()){
          		String innerstr ="";
          		int m=0;
          		for (String temp : arr) {
					
				
					String str1 = temp;
					
					str1 = rset.getString(str1);
					if (str1==null) str1="";
					
					str1 = str1.replaceAll("[^-@#$%&*()_',\\p{Alnum}\\-\\.\\:\\\\\\/\\@\\;\\\" ]", "");
					//replacing non text characters
					if(m != arr.size()-1)
					{
						innerstr = innerstr + ""+ str1 + "|" ;
					}else
					{
						innerstr = innerstr + ""+ str1+"";
					}
					m=m+1;
				}
				System.out.println(innerstr);
			}
                }
                finally
                {
                    // Close the statement
                    stmt.close();
                    //System.out.println("\n Statement object closed. \n");
                }
            }
            finally
            {
                // Close the connection
                //System.out.println(" Closing connection to Teradata...");
                con.close();
                //System.out.println(" Connection to Teradata closed. \n");
            }

            //System.out.println(" Sample gptd2gp.java finished. \n");
        }
        catch (SQLException ex)
        {
            // A SQLException was generated.  Catch it and display
            System.out.println();
            System.out.println("*** SQLException caught ***");

            while (ex != null)
            {
                System.out.println(" Error code: " + ex.getErrorCode());
                System.out.println(" SQL State: " + ex.getSQLState());
                System.out.println(" Message: " + ex.getMessage());
                ex.printStackTrace();
                System.out.println();
                ex = ex.getNextException();
            }

            throw new IllegalStateException ("Sample failed.") ;
        }
    } // End main
} // End class gptd2gp.java
