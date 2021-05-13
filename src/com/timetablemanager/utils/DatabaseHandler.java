/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package com.timetablemanager.utils;

/**
 *
 * @author Shelani Wijesekera
 */

import java.sql.DriverManager;

import com.mysql.jdbc.Connection;



public class DatabaseHandler {
      private static final String USER_NAME = "root";
	private static final String PASSWORD = "shl199809";	
	public static Connection conn = null;
	

	public static void makeConnection()
	{
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			
                        conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/timetable_db",USER_NAME,PASSWORD);
			
			//listener.onFinished(true);
			System.out.println("Successfully Connected to the Database");
			
		}
		catch(Exception e)
		{
			//listener.onFinished(false);
			System.out.println("Error while connecting to the Database");
			e.printStackTrace();
		}
		
		
	}

    
}
