/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timetablemanager.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import com.timetablemanager.models.SessionNotAvailable;
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
import javafx.scene.control.Alert;
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
public class SessionNotAvailableController implements Initializable {

    @FXML
    private TableView<SessionNotAvailable> tnaSession;
    @FXML
    private TableColumn<SessionNotAvailable, String> colId;
    @FXML
    private TableColumn<SessionNotAvailable, String> colDay;
    @FXML
    private TableColumn<SessionNotAvailable, Integer> colTime;
    @FXML
    private TableColumn<SessionNotAvailable, String> colLecturer;
    @FXML
    private TableColumn<SessionNotAvailable, String> colGroup;
    @FXML
    private TableColumn<SessionNotAvailable, String> colSubGroup;
    @FXML
    private TableColumn<SessionNotAvailable, String> colSession;
    @FXML
    private ComboBox<String> cbLecturer;
    @FXML
    private ComboBox<String> cbSession;
    @FXML
    private ComboBox<String> cbDay;
    @FXML
    private TextField tfTime;
    @FXML
    private ComboBox<String> cbGroup;
    @FXML
    private ComboBox<String> cbSGroup;
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
    private String group;
    private String Sgroup;
     private String SessionNotAvailableId;
    
    
     
     
     ObservableList<String> DayList = FXCollections.observableArrayList("Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday");
    
     
     
    
     @Override
    public void initialize(URL url, ResourceBundle rb) {
       showSessionNotAvailable();
        
        cbLecturer.setItems(getLecturerList());
        cbSession.setItems(getSessionList());
        cbDay.setItems(DayList);
        cbGroup.setItems(getGroupList());
        cbSGroup.setItems(getSubGroupList());
    }   
    
    @FXML
    void handleAction(ActionEvent event) {
        SessionNotAvailable SessionNotAvailable = tnaSession.getSelectionModel().getSelectedItem();
        if(event.getSource() == btnSave){
            insertRecord();
        }else if(event.getSource() == btnUpdate && SessionNotAvailable != null){
            updateRecord();
        }else if(event.getSource() == btnDelete && SessionNotAvailable != null){
            deleteRecord();
            clearRecord();
        }else if(event.getSource() == btnClear){
            clearRecord();
        }
    }
    
    @FXML
    private void handleMouseAction(MouseEvent event) {
        SessionNotAvailable SessionNotAvailable = tnaSession.getSelectionModel().getSelectedItem();
        SessionNotAvailableId = SessionNotAvailable.getId();
        cbDay.setValue(SessionNotAvailable.getDay());
        tfTime.setText(String.valueOf(SessionNotAvailable.getTime()));
        cbLecturer.setValue(SessionNotAvailable.getLecturer());
        cbGroup.setValue(SessionNotAvailable.getGroup());
        cbSGroup.setValue(SessionNotAvailable.getSgroup());
        cbSession.setValue(SessionNotAvailable.getSession());
         
    }

    

    @FXML
    void dayChanger(ActionEvent event) {
        day = cbDay.getValue();
    }

    @FXML
    void groupChanger(ActionEvent event) {
        group = cbGroup.getValue();
    }
    @FXML
    void SgroupChanger(ActionEvent event) {
        Sgroup = cbSGroup.getValue();
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
    public ObservableList<String> getGroupList(){
        ObservableList<String> groupList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query = "SELECT groupId FROM studentgroups";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            String group;
            while(rs.next()){
                group = rs.getString("groupId");
                groupList.add(group);
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
        return groupList;
    }
    
    public ObservableList<String> getSubGroupList(){
        ObservableList<String> SgroupList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query = "SELECT subgroupId FROM studentgroups";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            String subgroup;
            while(rs.next()){
                subgroup = rs.getString("subgroupId");
                SgroupList.add(subgroup);
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
        return SgroupList;
    }
    
    public ObservableList<SessionNotAvailable> getSessionNotAvailableList(){
        ObservableList<SessionNotAvailable> SessionNotAvailableList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query = "SELECT * FROM session_not_available";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            SessionNotAvailable SessionNotAvailable;
            while(rs.next()){
                SessionNotAvailable = new SessionNotAvailable(rs.getString("id"), rs.getString("lecturer"),rs.getString("session"), rs.getString("day"), rs.getInt("time"),rs.getString("group"),rs.getString("sgroup"));
                SessionNotAvailableList.add(SessionNotAvailable);
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
        return SessionNotAvailableList;
    }
    public void showSessionNotAvailable(){
        ObservableList<SessionNotAvailable> list = getSessionNotAvailableList();
       
        colId.setCellValueFactory(new PropertyValueFactory<SessionNotAvailable, String>("id"));
        colLecturer.setCellValueFactory(new PropertyValueFactory<SessionNotAvailable, String>("lecturer"));
        colSession.setCellValueFactory(new PropertyValueFactory<SessionNotAvailable, String>("session"));
        colDay.setCellValueFactory(new PropertyValueFactory<SessionNotAvailable, String>("day"));
        colTime.setCellValueFactory(new PropertyValueFactory<SessionNotAvailable, Integer>("time"));
        colGroup.setCellValueFactory(new PropertyValueFactory<SessionNotAvailable, String>("group"));
        colSubGroup.setCellValueFactory(new PropertyValueFactory<SessionNotAvailable, String>("sgroup"));
        
        tnaSession.setItems(list);
       
    }
    private void insertRecord(){
        
        IDGenerator nextId = new IDGenerator();
        String nextgeneratedId = nextId.generateId("session_not_available", "NAS");
        String query = "INSERT INTO session_not_available VALUES (" + "'" + nextgeneratedId + "'" + "," + "'" + lecturer + "'" + "," + "'" + session + "'" + "," + "'" + day 
                     + "'" + "," + "'" + Integer.parseInt(tfTime.getText()) + "'" + "," + "'" + group + "'" + "," + "'" + Sgroup + "'" + ")";
        executeQuery(query);
        showSessionNotAvailable();
        
        
    }
    
    private void updateRecord(){
        String query = "UPDATE  session_not_available SET lecturer  = '" + lecturer + "', session = '" + session + "', day = '" + day + "', time = '" + tfTime.getText() + "', group = '" + group + "', sgroup = '" + Sgroup + "'"
                + "   WHERE id = " + "'" + SessionNotAvailableId + "'";
        executeQuery(query);
        showSessionNotAvailable();
    }    
    
    private void deleteRecord(){
        String query = "DELETE FROM session_not_available WHERE id = " + "'" + SessionNotAvailableId + "'";
        executeQuery(query);
        showSessionNotAvailable ();
    }
    
    private void clearRecord(){
        
        cbLecturer.setValue("");
        cbSession.setValue("");
        cbDay.setValue("");
        tfTime.setText(null);
        cbGroup.setValue("");
        cbSGroup.setValue("");
      
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
