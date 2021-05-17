/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timetablemanager.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import com.timetablemanager.models.NotAvailableLocation;
import com.timetablemanager.utils.ConnectionUtil;
import com.timetablemanager.utils.IDGenerator;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Shelani Wijesekera
 */
public class LocationNotAvailableController implements Initializable {

      @FXML
    private TableView<NotAvailableLocation> tnALocation;

    @FXML
    private TableColumn<NotAvailableLocation, String> colId;

    @FXML
    private TableColumn<NotAvailableLocation, String> colRoom;

    @FXML
    private TableColumn<NotAvailableLocation, String> colDay;

    @FXML
    private TableColumn<NotAvailableLocation, Integer> colSTime;

    @FXML
    private TableColumn<NotAvailableLocation, Integer> colETime;

     @FXML
    private ComboBox<String> cbRoom;

    @FXML
    private ComboBox<String> cbDay;

    @FXML
    private TextField tfStartTime;

    @FXML
    private TextField tfEndTime;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnSave;

    
    
    
    private ConnectionUtil conUtil = new ConnectionUtil();
    private Connection conn;
    private Statement st;
    private ResultSet rs;
    private String NotAvailableLocationId;
    private String day;
    private String room;

    
    
    ObservableList<String> DayList = FXCollections.observableArrayList("Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday");

    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showNotAvailableLocation();
        
        cbRoom.setItems(getRoomList());
        cbDay.setItems(DayList);
        
    } 
    
    @FXML
    void handleAction(ActionEvent event) {
        NotAvailableLocation NotAvailableLocation = tnALocation.getSelectionModel().getSelectedItem();
        if(event.getSource() == btnSave){
            insertRecord();
        }else if(event.getSource() == btnUpdate && NotAvailableLocation != null){
            updateRecord();
        }else if(event.getSource() == btnDelete && NotAvailableLocation != null){
            deleteRecord();
            clearRecord();
        }else if(event.getSource() == btnClear){
            clearRecord();
        }
    }
    
    @FXML
    private void handleMouseAction(MouseEvent event) {
        NotAvailableLocation NotAvailableLocation = tnALocation.getSelectionModel().getSelectedItem();
        NotAvailableLocationId = NotAvailableLocation.getId();
        cbRoom.setValue(NotAvailableLocation.getRoom());
        cbDay.setValue(NotAvailableLocation.getDay());
        tfStartTime.setText(String.valueOf(NotAvailableLocation.getStartTime()));
        tfEndTime.setText(String.valueOf(NotAvailableLocation.getEndTime()));
        
    }

    @FXML
    void roomChanger(ActionEvent event) {
        room = cbRoom.getValue();
    }
    
     @FXML
    void dayChanger(ActionEvent event) {
        day = cbDay.getValue();
    }
    
    public ObservableList<String> getRoomList(){
        ObservableList<String> roomList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query = "SELECT room FROM locations";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            String room;
            while(rs.next()){
                room = rs.getString("room");
                roomList.add(room);
            }   
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                if(conn != null){
                    conn.close();
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return roomList;
    }
    
    public ObservableList<NotAvailableLocation> getNotAvailableLocationList(){
        ObservableList<NotAvailableLocation> NotAvailableLocationList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query = "SELECT * FROM not_available_location";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            NotAvailableLocation NotAvailableLocation;
            while(rs.next()){
                NotAvailableLocation = new NotAvailableLocation(rs.getString("id"), rs.getString("room"), rs.getInt("stime"), rs.getInt("etime"), rs.getString("day"));
                NotAvailableLocationList.add(NotAvailableLocation);
            }   
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                if(conn != null){
                    conn.close();
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return NotAvailableLocationList;
    }
    
    public void showNotAvailableLocation(){
        ObservableList<NotAvailableLocation> list = getNotAvailableLocationList();
       
        colId.setCellValueFactory(new PropertyValueFactory<NotAvailableLocation, String>("id"));
        colRoom.setCellValueFactory(new PropertyValueFactory<NotAvailableLocation, String>("room"));
        colSTime.setCellValueFactory(new PropertyValueFactory<NotAvailableLocation, Integer>("startTime"));
        colETime.setCellValueFactory(new PropertyValueFactory<NotAvailableLocation, Integer>("endTime"));
        colDay.setCellValueFactory(new PropertyValueFactory<NotAvailableLocation, String>("day"));
        
        
        
        tnALocation.setItems(list);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void insertRecord(){
        IDGenerator nextId = new IDGenerator();
        String nextgeneratedId = nextId.generateId("not_available_location", "NL");
        String query = "INSERT INTO not_available_location VALUES (" + "'" + nextgeneratedId + "'" + "," + "'" + room + "'" + "," + "'" + tfStartTime.getText() + "'" + "," + "'" +  tfEndTime.getText()
                     + "'" + "," + "'" + day + "'" + ")";
        executeQuery(query);
        showNotAvailableLocation();
    }
    
    private void updateRecord(){
        String query = "UPDATE  not_available_location SET room  = '" + room + "', day = '" + day + "', stime = '" + tfStartTime.getText() + "', etime = '" + tfEndTime.getText() +"'  WHERE id = " + "'" + NotAvailableLocationId + "'";
        executeQuery(query);
        showNotAvailableLocation();
    }    
    
    private void deleteRecord(){
        String query = "DELETE FROM not_available_location WHERE id = " + "'" + NotAvailableLocationId + "'";
        executeQuery(query);
        showNotAvailableLocation ();
    }
    
    private void clearRecord(){
        
        cbRoom.setValue("");
        cbDay.setValue("");
        tfStartTime.setText(null);
        tfEndTime.setText(null);
    }
    
    private void executeQuery(String query) {
        conn = conUtil.getConnection();
        try{
            st = conn.createStatement();
            st.executeUpdate(query);
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                if(conn != null){
                    conn.close();
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
    
    
    
}
