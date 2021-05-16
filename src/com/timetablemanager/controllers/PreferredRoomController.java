/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timetablemanager.controllers;

import com.timetablemanager.models.PreferredRoom;
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
public class PreferredRoomController implements Initializable {

    @FXML
    private TableView<PreferredRoom> tvPrefferedRoom;
    @FXML
    private TableColumn<PreferredRoom, String> colId;
    @FXML
    private TableColumn<PreferredRoom, String> colSubject;
    @FXML
    private TableColumn<PreferredRoom, String> colTag;
    @FXML
    private TableColumn<PreferredRoom, String> colRoom;
    @FXML
    private ComboBox<String> cbSubject;
    @FXML
    private ComboBox<String> cbTag;
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
    
    private String subject;
    private String tag;
    private String room;
    private String id;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showPreferredRoom();
        cbSubject.setItems(getSubjectList());
        cbTag.setItems(getTagList());
        cbRoom.setItems(getRoomList());
    }    

    @FXML
    private void handleMouseAction(MouseEvent event) {
        PreferredRoom preferredRoom = tvPrefferedRoom.getSelectionModel().getSelectedItem();
        id = preferredRoom.getId();
        cbSubject.setValue("Select Subject");
        cbTag.setValue("Select Tag");
        cbRoom.setValue("Select Room");
    }

    @FXML
    private void changeSubject(ActionEvent event) {
        subject = cbSubject.getValue();
        
    }

    @FXML
    private void changeTag(ActionEvent event) {
        tag = cbTag.getValue();
    }

    @FXML
    private void changeRoom(ActionEvent event) {
        room = cbRoom.getValue();
    }

    @FXML
    private void buttonHandler(ActionEvent event) {
        PreferredRoom preferredRoom = tvPrefferedRoom.getSelectionModel().getSelectedItem();
        if(event.getSource() == btnSave){
            insertRecord();
        }else if(event.getSource() == btnDelete && preferredRoom != null){
            deleteRecord();
            clearRecord();
        }
    }
    
    public ObservableList<String> getSubjectList(){
        ObservableList<String> subjectList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query = "SELECT subject FROM subjects";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            String subject;
            while(rs.next()){
                subject = rs.getString("subject");
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
    
    public ObservableList<String> getTagList(){
        ObservableList<String> tagList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query = "SELECT rTag FROM tags";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            String tag;
            while(rs.next()){
                tag = rs.getString("rTag");
                tagList.add(tag);
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
        return tagList;
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
        String nextgeneratedId = nextId.generateId("preferred_rooms", "PR");
        try{   
            // create a prepared statement
            String query = "insert into preferred_rooms(`id`,`subject`,`tag`,`room`) values (?, ?, ?, ?)"; 
            ps = conn.prepareStatement(query); 
            // binding values
            ps.setString(1, nextgeneratedId); 
            ps.setString(2, subject);
            ps.setString(3, tag);
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
        showPreferredRoom();
    }  
    
    public ObservableList<PreferredRoom> getPreferredRoomList(){
        ObservableList<PreferredRoom> preferredRoomList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query = "SELECT * FROM preferred_rooms";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            PreferredRoom preferredRoom;
            while(rs.next()){
                preferredRoom = new PreferredRoom(rs.getString("id"), rs.getString("subject"), rs.getString("tag"), rs.getString("room"));
                preferredRoomList.add(preferredRoom);
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
        return preferredRoomList;
    }
    
    public void showPreferredRoom(){
        ObservableList<PreferredRoom> list = getPreferredRoomList();
       
        colId.setCellValueFactory(new PropertyValueFactory<PreferredRoom, String>("id"));
        colSubject.setCellValueFactory(new PropertyValueFactory<PreferredRoom, String>("subject"));
        colTag.setCellValueFactory(new PropertyValueFactory<PreferredRoom, String>("tag"));
        colRoom.setCellValueFactory(new PropertyValueFactory<PreferredRoom, String>("room"));
             
        tvPrefferedRoom.setItems(list);
    }
    
    public void deleteRecord(){
        conn = conUtil.getConnection();
        try{
            // create a prepared statement
            String query = "delete from preferred_rooms where id=?"; 
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
        showPreferredRoom();
    }
    
    private void clearRecord(){
        cbSubject.setValue("Select Subject");
        cbTag.setValue("Select Tag");
        cbRoom.setValue("Select Room");
   
    }
}
