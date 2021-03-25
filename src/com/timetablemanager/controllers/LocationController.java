/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timetablemanager.controllers;

import com.timetablemanager.models.Location;
import com.timetablemanager.utils.ConnectionUtil;
import com.timetablemanager.utils.IDGenerator;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Rajindu's PC
 */
public class LocationController implements Initializable {

    @FXML
    private TableView<Location> tvLocations;
    @FXML
    private TableColumn<Location, String> colId;
    @FXML
    private TableColumn<Location, String> colBuilding;
    @FXML
    private TableColumn<Location, String> colRoom;
    @FXML
    private TableColumn<Location, String> colRoomType;
    @FXML
    private TableColumn<Location, Integer> colCapacity;
    @FXML
    private TextField tfBuilding;
    @FXML
    private TextField tfRoom;
    @FXML
    private TextField tfCapacity;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnSave;
    @FXML
    private RadioButton rb1;
    @FXML
    private ToggleGroup rbGender;
    @FXML
    private RadioButton rb2;
    
    private ConnectionUtil conUtil = new ConnectionUtil();
    private Connection conn;
    private Statement st;
    private ResultSet rs;
    private String roomType = null;
    private String locId = null;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showLocations();
    }    

    @FXML
    private void buttonHandler(ActionEvent event) {
        Location location = tvLocations.getSelectionModel().getSelectedItem();
        if(event.getSource() == btnSave){
            insertRecord();
        }else if(event.getSource() == btnUpdate && location != null){
            updateRecord();
        }else if(event.getSource() == btnDelete && location != null){
            deleteRecord();
            clearRecord();
        }else if(event.getSource() == btnClear){
            clearRecord();
        }
    }
    
    @FXML
    private void roomTypeHandler(ActionEvent event) {
        if(rb1.isSelected()){
            roomType = rb1.getText();
        }else if(rb2.isSelected()){
            roomType = rb2.getText();
        }else{
            System.out.println("Not Selected");
        }
    }
    
     @FXML
    private void handleMouseAction(MouseEvent event) {
        Location location = tvLocations.getSelectionModel().getSelectedItem();
        locId = location.getId();
        tfBuilding.setText(location.getBuilding());
        tfRoom.setText(location.getRoom());
        if(location.getType().equals("Lecture Hall")){
            rb1.setSelected(true);
        }else if(location.getType().equals("Laboratary")){
            rb2.setSelected(true);
        }
        tfCapacity.setText(String.valueOf(location.getCapacity()));
    }
    
    public ObservableList<Location> getLocationList(){
        ObservableList<Location> locationList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query = "SELECT * FROM locations";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Location location;
            while(rs.next()){
                location = new Location(rs.getString("id"), rs.getString("building"), rs.getString("room"), rs.getString("type"),rs.getInt("capacity"));
                locationList.add(location);
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
        return locationList;
    }
    
    public void showLocations(){
        ObservableList<Location> list = getLocationList();
       
        colId.setCellValueFactory(new PropertyValueFactory<Location, String>("id"));
        colBuilding.setCellValueFactory(new PropertyValueFactory<Location, String>("building"));
        colRoom.setCellValueFactory(new PropertyValueFactory<Location, String>("room"));
        colRoomType.setCellValueFactory(new PropertyValueFactory<Location, String>("type"));
        colCapacity.setCellValueFactory(new PropertyValueFactory<Location, Integer>("capacity"));
        
        tvLocations.setItems(list);
    }
    
    private void insertRecord(){
        IDGenerator nextId = new IDGenerator();
        String nextgeneratedId = nextId.generateId("locations", "R");
        String query = "INSERT INTO locations VALUES (" + "'" + nextgeneratedId + "'" + ",'" + tfBuilding.getText() + "','" + tfRoom.getText() + "',"
                + "'" + roomType + "'" + "," + Integer.parseInt(tfCapacity.getText()) + ")";
        executeQuery(query);
        showLocations();
    }
    
    private void updateRecord(){
        String query = "UPDATE  locations SET building  = '" + tfBuilding.getText() + "', room = '" + tfRoom.getText() + "', type = " +
                "'" + roomType + "'" + ", capacity = " + Integer.parseInt(tfCapacity.getText()) + " WHERE id = " + "'" + locId + "'";
        executeQuery(query);
        showLocations();
    }    
    
    private void deleteRecord(){
        String query = "DELETE FROM locations WHERE id = " + "'" + locId + "'";
        executeQuery(query);
        showLocations();
    }
    
    private void clearRecord(){
        tfBuilding.setText(null);
        tfRoom.setText(null);
        rb1.setSelected(false);
        rb2.setSelected(false);
        tfCapacity.setText(null);
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
