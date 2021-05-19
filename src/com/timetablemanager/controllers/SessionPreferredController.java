/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timetablemanager.controllers;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import com.timetablemanager.models.SessionPreferred;
import com.timetablemanager.utils.ConnectionUtil;
import com.timetablemanager.utils.IDGenerator;
import java.net.URL;
import java.sql.Connection;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Shelani Wijesekera
 */
public class SessionPreferredController implements Initializable {

     @FXML
    private TableView<SessionPreferred> tPSession;
    @FXML
    private TableColumn<SessionPreferred, String> colId;
    @FXML
    private TableColumn<SessionPreferred, String> colLecturer;
    @FXML
    private TableColumn<SessionPreferred, String> colSession;
    @FXML
    private TableColumn<SessionPreferred, String> colDay;
    @FXML
    private TableColumn<SessionPreferred, Integer> colTime;
    
    @FXML
    private ComboBox<String> cbLecturer;
    @FXML
    private ComboBox<String> cbSession;
    @FXML
    private ComboBox<String> cbDay;
    
    @FXML
    private TextField tfTime;

    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnSave;
    
    private ConnectionUtil conUtil = new ConnectionUtil();
    private Connection conn;
    private Statement st;
    private ResultSet rs;
    private String day;
    private String lecturer;
    private String session;
    private String SessionPreferredId;
    
     
     
     ObservableList<String> DayList = FXCollections.observableArrayList("Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday");

     
      @Override
    public void initialize(URL url, ResourceBundle rb) {
        showSessionPreferred();
        
        cbLecturer.setItems(getLecturerList());
        cbSession.setItems(getSessionList());
        cbDay.setItems(DayList);
    }    
    
    @FXML
    void handleAction(ActionEvent event) {
        SessionPreferred SessionPreferred = tPSession.getSelectionModel().getSelectedItem();
        if(event.getSource() == btnSave){
            insertRecord();
        }else if(event.getSource() == btnUpdate && SessionPreferred != null){
            updateRecord();
        }else if(event.getSource() == btnDelete && SessionPreferred != null){
            deleteRecord();
            clearRecord();
        }else if(event.getSource() == btnClear){
            clearRecord();
        }
    }
    
     @FXML
    private void handleMouseAction(MouseEvent event) {
        SessionPreferred SessionPreferred = tPSession.getSelectionModel().getSelectedItem();
        SessionPreferredId = SessionPreferred.getId();
        cbLecturer.setValue(SessionPreferred.getLecturer());
        tfTime.setText(String.valueOf(SessionPreferred.getTime()));
        cbSession.setValue(SessionPreferred.getSession());
        cbDay.setValue(SessionPreferred.getDay());
        
        
    }
  
    @FXML
    void dayChanger(ActionEvent event) {
        day = cbDay.getValue();
    }

    @FXML
    void lecturerChanger(ActionEvent event) {
        lecturer = cbLecturer.getValue();
    }

    @FXML
    void sessionChanger(ActionEvent event) {
        session = cbSession.getValue();
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
    
    public ObservableList<String> getSessionList(){
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
    
     public ObservableList<SessionPreferred> getSessionPreferredList(){
        ObservableList<SessionPreferred> SessionPreferredList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query = "SELECT * FROM session_preferred";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            SessionPreferred SessionPreferred;
            while(rs.next()){
                SessionPreferred = new SessionPreferred(rs.getString("id"), rs.getString("lecturer"),rs.getInt("time"), rs.getString("session"), rs.getString("day"));
                SessionPreferredList.add(SessionPreferred);
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
        return SessionPreferredList;
    }
     
     public void showSessionPreferred(){
        ObservableList<SessionPreferred> list = getSessionPreferredList();
       
        colId.setCellValueFactory(new PropertyValueFactory<SessionPreferred, String>("id"));
        colLecturer.setCellValueFactory(new PropertyValueFactory<SessionPreferred, String>("lecturer"));
        colTime.setCellValueFactory(new PropertyValueFactory<SessionPreferred, Integer>("time"));
        colSession.setCellValueFactory(new PropertyValueFactory<SessionPreferred, String>("session"));
        colDay.setCellValueFactory(new PropertyValueFactory<SessionPreferred, String>("day"));
        
        
        tPSession.setItems(list);
       
    }
     
     
     private void insertRecord(){
        IDGenerator nextId = new IDGenerator();
        String nextgeneratedId = nextId.generateId("session_preferred", "PS");
        String query = "INSERT INTO session_preferred VALUES (" + "'" + nextgeneratedId + "'" + "," + "'" + lecturer + "'" + "," + "'" + Integer.parseInt(tfTime.getText()) + "'" + "," + "'" + session 
                     + "'" + "," + "'" + day + "'" + ")";
        executeQuery(query);
        showSessionPreferred();
    }
    
    private void updateRecord(){
        String query = "UPDATE  session_preferred SET lecturer  = '" + lecturer + "', time = '" + tfTime.getText() + "', session = '" + session + "', day = '" + day +"'  WHERE id = " + "'" + SessionPreferredId + "'";
        executeQuery(query);
        showSessionPreferred();
    }    
    
    private void deleteRecord(){
        String query = "DELETE FROM session_preferred WHERE id = " + "'" + SessionPreferredId + "'";
        executeQuery(query);
        showSessionPreferred ();
    }
    
    private void clearRecord(){
        
        cbLecturer.setValue("");
        cbSession.setValue("");
        cbDay.setValue("");
        tfTime.setText(null);
      
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


   
    

