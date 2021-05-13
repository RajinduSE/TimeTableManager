/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timetablemanager.controllers;

import com.mysql.jdbc.PreparedStatement;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.timetablemanager.models.Day;
import com.timetablemanager.models.Program;
import com.timetablemanager.utils.DatabaseHandler;
import com.timetablemanager.utils.OnTaskCompleteListener;
import com.timetablemanager.utils.QueriesOfWorkingDays;
import java.io.IOException;
import java.sql.Connection;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;





/**
 * FXML Controller class
 *
 * @author Shelani Wijesekera
 */
public class WorkingDayHourController implements Initializable,OnTaskCompleteListener {

    
        @FXML
	private ComboBox<Object> combo_working_days_type;


	
	@FXML
	private ComboBox<Integer> combo_number_of_working_days;
	@FXML
	private Button addTimeSlotButton;
	@FXML
	private Button numberOfWorkingDaysAddBtn,workingTimeDurationAddBtn,workingDaysAddBtn;
	@FXML
	private TextField hoursTextFiled,minutesTextFiled;
	@FXML
	private AnchorPane workingDaysMainPane;
	
	
	
	@FXML
	private CheckBox MondayCombo,TuesdayCombo,WednesdayCombo,ThursdayCombo,FridayCombo,SaturdayCombo,SundayCombo;
	
	
	private int programType = Program.WEEK_DAY;
        
        //private ConnectionUtil conUtil = new ConnectionUtil();
        //private Connection conn;
        

        
	
        
        @Override
	public void initialize(URL location, ResourceBundle resources) 
	{
            DatabaseHandler.makeConnection();
            QueriesOfWorkingDays.createTables(WorkingDayHourController.this);
           
                //Runnable r = new Runnable() 
		 //{
	         //public void run()
	         //{
	        	 
                         
	         //}
	     //};
	     //new Thread(r).start();
            
              
                
               
	
		

		//listeners
		combo_working_days_type.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
	         
			if(newValue.equals("Weekend"))
			{
				programType = Program.WEEK_END;
			}
			else if(newValue.equals("Weekday"))
			{
				programType = Program.WEEK_DAY;
			}
			
			setupNumberOfWorkingDaysRow();
			setCheckBoxes();
	    }
	    ); 
	
		
	}
	private void initializeWorkingDaysTypeCombo()
	{
		ObservableList<Object> data = FXCollections.observableArrayList();
		data.add("Weekday");
		data.add("Weekend");

		
		Platform.runLater(new Runnable()
		{

			@Override
			public void run() {
				combo_working_days_type.setItems(null);
				combo_working_days_type.setItems(data);
				
				combo_working_days_type.getSelectionModel().selectFirst();
				
			}
			  
		});
		
		
	
	}
	private void initializeNumberOfWorkingDaysCombo(int value)
	{
		ObservableList<Integer> data = FXCollections.observableArrayList();
		data.add(1);
		data.add(2);
		data.add(3);
		data.add(4);
		data.add(5);
		data.add(6);
		data.add(7);
		
		Platform.runLater(new Runnable()
		{

			@Override
			public void run() 
			{
				combo_number_of_working_days.setItems(null);
				combo_number_of_working_days.setItems(data);
				
				if(value != -99)
				{
					combo_number_of_working_days.getSelectionModel().clearAndSelect(value - 1);
				}
				
			}
			  
		});
		
		
	}
        
	
	public void setupNumberOfWorkingDaysRow()
	{
		ResultSet set = QueriesOfWorkingDays.sync(programType);
		if(set != null)
		{
			try
			{
				set.next();
				int workingDays = set.getInt(2);
				if(workingDays != -99)
				{
					initializeNumberOfWorkingDaysCombo(workingDays);
					setNumberOfWorkingdaysButtonText("Update");
				}
				else
				{
					setNumberOfWorkingdaysButtonText("Add");
					initializeNumberOfWorkingDaysCombo(-99);
				}
				
			} 
			catch (SQLException e)
			{
				setNumberOfWorkingdaysButtonText("Add");
				initializeNumberOfWorkingDaysCombo(-99);
			}
			
			setupWorkingTimeDuration(set);
		}
	}
	public void setupWorkingTimeDuration(ResultSet set)
	{
		try
		{
			int hours = set.getInt(3);
			int minutes = set.getInt(4);
			
			if(hours != -99 && minutes != -99)
			{
				
				Platform.runLater(new Runnable()
				{

					@Override
					public void run() {
						hoursTextFiled.setText("" + hours);
						minutesTextFiled.setText("" + minutes);
						workingTimeDurationAddBtn.setText("Update");
                                                ////////////////
                                              
						
					}
					  
				});
				
			}
			else
			{
				Platform.runLater(new Runnable()
				{

					@Override
					public void run() {
						// TODO Auto-generated method stub
						hoursTextFiled.setText("");
						minutesTextFiled.setText("");
                                                //newly added
						workingTimeDurationAddBtn.setText("Add");
					}
					  
				});
				
			}
		} 
		catch (SQLException e)
		{
			Platform.runLater(new Runnable()
			{

				@Override
				public void run() {
					// TODO Auto-generated method stub
					workingTimeDurationAddBtn.setText("Add");
					hoursTextFiled.setText("");
					minutesTextFiled.setText("");
				}
				  
			});
			
		}
	}
	public void addNumberOfWorkingDays(ActionEvent event)
	{
		
		try
		{

			
			 Runnable r = new Runnable() 
			 {
		         public void run()
		         {
		        	 int item = combo_number_of_working_days.getSelectionModel().getSelectedItem();
		 			 boolean res = QueriesOfWorkingDays.addNumberOfWorkingDays(programType,item);
		 			 setupNumberOfWorkingDaysRow();
		 			Platform.runLater(new Runnable()
		     		{

						@Override
						public void run() 
						{
							
						}
		     		   
		     		});
		         }
		     };
		     new Thread(r).start();
			
			
		}
		catch(Exception e)
		{
			showAlert("Please choose a number first");
			
		}
		
	}
	public void deleteNumberOfWorkingDays(ActionEvent event)
	{
		boolean res = QueriesOfWorkingDays.deleteNumberOfWorkingDays(programType);
		if(res)
		{
			showAlert("Success");
			setupNumberOfWorkingDaysRow();
                        //new new
                       // workingDaysAddBtn.setText("Add");
		}
		else
		{
			showAlert("Failed");
		}
	}
	public void addWorkingTimeDuration(ActionEvent event)
	{
		try
		{
			String hours = hoursTextFiled.getText().toString();
			String minutes = minutesTextFiled.getText().toString();
			
			int hoursInt = Integer.parseInt(hours);
			int minutesInt = Integer.parseInt(minutes);
			
			boolean res = QueriesOfWorkingDays.addWorkingTimeDuration(programType,hoursInt, minutesInt);
			if(res)
			{
				showAlert("Successfull");
			}
			else
			{
				showAlert("Unsuccessfull");
			}
			
			setupNumberOfWorkingDaysRow();
		}
		catch(Exception e)
		{
			showAlert("Please enter only Integers");
		}
	
	}
	public void deleteWorkingTimeDuration(ActionEvent event)
	{
		boolean res = QueriesOfWorkingDays.deleteWorkingTimeDuration(programType);
		if(res)
		{
			showAlert("Success");
			setupNumberOfWorkingDaysRow();
		}
		else
		{
			showAlert("Failed");
		}
	}
	public void showAlert(String message)
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText(message);
		alert.show();
	}
	public void setCheckBoxes()
	{
		ResultSet set = QueriesOfWorkingDays.getWorkingDays(programType);
		if(set != null)
		{
			try
			{
				while(set.next())
				{
					int day = set.getInt(3);
					String isSelected = set.getString(4);
					boolean isSelect = Boolean.parseBoolean(isSelected);
					
						if(day == Day.MONDAY)
						{
							if(isSelect)
								MondayCombo.setSelected(true);
							else
								MondayCombo.setSelected(false);
						}
						else if(day == Day.TUESDAY)
						{
							if(isSelect)
								TuesdayCombo.setSelected(true);
							else
								TuesdayCombo.setSelected(false);
							
						}
						else if(day == Day.WEDNESDAY)
						{
							if(isSelect)
								WednesdayCombo.setSelected(true);
							else
								WednesdayCombo.setSelected(false);
							
						}
						else if(day == Day.THURSDAY)
						{
							if(isSelect)
								ThursdayCombo.setSelected(true);
							else
								ThursdayCombo.setSelected(false);
							
						}
						else if(day == Day.FRIDAY)
						{
							if(isSelect)
								FridayCombo.setSelected(true);
							else
								FridayCombo.setSelected(false);
							
						}
						else if(day == Day.SATURDAY)
						{
							if(isSelect)
								SaturdayCombo.setSelected(true);
							else
								SaturdayCombo.setSelected(false);
							
						}
						else if(day == Day.SUNDAY)
						{
							if(isSelect)
								SundayCombo.setSelected(true);
							else
								SundayCombo.setSelected(false);
							
						}
					
					
				}
			} 
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
		//new new
                        workingDaysAddBtn.setText("Add");	
		}
	}
	public void onWorkingDaysUpdateButtonClicked(ActionEvent event)
	{
		
		 int item = combo_number_of_working_days.getSelectionModel().getSelectedItem();
		 int count = 0;
		 if(MondayCombo.isSelected())
		 {
			 count++;
		 }
		 if(TuesdayCombo.isSelected())
		 {
			 count++;
		 }
		 if(WednesdayCombo.isSelected())
		 {
			 count++;
		 }
		 if(ThursdayCombo.isSelected())
		 {
			 count++;
		 }
		 if(FridayCombo.isSelected())
		 {
			 count++;
		 }
		 if(SaturdayCombo.isSelected())
		 {
			 count++;
		 }
		 if(SundayCombo.isSelected())
		 {
			 count++;
		 }
		 
		 
		 if(count > item)
		 {
			 showAlert("Please increase the number of working days");
			 return;
		 }
		 if(count < item)
		 {
			 showAlert("Please decrease the number of working days");
			 return;
		 }
		
		
		
		
		
		
	
		Runnable r = new Runnable() 
		 {
	         public void run()
	         {
	        	 boolean res = QueriesOfWorkingDays.updateWorkingDays(programType,Day.MONDAY, MondayCombo.isSelected());
	     		res = QueriesOfWorkingDays.updateWorkingDays(programType,Day.TUESDAY, TuesdayCombo.isSelected());
	     		res = QueriesOfWorkingDays.updateWorkingDays(programType,Day.WEDNESDAY, WednesdayCombo.isSelected());
	     		res = QueriesOfWorkingDays.updateWorkingDays(programType,Day.THURSDAY, ThursdayCombo.isSelected());
	     		res = QueriesOfWorkingDays.updateWorkingDays(programType,Day.FRIDAY, FridayCombo.isSelected());
	     		res = QueriesOfWorkingDays.updateWorkingDays(programType,Day.SATURDAY, SaturdayCombo.isSelected());
	     		res = QueriesOfWorkingDays.updateWorkingDays(programType,Day.SUNDAY, SundayCombo.isSelected());
	     		
	     		
	     		Platform.runLater(new Runnable()
	     		{

					@Override
					public void run() 
					{
						//progressDialogVBox.setVisible(false);
						setCheckBoxes();
                                                /////////new new
                                                workingDaysAddBtn.setText("Update");
                                                showAlert("Success");
						//disableOrEnableBackground(false);
						
					}
	     		   
	     		});
	         }
		 };
		 new Thread(r).start();
	
		
	}
	public void onWorkingDaysDeleteButtonClicked(ActionEvent event)
	{
		
		
		 Runnable r = new Runnable() 
		 {
	         public void run()
	         {
	        	boolean res = QueriesOfWorkingDays.updateWorkingDays(programType,Day.MONDAY, false);
	     		res = QueriesOfWorkingDays.updateWorkingDays(programType,Day.TUESDAY, false);
	     		res = QueriesOfWorkingDays.updateWorkingDays(programType,Day.WEDNESDAY, false);
	     		res = QueriesOfWorkingDays.updateWorkingDays(programType,Day.THURSDAY, false);
	     		res = QueriesOfWorkingDays.updateWorkingDays(programType,Day.FRIDAY, false);
	     		res = QueriesOfWorkingDays.updateWorkingDays(programType,Day.SATURDAY, false);
	     		res = QueriesOfWorkingDays.updateWorkingDays(programType,Day.SUNDAY, false);
	     		
	     		Platform.runLater(new Runnable()
	     		{

					@Override
					public void run() 
					{
						
						setCheckBoxes();
                                                //new new
                                                workingDaysAddBtn.setText("Add");
						
						
					}
	     		   
	     		});
	     		
	         }
	     };
	     new Thread(r).start();
		
	}
	@Override
	public void onFinished(boolean isSuccess)
	{
		
		
		initializeWorkingDaysTypeCombo();
		setupNumberOfWorkingDaysRow();
		setCheckBoxes();
		
	}
	public void setNumberOfWorkingdaysButtonText(String text)
	{
		Platform.runLater(new Runnable()
		{

			@Override
			public void run() 
			{
				numberOfWorkingDaysAddBtn.setText(text);
			 			}

		});
		
	}
	
	
        @FXML
    private void navigateTimeSlots(ActionEvent event) {
        try {
            Stage stage = (Stage)addTimeSlotButton.getScene().getWindow();
            stage.close();
            Stage locationStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/timetablemanager/views/TimeSlots.fxml"));
            locationStage.setResizable(false);
            Scene scene = new Scene(root);
            locationStage.setScene(scene);
            locationStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
      
    
    
        
}



    

