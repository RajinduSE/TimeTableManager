/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timetablemanager.controllers;

import com.timetablemanager.models.LecturerRoom;
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
public class LecturerRoomController implements Initializable {

    @FXML
    private TableView<LecturerRoom> tvLecturerRoom;
    @FXML
    private TableColumn<LecturerRoom, String> colId;
    @FXML
    private TableColumn<LecturerRoom, String> colLecturer;
    @FXML
    private TableColumn<LecturerRoom, String> colRoom;
    @FXML
    private ComboBox<String> cbLecturer;
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
    private String lecturer;
    private String room;
    private String id;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showLecturerRoom();
        cbLecturer.setItems(getLecturerList());
        cbRoom.setItems(getRoomList());
    }    

    @FXML
    private void handleMouseAction(MouseEvent event) {
        LecturerRoom lecturerRoom = tvLecturerRoom.getSelectionModel().getSelectedItem();
        id = lecturerRoom.getId();
        cbLecturer.setValue(lecturerRoom.getLecturer());
        cbRoom.setValue(lecturerRoom.getRoom());
    }

    @FXML
    private void changeLecturer(ActionEvent event) {
        lecturer = cbLecturer.getValue();
    }

    @FXML
    private void changeRoom(ActionEvent event) {
        room = cbRoom.getValue();
    }

    @FXML
    private void buttonHandler(ActionEvent event) {
        LecturerRoom lecturerRoom = tvLecturerRoom.getSelectionModel().getSelectedItem();
        if(event.getSource() == btnSave){
            insertRecord();
        }else if(event.getSource() == btnDelete && lecturerRoom != null){
            deleteRecord();
            clearRecord();
        }
    }
    
    public ObservableList<String> getLecturerList(){
        ObservableList<String> lecturerList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query = "SELECT name FROM lecturers";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            String lecturer;
            while(rs.next()){
                lecturer = rs.getString("name");
                lecturerList.add(lecturer);
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
        return lecturerList;
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
        String nextgeneratedId = nextId.generateId("lecturer_rooms", "LR");
        try{   
            // create a prepared statement
            String query = "insert into lecturer_rooms(`id`,`lecturer`,`room`) values (?, ?, ?)"; 
            ps = conn.prepareStatement(query); 
            // binding values
            ps.setString(1, nextgeneratedId); 
            ps.setString(2, lecturer); 
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
        showLecturerRoom();
    }  
    
    public ObservableList<LecturerRoom> getLecturerRoomList(){
        ObservableList<LecturerRoom> lecturerRoomList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query = "SELECT * FROM lecturer_rooms";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            LecturerRoom lecturerRoom;
            while(rs.next()){
                lecturerRoom = new LecturerRoom(rs.getString("id"), rs.getString("lecturer"), rs.getString("room"));
                lecturerRoomList.add(lecturerRoom);
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
        return lecturerRoomList;
    }
    
    public void showLecturerRoom(){
        ObservableList<LecturerRoom> list = getLecturerRoomList();
       
        colId.setCellValueFactory(new PropertyValueFactory<LecturerRoom, String>("id"));
        colLecturer.setCellValueFactory(new PropertyValueFactory<LecturerRoom, String>("lecturer"));
        colRoom.setCellValueFactory(new PropertyValueFactory<LecturerRoom, String>("room"));
             
        tvLecturerRoom.setItems(list);
    }
    
    public void deleteRecord(){
        conn = conUtil.getConnection();
        try{
            // create a prepared statement
            String query = "delete from lecturer_rooms where id=?"; 
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
        showLecturerRoom();
    }
    
    private void clearRecord(){
        cbLecturer.setValue("Select Lecturer");
        cbRoom.setValue("Select Room");
   
    }
}
