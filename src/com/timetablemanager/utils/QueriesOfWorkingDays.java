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

import java.sql.ResultSet;
import java.sql.SQLException;

import com.timetablemanager.models.Day;
import com.timetablemanager.models.Program;
import java.sql.PreparedStatement;
import com.timetablemanager.models.Day;
import com.timetablemanager.models.Program;

public class QueriesOfWorkingDays {
    
    public static void createTables(OnTaskCompleteListener listener)
	{
		
		if(DatabaseHandler.conn != null)
		{
			
                    
			try
			{
				String tableName = "WorkingDaysAndHours";
				boolean isCreated = DatabaseHandler.conn.getMetaData().getTables(null, null, tableName, null).next();
				if(!isCreated)
				{
					String query = "CREATE TABLE WorkingDaysAndHours(Type INTEGER PRIMARY KEY,NumberOfWorkingDays INTEGER,WorkingTimeHours INTEGER,WorkingTimeMinutes INTEGER)";

					PreparedStatement preparedStmt = DatabaseHandler.conn.prepareStatement(query);

					preparedStmt.execute();
				
				}
				
			} 
			catch (SQLException e)
			{
			
				e.printStackTrace();
			}
			
			

			try
			{
				String tableName = "WorkingDays";
				boolean isCreated = DatabaseHandler.conn.getMetaData().getTables(null, null, tableName, null).next();
				if(!isCreated)
				{
					String query = "CREATE TABLE WorkingDays(Id INTEGER PRIMARY KEY AUTO_INCREMENT,Type INTEGER,Day INTEGER,isSelected VARCHAR(50))";

					PreparedStatement preparedStmt = DatabaseHandler.conn.prepareStatement(query);;

					preparedStmt.execute();
					
					System.out.println("Created Table " + tableName);
					//weekday is equal to 0
					addWorkingDays(Program.WEEK_DAY,Day.MONDAY,false);
					addWorkingDays(Program.WEEK_DAY,Day.TUESDAY,false);
					addWorkingDays(Program.WEEK_DAY,Day.WEDNESDAY,false);
					addWorkingDays(Program.WEEK_DAY,Day.THURSDAY,false);
					addWorkingDays(Program.WEEK_DAY,Day.FRIDAY,false);
					addWorkingDays(Program.WEEK_DAY,Day.SATURDAY,false);
					addWorkingDays(Program.WEEK_DAY,Day.SUNDAY,false);
					
					
					//weekend is equal to 1
					addWorkingDays(Program.WEEK_END,Day.MONDAY,false);
					addWorkingDays(Program.WEEK_END,Day.TUESDAY,false);
					addWorkingDays(Program.WEEK_END,Day.WEDNESDAY,false);
					addWorkingDays(Program.WEEK_END,Day.THURSDAY,false);
					addWorkingDays(Program.WEEK_END,Day.FRIDAY,false);
					addWorkingDays(Program.WEEK_END,Day.SATURDAY,false);
					addWorkingDays(Program.WEEK_END,Day.SUNDAY,false);
					
					System.out.println("Data added to " + tableName);
					
				}
				else
				{
					System.out.println("Table " + tableName + " Already Exists");
				}
				
				
			} 
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			
		
			
			
			try
			{
				String tableName = "Slots";
				boolean isCreated = DatabaseHandler.conn.getMetaData().getTables(null, null, tableName, null).next();
				if(!isCreated)
				{
					String query = "CREATE TABLE Slots(Id INTEGER PRIMARY KEY AUTO_INCREMENT,Type INTEGER,Duration INTEGER,StartTimeInHours INTEGER,StartTimeInMinutes INTEGER,EndTimeInHours INTEGER,EndTimeInMinutes INTEGER)";

					PreparedStatement preparedStmt = DatabaseHandler.conn.prepareStatement(query);;

					preparedStmt.execute();
					
					System.out.println("Created Table " + tableName);
					
				}
				else
				{
					System.out.println("Table " + tableName + " Already Exists");
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			listener.onFinished(true);
			
			
			
		}
	}
	
	public static boolean addNumberOfWorkingDays(int type,int value)
	{
		
			if(DatabaseHandler.conn != null)
			{
				boolean res= checkWhetherARowExists(type);
				//if this is true update it
				//if false create the row
				
				if(!res)
				{
					//insert
					String query = " INSERT into WorkingDaysAndHours(Type,NumberOfWorkingDays)" + " VALUES (?,?)";

				    try
				    {

						PreparedStatement preparedStmt = DatabaseHandler.conn.prepareStatement(query);;

						preparedStmt.setInt(1,type);
						preparedStmt.setInt(2,value);
						
						preparedStmt.execute();
						return true;
					} 
				    catch (SQLException e)
				    {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return false;
					}
				}
				else
				{
					//update
					String query = " UPDATE WorkingDaysAndHours SET NumberOfWorkingDays=(?) WHERE Type=(?)";

					 
				    try
				    {
						PreparedStatement preparedStmt = DatabaseHandler.conn.prepareStatement(query);;

						preparedStmt.setInt(1,value);
						preparedStmt.setInt(2,type);
						preparedStmt.execute();
						return true;
					} 
				    catch (SQLException e)
				    {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return false;
					}
				}
				
				
				
			}
			return false;
		
	}
	public static boolean addWorkingTimeDuration(int type,int hours, int minutes)
	{
		
		if(DatabaseHandler.conn != null)
		{
			
			boolean res= checkWhetherARowExists(type);
			if(res)
			{
				//update
				String query = " UPDATE WorkingDaysAndHours SET WorkingTimeHours=(?),WorkingTimeMinutes=(?) WHERE Type=(?)";

				 
			    try
			    {

					PreparedStatement preparedStmt = DatabaseHandler.conn.prepareStatement(query);;

					preparedStmt.setInt(1,hours);
					preparedStmt.setInt(2,minutes);
					preparedStmt.setInt(3,type);
					
					preparedStmt.execute();
					return true;
				} 
			    catch (SQLException e)
			    {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
			}
			else
			{
				
				//insert
				String query = " INSERT into WorkingDaysAndHours(Type,WorkingTimeHours,WorkingTimeMinutes)" + " VALUES (?,?,?)";

				 
			    try
			    {

					PreparedStatement preparedStmt = DatabaseHandler.conn.prepareStatement(query);;

					preparedStmt.setInt(1,type);
					preparedStmt.setInt(2,hours);
					preparedStmt.setInt(3,minutes);
					
					preparedStmt.execute();
					return true;
				} 
			    catch (SQLException e)
			    {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
			}
			
		}
		
		
		return false;
	}
	public static boolean deleteNumberOfWorkingDays(int type)
	{
		boolean res= checkWhetherARowExists(type);
		if(res)
		{
			//update
			String query = " UPDATE WorkingDaysAndHours SET NumberOfWorkingDays=(?) WHERE Type=(?)";

			 
		    try
		    {

				PreparedStatement preparedStmt = DatabaseHandler.conn.prepareStatement(query);;

				preparedStmt.setInt(1,-99);
				preparedStmt.setInt(2,type);
	
				
				preparedStmt.execute();
				return true;
			} 
		    catch (SQLException e)
		    {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
	public static boolean deleteWorkingTimeDuration(int type)
	{
		boolean res= checkWhetherARowExists(type);
		if(res)
		{
			//update
			String query = " UPDATE WorkingDaysAndHours SET WorkingTimeHours=(?),WorkingTimeMinutes=(?) WHERE Type=(?)";

			 
		    try
		    {

				PreparedStatement preparedStmt = DatabaseHandler.conn.prepareStatement(query);;

				preparedStmt.setInt(1,-99);
				preparedStmt.setInt(2,-99);
				preparedStmt.setInt(3,type);
	
				
				preparedStmt.execute();
				return true;
			} 
		    catch (SQLException e)
		    {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
	public static ResultSet sync(int type)
	{
		
		if(DatabaseHandler.conn != null)
		{
			String query = " SELECT * FROM WorkingDaysAndHours WHERE Type=(?)";

			 
		    try
		    {

				PreparedStatement preparedStmt = DatabaseHandler.conn.prepareStatement(query);;

				preparedStmt.setInt(1,type);
				return preparedStmt.executeQuery();
			} 
		    catch (SQLException e)
		    {
				e.printStackTrace();
			}
		}
		return null;
	}
	public static boolean checkWhetherARowExists(int type)
	{
		if(DatabaseHandler.conn != null)
		{
			String query = "SELECT * FROM WorkingDaysAndHours WHERE Type=(?)";

			 
		    try
		    {

				PreparedStatement preparedStmt = DatabaseHandler.conn.prepareStatement(query);;

				preparedStmt.setInt(1,type);
				
				ResultSet set = preparedStmt.executeQuery();
				if(set != null)
				{
					set.next();
					boolean value = set.isLast();
					return value;
				
				}
				return false;
			} 
		    catch (SQLException e)
		    {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
	public static boolean createNewSlot(int type,int duration,int startHours,int startMinutes,int endHours,int endMinutes)
	{
		if(DatabaseHandler.conn != null)
		{
			//insert
			String query = " INSERT into Slots(Type,Duration,StartTimeInHours,StartTimeInMinutes,EndTimeInHours,EndTimeInMinutes)" + " VALUES (?,?,?,?,?,?)";
		    try
		    {

				PreparedStatement preparedStmt = DatabaseHandler.conn.prepareStatement(query);;

				preparedStmt.setInt(1,type);
				preparedStmt.setInt(2,duration);
				preparedStmt.setInt(3,startHours);
				preparedStmt.setInt(4,startMinutes);
				preparedStmt.setInt(5,endHours);
				preparedStmt.setInt(6,endMinutes);
				
				preparedStmt.execute();
				return true;
			} 
		    catch (SQLException e)
		    {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		
		return false;
		
		
	}
	public static ResultSet getSlotsByProgram(int type)
	{
		if(DatabaseHandler.conn != null)
		{
			String query = " SELECT * FROM Slots WHERE Type=(?) ORDER BY EndTimeInHours ASC";

			 
		    try
		    {
				PreparedStatement preparedStmt = DatabaseHandler.conn.prepareStatement(query);;

				preparedStmt.setInt(1,type);
				return preparedStmt.executeQuery();
			} 
		    catch (SQLException e)
		    {
				e.printStackTrace();
			}
		}
		return null;
	}
	public static int getSlotsCountByProgram(int type)
	{
		if(DatabaseHandler.conn != null)
		{
			String query = " SELECT * FROM Slots WHERE Type=(?) ORDER BY EndTimeInHours ASC";

			 
		    try
		    {

				PreparedStatement preparedStmt = DatabaseHandler.conn.prepareStatement(query);;

				preparedStmt.setInt(1,type);
				ResultSet set = preparedStmt.executeQuery();
				
				set.last();
				int size = set.getRow();
				return size;
			} 
		    catch (SQLException e)
		    {
				e.printStackTrace();
			}
		}
		
		return 0;
	}
	public static ResultSet getWorkingDays(int type)
	{
		
		if(DatabaseHandler.conn != null)
		{
			String query = " SELECT * FROM WorkingDays WHERE Type=(?)";

			 
		    try
		    {
				PreparedStatement preparedStmt = DatabaseHandler.conn.prepareStatement(query);;

				preparedStmt.setInt(1,type);
				return preparedStmt.executeQuery();
			} 
		    catch (SQLException e)
		    {
				e.printStackTrace();
			}
		}
		return null;
	}
	public static boolean updateWorkingDays(int type,int day,boolean isTrue)
	{
			if(DatabaseHandler.conn != null)
			{
				String query = " UPDATE WorkingDays SET isSelected=(?) WHERE Type=(?) AND Day=(?)";

			    try
			    {
					PreparedStatement preparedStmt = DatabaseHandler.conn.prepareStatement(query);;

					preparedStmt.setString(1,String.valueOf(isTrue));
					preparedStmt.setInt(2, type);
					preparedStmt.setInt(3, day);
					preparedStmt.execute();
				} 
			    catch (SQLException e)
			    {
					e.printStackTrace();
				}
			}
		return true;
	}
	public static boolean addWorkingDays(int type,int day,boolean isSelected)
	{
		
		if(DatabaseHandler.conn != null)
		{
			String query = " INSERT INTO WorkingDays(Type,Day,isSelected) VALUES(?,?,?)";

		    try
		    {
				PreparedStatement preparedStmt = DatabaseHandler.conn.prepareStatement(query);;

				preparedStmt.setInt(1, type);
				preparedStmt.setInt(2, day);
				preparedStmt.setString(3,String.valueOf(isSelected));
				
				preparedStmt.execute();
				
				return true;
			} 
		    catch (SQLException e)
		    {
				e.printStackTrace();
			}
		}
		
		return false;
	}	
	
	
		
	
}
