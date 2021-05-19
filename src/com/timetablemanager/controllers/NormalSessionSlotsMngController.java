/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timetablemanager.controllers;

import com.timetablemanager.models.Session;
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
public class NormalSessionSlotsMngController implements Initializable {

    @FXML
    private ComboBox<String> cbDay;
    @FXML
    private TableView<Session> tvSessions;
    @FXML
    private TableColumn<Session, String> colId;
    @FXML
    private TableColumn<Session, String> colLecturer1;
    @FXML
    private TableColumn<Session, String> colLecturer2;
    @FXML
    private TableColumn<Session, String> colTag;
    @FXML
    private TableColumn<Session, String> colSubject;
    @FXML
    private TableColumn<Session, String> colSubCode;
    @FXML
    private TableColumn<Session, String> colGroup;
    @FXML
    private TableColumn<Session, Integer> colCount;
    @FXML
    private TableColumn<Session, Integer> colDuration;
    @FXML
    private ComboBox<String> cbSession;
    @FXML
    private ComboBox<String> cbSlot;
    @FXML
    private Button btnSave;
    @FXML
    private ComboBox<String> cbRoom;
    
    private ConnectionUtil conUtil = new ConnectionUtil();
    private Connection conn;
    private Statement st;
    private PreparedStatement ps;
    private ResultSet rs;
    
    private String lecturer1;
    private String tag;
    private String subject;
    private String subjectCode;
    private String group;
    private String id;   
    
    ObservableList<String> days = FXCollections.observableArrayList("Monday","Tuesday","Wednesday","Thursday","Friday");
    ObservableList<String> slots = FXCollections.observableArrayList("8.30","9.30","10.30","11.30","12.30","13.30","14.30","15.30","16.30","17.30");
    
    private String day;
    private String slot;
    private String room;
    private String sess;
    private Session session;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showSessions();
        cbDay.setItems(days);
        cbSlot.setItems(slots);
    }    


    @FXML
    private void changeDay(ActionEvent event) {
        day = cbDay.getValue();
    }


    @FXML
    private void buttonHandler(ActionEvent event) {
        if(event.getSource() == btnSave){
            insertRecord();
        }
    }


    @FXML
    private void hadleMouseAction(MouseEvent event) {
        session = tvSessions.getSelectionModel().getSelectedItem();
        id = session.getId();
        cbRoom.setValue("Select Room");
        sess = getSession(id);
        cbSession.setValue(sess);
        lecturer1 = session.getLecturer1();
        subjectCode = session.getCode();
        subject = session.getSubject();
        tag = session.getTag();
        group = session.getGroup();
        
        room = checkSuitableRoomForSubject(subjectCode);
        
        if(room.equals("no")){
            room = checkSuitableRoomForLecturer(lecturer1);
        }
        if(room.equals("no")){
            room = checkSuitableRoomForGroup(group);
        }
        if(room.equals("no")){
            room = checkSuitableRoomForSubGroup(group);
        }
        if(room.equals("no")){
            room = checkSuitableRoomForSession(sess);
        }
        if(room.equals("no")){
            room = checkPreferredRoomForSession(subject,tag);
        }
        if(room.equals("no")){
            cbRoom.setItems(getRoomList());
        }else{
            cbRoom.setValue(room);
        }
        
        
    }

    @FXML
    private void changeSession(ActionEvent event) {
        sess = cbSession.getValue();
    }

    @FXML
    private void changeSlot(ActionEvent event) {
        slot = cbSlot.getValue();
    }

    @FXML
    private void changeRoom(ActionEvent event) {
        room = cbRoom.getValue();
    }
    
    public ObservableList<Session> getSessions(){
        ObservableList<Session> sessionList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query = "SELECT * FROM sessions";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Session session;
            while(rs.next()){
                session = new Session(rs.getString("id"), rs.getString("lecturer1"), rs.getString("lecturer2"), rs.getString("tag"), rs.getString("subject"), rs.getString("code"), rs.getString("groupId"), rs.getInt("count"), rs.getInt("duration"));
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
    
    public void showSessions(){
        ObservableList<Session> list = getSessions();
       
        colId.setCellValueFactory(new PropertyValueFactory<Session, String>("id"));
        colLecturer1.setCellValueFactory(new PropertyValueFactory<Session, String>("lecturer1"));
        colLecturer2.setCellValueFactory(new PropertyValueFactory<Session, String>("lecturer2"));
        colTag.setCellValueFactory(new PropertyValueFactory<Session, String>("tag"));
        colSubject.setCellValueFactory(new PropertyValueFactory<Session, String>("subject"));
        colSubCode.setCellValueFactory(new PropertyValueFactory<Session, String>("code"));
        colGroup.setCellValueFactory(new PropertyValueFactory<Session, String>("group"));
        colCount.setCellValueFactory(new PropertyValueFactory<Session, Integer>("count"));
        colDuration.setCellValueFactory(new PropertyValueFactory<Session, Integer>("duration"));
         
        tvSessions.setItems(list);
    }
    
    public String getSession(String num){
        String session = null;
        conn = conUtil.getConnection();
        String query = "SELECT session FROM sessions WHERE id = " + "'" + num + "'";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            if(rs.next()){
                session = rs.getString("session");
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
        return session;
    }

    private String checkSuitableRoomForSubject(String subjectCode) {
        String room = "no";
        conn = conUtil.getConnection();
        String query = "SELECT room FROM subject_rooms WHERE subject = " + "'" + subjectCode + "'";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            if(rs.next()){
                room = rs.getString("room");
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
        return room;
    }

    private String checkSuitableRoomForLecturer(String lecturer1) {
        String room = "no";
        conn = conUtil.getConnection();
        String query = "SELECT room FROM lecturer_rooms WHERE lecturer = " + "'" + lecturer1 + "'";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            if(rs.next()){
                room = rs.getString("room");
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
        return room;
        
    }

    private String checkSuitableRoomForGroup(String group) {
        String room = "no";
        conn = conUtil.getConnection();
        String query = "SELECT room FROM group_rooms WHERE groupId = " + "'" + group + "'";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            if(rs.next()){
                room = rs.getString("room");
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
        return room;
    }

    private String checkSuitableRoomForSession(String sess) {
        String room = "no";
        conn = conUtil.getConnection();
        String query = "SELECT room FROM session_rooms WHERE session = " + "'" + sess + "'";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            if(rs.next()){
                room = rs.getString("room");
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
        return room;
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

    private String checkSuitableRoomForSubGroup(String group) {
        String room = "no";
        conn = conUtil.getConnection();
        String query = "SELECT room FROM subgroup_rooms WHERE subGroupId = " + "'" + group + "'";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            if(rs.next()){
                room = rs.getString("room");
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
        return room;
    }

    private String checkPreferredRoomForSession(String subject, String tag) {
        String room = "no";
        conn = conUtil.getConnection();
        String query = "SELECT room FROM preferred_rooms WHERE subject = " + "'" + subject + "' AND tag = " + "'" + tag + "'";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            if(rs.next()){
                room = rs.getString("room");
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
        return room;
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
            ps.setString(4, sess);
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
