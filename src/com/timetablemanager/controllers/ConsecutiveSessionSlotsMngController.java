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
public class ConsecutiveSessionSlotsMngController implements Initializable {

    @FXML
    private ComboBox<String> cbSession1;
    @FXML
    private ComboBox<String> cbSession2;
    @FXML
    private ComboBox<String> cbDay;
    @FXML
    private ComboBox<String> cbSlot1;
    @FXML
    private Button btnSession1;
    @FXML
    private ComboBox<String> cbSlot2;
    @FXML
    private Button btnSession2;
    
    private ConnectionUtil conUtil = new ConnectionUtil();
    private Connection conn;
    private Statement st;
    private PreparedStatement ps;
    private ResultSet rs;
    ObservableList<String> days = FXCollections.observableArrayList("Monday","Tuesday","Wednesday","Thursday","Friday");
    ObservableList<String> slots = FXCollections.observableArrayList("8.30","9.30","10.30","11.30","12.30","13.30","14.30","15.30","16.30","17.30");
    @FXML
    private TableView<ConsecutiveSessionRoom> tvConsecutives;
    @FXML
    private TableColumn<ConsecutiveSessionRoom, String> colId;
    @FXML
    private TableColumn<ConsecutiveSessionRoom, String> colSession1;
    @FXML
    private TableColumn<ConsecutiveSessionRoom, String> colSession2;
    @FXML
    private TableColumn<ConsecutiveSessionRoom, String> colRoom;
    
    private String session1;
    private String session2;
    private String room;
    private String id;
    private String day;
    private String slot1;
    private String slot2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showConsSessionRoomList();
        cbDay.setItems(days);
        cbSlot1.setItems(slots);
        cbSlot2.setItems(slots);
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
    private void changeDay(ActionEvent event) {
        day = cbDay.getValue();
    }

    @FXML
    private void changeSlot1(ActionEvent event) {
        slot1 = cbSlot1.getValue();
    }

    @FXML
    private void buttonHandler(ActionEvent event) {
        if(event.getSource() == btnSession1){
            insertSession1();
        }else if(event.getSource() == btnSession2){
            insertSession2();
        }
    }

    @FXML
    private void changeSlot2(ActionEvent event) {
        slot2 = cbSlot2.getValue();
    }
    
    public ObservableList<String> getSessions1(){
        ObservableList<String> sessionList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();

        String query = "SELECT session1 FROM consecutive_session_rooms";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            String session;
            while(rs.next()){
                session = rs.getString("session1");
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

    @FXML
    private void handleMouseAction(MouseEvent event) {
        ConsecutiveSessionRoom conSessionRoom = tvConsecutives.getSelectionModel().getSelectedItem();
        id = conSessionRoom.getId();
        cbSession1.setValue(conSessionRoom.getSession1());
        cbSession2.setValue(conSessionRoom.getSession2());
        room = conSessionRoom.getRoom();
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
             
        tvConsecutives.setItems(list);
    }
    
    public void insertSession1(){
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
            ps.setString(3, slot1);
            ps.setString(4, session1);
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
    
    public void insertSession2(){
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
            ps.setString(3, slot2);
            ps.setString(4, session2);
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
