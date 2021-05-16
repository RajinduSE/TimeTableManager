/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timetablemanager.controllers;

import java.net.URL;
import java.util.ResourceBundle;



import com.timetablemanager.models.Day;
import com.timetablemanager.models.Program;
import com.timetablemanager.models.Duration;
import com.timetablemanager.utils.QueriesOfWorkingDays;
import java.io.IOException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author Shelani Wijesekera
 */
public class TimeSlotsController implements Initializable {

    /**
     * Initializes the controller class.
     */
   
        // TODO
        @FXML
	private Button clearTimeSlotbtn;
	@FXML
	private ComboBox<Object> combo_working_days_type;
	
	@FXML
	private RadioButton oneHourRadio,thirtyMinutesradio;
	
	@FXML
	private TextField startHoursText,startMinutesText;
	
	
	private int programType = -99;
	
	@FXML
    private void navigateTimeSlots(ActionEvent event) {
        try {
            Stage stage = (Stage)clearTimeSlotbtn.getScene().getWindow();
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
        
	
	public void onAddSlotButtonClicked(ActionEvent event)
	{
		if(programType != -99)
		{
			try
			{
				String hours = startHoursText.getText();
				String minutes = startMinutesText.getText();
				int intHours = Integer.parseInt(hours);
				int intMinutes = Integer.parseInt(minutes);
				
				
				boolean isOneHour = oneHourRadio.isSelected();
				boolean isThirty = thirtyMinutesradio.isSelected();
				
				if(isOneHour || isThirty)
				{
					if(isOneHour)
					{
						boolean res = QueriesOfWorkingDays.createNewSlot(programType, Duration.ONE_HOUR, intHours, intMinutes,(intHours + 1),intMinutes);
						if(res)
						{
							showAlert("Created");
						}
						else
						{
							showAlert("Failed to create");
						}
					}
					else
					{
						if(intMinutes == 0)
						{
							boolean res = QueriesOfWorkingDays.createNewSlot(programType, Duration.THIRTY_MINUTES, intHours, intMinutes,intHours,(intMinutes + 30));
							if(res)
							{
								showAlert("Created");
							}
							else
							{
								showAlert("Failed to create");
							}
						}
						else
						{
							boolean res = QueriesOfWorkingDays.createNewSlot(programType, Duration.THIRTY_MINUTES, intHours, intMinutes,(intHours + 1),00);
							if(res)
							{
								showAlert("Created");
							}
							else
							{
								showAlert("Failed to create");
							}
						}
						
					}
				}
				else
				{
					showAlert("Please select the duration");
				}
				
			}
			catch(Exception e)
			{
				showAlert("Please specify only numbers");
			}
			
			
			
		}
		else
		{
			showAlert("Please select Program Type");
		}
	}
	public void showAlert(String message)
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText(message);
		alert.show();
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		// TODO Auto-generated method stub
		
		initializeWorkingDaysTypeCombo();
		
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
	    }
	    ); 
		
		oneHourRadio.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected)
		    {
		        if(isNowSelected && thirtyMinutesradio.isSelected())
		        {
		        	thirtyMinutesradio.setSelected(false);
		        }
		    }
		});
		
		thirtyMinutesradio.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected)
		    {
		    	if(isNowSelected && oneHourRadio.isSelected())
		        {
		    		oneHourRadio.setSelected(false);
		        }
		    }
		});
		
	}
	private void initializeWorkingDaysTypeCombo()
	{
		ObservableList<Object> data = FXCollections.observableArrayList();
		data.add("Weekday");
		data.add("Weekend");
		combo_working_days_type.setItems(null);
		combo_working_days_type.setItems(data);
		
//		combo_working_days_type.getSelectionModel().selectFirst();
		
	
	}

       
    
}
