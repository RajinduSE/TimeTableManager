/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timetablemanager.controllers;

import com.timetablemanager.models.SubjectRoom;
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
public class SubjectRoomController implements Initializable {
    
    
    @FXML
    private TableView<SubjectRoom> tvSubRooms;
    @FXML
    private TableColumn<SubjectRoom, String> colSubRoomId;
    @FXML
    private TableColumn<SubjectRoom, String> colSubject;
    @FXML
    private TableColumn<SubjectRoom, String> colRoom;
    @FXML
    private ComboBox<String> cbSubject;
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
    private String subRoomId;
    private String subject;
    private String room;
       
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showSubjectRoom();
        cbSubject.setItems(getSubjectList());
        cbRoom.setItems(getRoomList());
    } 
    
    @FXML
    private void buttonHandler(ActionEvent event) {
        SubjectRoom subjectRoom = tvSubRooms.getSelectionModel().getSelectedItem();
        if(event.getSource() == btnSave){
            insertRecord();
        }else if(event.getSource() == btnDelete && subjectRoom != null){
            deleteRecord();
            clearRecord();
        }
    }
    
    public ObservableList<String> getSubjectList(){
        ObservableList<String> subjectList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query = "SELECT subject,code FROM subjects";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            String subject;
            while(rs.next()){
                subject = rs.getString("code") + "-" + rs.getString("subject");
                subjectList.add(subject);
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
        return subjectList;
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

    @FXML
    private void subjectChange(ActionEvent event) {
        subject = cbSubject.getValue();
    }

    @FXML
    private void roomChange(ActionEvent event) {
        room = cbRoom.getValue();
    }

    public void insertRecord(){
        conn = conUtil.getConnection(); 
        IDGenerator nextId = new IDGenerator();
        String nextgeneratedId = nextId.generateId("subject_rooms", "SR");
        try{   
            // create a prepared statement
            String query = "insert into subject_rooms(`id`,`subject`,`room`) values (?, ?, ?)"; 
            ps = conn.prepareStatement(query); 
            // binding values
            ps.setString(1, nextgeneratedId); 
            ps.setString(2, subject.substring(0, 6)); 
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
        showSubjectRoom();
    }  
    
    public ObservableList<SubjectRoom> getSubjectRoomList(){
        ObservableList<SubjectRoom> subjectRoomList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query = "SELECT * FROM subject_rooms";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            SubjectRoom subjectRoom;
            while(rs.next()){
                subjectRoom = new SubjectRoom(rs.getString("id"), rs.getString("subject"), rs.getString("room"));
                subjectRoomList.add(subjectRoom);
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
        return subjectRoomList;
    }
    
    public void showSubjectRoom(){
        ObservableList<SubjectRoom> list = getSubjectRoomList();
       
        colSubRoomId.setCellValueFactory(new PropertyValueFactory<SubjectRoom, String>("id"));
        colSubject.setCellValueFactory(new PropertyValueFactory<SubjectRoom, String>("subject"));
        colRoom.setCellValueFactory(new PropertyValueFactory<SubjectRoom, String>("room"));
             
        tvSubRooms.setItems(list);
    }
    
    public void deleteRecord(){
        conn = conUtil.getConnection();
        try{
            // create a prepared statement
            String query = "delete from subject_rooms where id=?"; 
            ps = conn.prepareStatement(query); 
            // binding values
            ps.setString(1, subRoomId); 

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
        showSubjectRoom();
    }

    @FXML
    private void handleMouseAction(MouseEvent event) {
        SubjectRoom subjectRoom = tvSubRooms.getSelectionModel().getSelectedItem();
        subRoomId = subjectRoom.getId();
        cbSubject.setValue(subjectRoom.getSubject());
        cbRoom.setValue(subjectRoom.getRoom());
    }
    
    private void clearRecord(){
        cbSubject.setValue("Select Subject");
        cbRoom.setValue("Select Room");
   
    }
}
