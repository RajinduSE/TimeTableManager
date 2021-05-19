/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timetablemanager.controllers;

import com.timetablemanager.utils.ConnectionUtil;
import com.timetablemanager.utils.IDGenerator;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

/**
 * FXML Controller class
 *
 * @author Rajindu's PC
 */
public class ParellelSessionsSlotsMngController implements Initializable {

    @FXML
    private ComboBox<String> cbCatergory;
    @FXML
    private ComboBox<String> cbSession;
    @FXML
    private ComboBox<String> cbRoom;
    @FXML
    private ComboBox<String> cbDay;
    @FXML
    private ComboBox<String> cbSlot;
    
    private ConnectionUtil conUtil = new ConnectionUtil();
    private Connection conn;
    private Statement st;
    private PreparedStatement ps;
    private ResultSet rs;
    private String category = null;
    private String session;
    private String room;
    private String day;
    private String slot;
    
    ObservableList<String> categories = FXCollections.observableArrayList("C1","C2","C3","C4");
    ObservableList<String> days = FXCollections.observableArrayList("Monday","Tuesday","Wednesday","Thursday","Friday");
    ObservableList<String> slots = FXCollections.observableArrayList("8.30","9.30","10.30","11.30","12.30","13.30","14.30","15.30","16.30","17.30");
    @FXML
    private Button btnSave;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cbCatergory.setItems(categories);
        cbRoom.setItems(getRoomList());
        cbDay.setItems(days);
        cbSlot.setItems(slots);
    }    

    @FXML
    private void changeCatergory(ActionEvent event) {
        category = cbCatergory.getValue();
        cbSession.setItems(getSessions());
    }

    @FXML
    private void changeSession(ActionEvent event) {
        session = cbSession.getValue();
    }

    @FXML
    private void changeRoom(ActionEvent event) {
        room = cbRoom.getValue();
    }

    @FXML
    private void changeDay(ActionEvent event) {
        day = cbDay.getValue();
    }

    @FXML
    private void changeSlot(ActionEvent event) {
        slot = cbSlot.getValue();
    }

    @FXML
    private void buttonHandler(ActionEvent event) {
        if(event.getSource() == btnSave){
            insertRecord();
        }
    }
    
    public ObservableList<String> getSessions(){
        ObservableList<String> sessionList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        if(category.equals(null) || category.equals("Select Catergory")){
            return null;
        }
        String query = "SELECT session FROM parallel_sessions_t WHERE catergory = " + "'" + category + "'";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            String session;
            while(rs.next()){
                session = rs.getString("session");
                sessionList.add(session);
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
        return sessionList;
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
    
    public void insertRecord(){
        conn = conUtil.getConnection(); 
        IDGenerator nextId = new IDGenerator();
        String nextgeneratedId = nextId.generateId("time_table_all", "T");
        try{   
            // create a prepared statement
            String query = "INSERT INTO time_table_all(`id`, `day`, `slot`, `session`, `room`) VALUES (?, ?, ?, ?, ?)"; 
            ps = conn.prepareStatement(query); 
            // binding values
            ps.setString(1, nextgeneratedId); 
            ps.setString(2, day); 
            ps.setString(3, slot);
            ps.setString(4, session);
            ps.setString(5, room);
       
            //execute the statement
             ps.execute();             
        }catch (Exception e){ 
            e.printStackTrace();
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
