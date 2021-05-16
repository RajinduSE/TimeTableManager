/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timetablemanager.controllers;

import com.timetablemanager.models.LecTuteRoom;
import com.timetablemanager.models.PracRoom;
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
public class TagRoomsController implements Initializable {

    @FXML
    private TableView<LecTuteRoom> tvLecTuteRooms;
    @FXML
    private TableColumn<LecTuteRoom, String> colLecTuteId;
    @FXML
    private TableColumn<LecTuteRoom, String> colLecTute;
    @FXML
    private TableView<PracRoom> tvPracRooms;
    @FXML
    private TableColumn<PracRoom, String> colPracId;
    @FXML
    private TableColumn<PracRoom, String> colPrac;
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
    private String tag;
    private String room;
    private String id;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cbTag.setItems(getTagList());
        showLecTuteRoomList();
        showPracRoomList();
    }    
    
    @FXML
    private void buttonHandler(ActionEvent event) {
        LecTuteRoom lecTuteRoom = tvLecTuteRooms.getSelectionModel().getSelectedItem();
        PracRoom pracRoom = tvPracRooms.getSelectionModel().getSelectedItem();
        if(event.getSource() == btnSave){
            insertRecord();
        }else if(event.getSource() == btnDelete && (lecTuteRoom != null || pracRoom != null)){
            deleteRecord();
            clearRecord();
        }
    }
    

    @FXML
    private void handleMouseActionLecTuteRooms(MouseEvent event) {
        LecTuteRoom lecTuteRoom = tvLecTuteRooms.getSelectionModel().getSelectedItem();
        id = lecTuteRoom.getId();
    }

    @FXML
    private void handleMouseActionPracRooms(MouseEvent event) {
        PracRoom pracRoom = tvPracRooms.getSelectionModel().getSelectedItem();
        id = pracRoom.getId();
    }

    @FXML
    private void changeTag(ActionEvent event) {
        tag = cbTag.getValue();
        if(!tag.equals("Select Tag")){
            cbRoom.setDisable(false);
            cbRoom.setItems(getRoomList());
        }
    }

    @FXML
    private void changeRoom(ActionEvent event) {
        room = cbRoom.getValue();
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
        String query;
        if(tag.equals("Lecture") || tag.equals("Tutorial")){
            query = "SELECT room FROM locations WHERE type = 'Lecture Hall'";
        }else{
            query = "SELECT room FROM locations WHERE type = 'Laboratary'";
        }
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
        String nextgeneratedId;
        String query;
        try{   
            if(tag.equals("Lecture") || tag.equals("Tutorial")){
                nextgeneratedId = nextId.generateId("lec_tute_rooms", "LR");
                query = "insert into lec_tute_rooms(`id`,`room`) values (?, ?)";
            }else{
                nextgeneratedId = nextId.generateId("prac_rooms", "PR");
                query = "insert into prac_rooms(`id`, `room`) values (?, ?)";
            }
            // create a prepared statement
            ps = conn.prepareStatement(query); 
            // binding values
            ps.setString(1, nextgeneratedId); 
            ps.setString(2, room); 
 
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
        showLecTuteRoomList();
        showPracRoomList();
    }
    
    public ObservableList<LecTuteRoom> getLecTuteRoomList(){
        ObservableList<LecTuteRoom> lecTuteRoomList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query = "SELECT * FROM lec_tute_rooms";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            LecTuteRoom lecTuteRoom;
            while(rs.next()){
                lecTuteRoom = new LecTuteRoom(rs.getString("id"),rs.getString("room"));
                lecTuteRoomList.add(lecTuteRoom);
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
        return lecTuteRoomList;
    }
    
    public void showLecTuteRoomList(){
        ObservableList<LecTuteRoom> list = getLecTuteRoomList();
       
        colLecTuteId.setCellValueFactory(new PropertyValueFactory<LecTuteRoom, String>("id"));
        colLecTute.setCellValueFactory(new PropertyValueFactory<LecTuteRoom, String>("room"));
             
        tvLecTuteRooms.setItems(list);
    }
    
    public ObservableList<PracRoom> getPracRoomList(){
        ObservableList<PracRoom> pracRoomList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query = "SELECT * FROM prac_rooms";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            PracRoom pracRoom;
            while(rs.next()){
                pracRoom = new PracRoom(rs.getString("id"),rs.getString("room"));
                pracRoomList.add(pracRoom);
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
        return pracRoomList;
    }
    
    public void showPracRoomList(){
        ObservableList<PracRoom> list = getPracRoomList();
       
        colPracId.setCellValueFactory(new PropertyValueFactory<PracRoom, String>("id"));
        colPrac.setCellValueFactory(new PropertyValueFactory<PracRoom, String>("room"));
             
        tvPracRooms.setItems(list);
    }
    
    public void deleteRecord(){
        conn = conUtil.getConnection();
        String query;
        try{
            // create a prepared statement
            if(id.substring(0, 2).equals("LR")){
                query = "delete from lec_tute_rooms where id=?";
            }else{
                query = "delete from prac_rooms where id=?";
            }
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
        showLecTuteRoomList();
        showPracRoomList();
    }

    private void clearRecord(){
        cbTag.setValue("Select Tag");
        cbRoom.setValue("Select Room");
   
    }
}
