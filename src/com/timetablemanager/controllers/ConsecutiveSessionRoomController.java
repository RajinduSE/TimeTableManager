/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timetablemanager.controllers;

import com.timetablemanager.models.ConsecutiveSessionRoom;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Rajindu's PC
 */
public class ConsecutiveSessionRoomController implements Initializable {

    @FXML
    private TableView<ConsecutiveSessionRoom> tvConsecSessions;
    @FXML
    private TableColumn<ConsecutiveSessionRoom, String> colId;
    @FXML
    private TableColumn<ConsecutiveSessionRoom, String> colSession1;
    @FXML
    private TableColumn<ConsecutiveSessionRoom, String> colSession2;
    @FXML
    private TableColumn<ConsecutiveSessionRoom, String> colRoom;
    @FXML
    private ComboBox<String> cbSession1;
    @FXML
    private ComboBox<String> cbSession2;
    @FXML
    private ComboBox<String> cbRoom;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnSave;

    private ConnectionUtil conUtil = new ConnectionUtil();
    private Connection conn;
    private Statement st;
    private PreparedStatement ps;
    private ResultSet rs;
    
    private String session1;
    private String session2;
    private String room;
    private String id;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showConsSessionRoomList();
        cbSession1.setItems(getLecSessions());
        cbSession2.setItems(getTuteSessions());
        cbRoom.setItems(getRoomList());
    }    

    @FXML
    private void handleMouseAction(MouseEvent event) {
        ConsecutiveSessionRoom conSessionRoom = tvConsecSessions.getSelectionModel().getSelectedItem();
        id = conSessionRoom.getId();
        cbSession1.setValue("Lecture Session");
        cbSession2.setValue("Tutorial Session");
        cbRoom.setValue("Select Room");
    }

    @FXML
    private void changeSession1(ActionEvent event) {
        session1 = cbSession1.getValue();
    }

    @FXML
    private void changeSession2(ActionEvent event) {
        session2 = cbSession2.getValue();
    }

    @FXML
    private void changeRoom(ActionEvent event) {
        room = cbRoom.getValue();
    }

    @FXML
    private void buttonHandler(ActionEvent event) {
        ConsecutiveSessionRoom conSessionRoom = tvConsecSessions.getSelectionModel().getSelectedItem();
        if(event.getSource() == btnSave){
            insertRecord();
        }else if(event.getSource() == btnDelete && conSessionRoom != null){
            deleteRecord();
            clearRecord();
        }
    }
    
    public ObservableList<String> getLecSessions(){
        ObservableList<String> lecSessionList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query = "SELECT session FROM sessions WHERE tag = 'Lecture'";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            String session;
            while(rs.next()){
                session = rs.getString("session");
                lecSessionList.add(session);
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
        return lecSessionList;
    }
    
    public ObservableList<String> getTuteSessions(){
        ObservableList<String> tuteSessionList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query = "SELECT session FROM sessions WHERE tag = 'Tutorial'";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            String session;
            while(rs.next()){
                session = rs.getString("session");
                tuteSessionList.add(session);
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
        return tuteSessionList;
    }
    
    public ObservableList<String> getRoomList(){
        ObservableList<String> roomList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query = "SELECT room FROM locations WHERE type = 'Lecture Hall'";
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
        String nextgeneratedId = nextId.generateId("consecutive_session_rooms", "CR");
        try{   
            // create a prepared statement
            String query = "insert into consecutive_session_rooms(`id`,`session1`,`session2`,`room`) values (?, ?, ?, ?)"; 
            ps = conn.prepareStatement(query); 
            // binding values
            ps.setString(1, nextgeneratedId); 
            ps.setString(2, session1); 
            ps.setString(3, session2);
            ps.setString(4, room); 
 
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
        showConsSessionRoomList();
    }  
    
    public ObservableList<ConsecutiveSessionRoom> getConsSessionRoomList(){
        ObservableList<ConsecutiveSessionRoom> consSessionRoomList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query = "SELECT * FROM consecutive_session_rooms";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            ConsecutiveSessionRoom consSessionRoom;
            while(rs.next()){
                consSessionRoom = new ConsecutiveSessionRoom(rs.getString("id"), rs.getString("session1"), rs.getString("session2"), rs.getString("room"));
                consSessionRoomList.add(consSessionRoom);
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
        return consSessionRoomList;
    }
    
    public void showConsSessionRoomList(){
        ObservableList<ConsecutiveSessionRoom> list = getConsSessionRoomList();
       
        colId.setCellValueFactory(new PropertyValueFactory<ConsecutiveSessionRoom, String>("id"));
        colSession1.setCellValueFactory(new PropertyValueFactory<ConsecutiveSessionRoom, String>("session1"));
        colSession2.setCellValueFactory(new PropertyValueFactory<ConsecutiveSessionRoom, String>("session2"));
        colRoom.setCellValueFactory(new PropertyValueFactory<ConsecutiveSessionRoom, String>("room"));
             
        tvConsecSessions.setItems(list);
    }
    
    public void deleteRecord(){
        conn = conUtil.getConnection();
        try{
            // create a prepared statement
            String query = "delete from consecutive_session_rooms where id=?"; 
            ps = conn.prepareStatement(query); 
            // binding values
            ps.setString(1, id); 

            // execute the statement
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
        showConsSessionRoomList();
    }
    
    private void clearRecord(){
        cbSession1.setValue("Lecture Session");
        cbSession2.setValue("Tutorial Session");
        cbRoom.setValue("Select Room");
   
    }
}
