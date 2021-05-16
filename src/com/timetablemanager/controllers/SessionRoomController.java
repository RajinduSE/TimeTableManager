/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timetablemanager.controllers;

import com.timetablemanager.models.SessionRoom;
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
public class SessionRoomController implements Initializable {

    @FXML
    private TableView<SessionRoom> tvSessioRoom;
    @FXML
    private TableColumn<SessionRoom, String> colId;
    @FXML
    private TableColumn<SessionRoom, String> colSession;
    @FXML
    private TableColumn<SessionRoom, String> colRoom;
    @FXML
    private ComboBox<String> cbSession;
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
    private String session;
    private String room;
    private String id;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showSessionRoom();
        cbSession.setItems(getSessions());
        cbRoom.setItems(getRoomList());
        
    }    

    @FXML
    private void handleMouseSessionRoom(MouseEvent event) {
        SessionRoom sessionRoom = tvSessioRoom.getSelectionModel().getSelectedItem();
        id = sessionRoom.getId();
        cbSession.setValue("Select Session");
        cbRoom.setValue("Select Room");
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
    private void handleButton(ActionEvent event) {
        SessionRoom sessionRoom = tvSessioRoom.getSelectionModel().getSelectedItem();
        if(event.getSource() == btnSave){
            insertRecord();
        }else if(event.getSource() == btnDelete && sessionRoom != null){
            deleteRecord();
            clearRecord();
        }
    }
    
    public ObservableList<String> getSessions(){
        ObservableList<String> sessionList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query = "SELECT session FROM sessions";
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
        String nextgeneratedId = nextId.generateId("session_rooms", "SR");
        try{   
            // create a prepared statement
            String query = "insert into session_rooms(`id`,`session`,`room`) values (?, ?, ?)"; 
            ps = conn.prepareStatement(query); 
            // binding values
            ps.setString(1, nextgeneratedId); 
            ps.setString(2, session); 
            ps.setString(3, room); 
 
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
        showSessionRoom();
    }  
    
    public ObservableList<SessionRoom> getSessionRoomList(){
        ObservableList<SessionRoom> sessionRoomList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query = "SELECT * FROM session_rooms";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            SessionRoom sessionRoom;
            while(rs.next()){
                sessionRoom = new SessionRoom(rs.getString("id"), rs.getString("session"), rs.getString("room"));
                sessionRoomList.add(sessionRoom);
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
        return sessionRoomList;
    }
    
    public void showSessionRoom(){
        ObservableList<SessionRoom> list = getSessionRoomList();
       
        colId.setCellValueFactory(new PropertyValueFactory<SessionRoom, String>("id"));
        colSession.setCellValueFactory(new PropertyValueFactory<SessionRoom, String>("session"));
        colRoom.setCellValueFactory(new PropertyValueFactory<SessionRoom, String>("room"));
             
        tvSessioRoom.setItems(list);
    }
    
    public void deleteRecord(){
        conn = conUtil.getConnection();
        try{
            // create a prepared statement
            String query = "delete from session_rooms where id=?"; 
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
        showSessionRoom();
    }
    
    private void clearRecord(){
        cbSession.setValue("Select Session");
        cbRoom.setValue("Select Room");
   
    }
}
