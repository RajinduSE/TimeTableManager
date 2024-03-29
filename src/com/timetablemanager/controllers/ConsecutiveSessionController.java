/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timetablemanager.controllers;
import com.timetablemanager.models.ConsecutiveSession;
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
 * @author Asus
 */
public class ConsecutiveSessionController implements Initializable {

    @FXML
    private Button btnAddSession;
    @FXML
    private Button btnDelete;
    @FXML
    private ComboBox<String> cbC1;
    @FXML
    private ComboBox<String> cbC2;
    @FXML
    private TableView<ConsecutiveSession> tvConsec;
    @FXML
    private TableColumn<ConsecutiveSession, String> colId;
    @FXML
    private TableColumn<ConsecutiveSession, String> colS1;
    @FXML
    private TableColumn<ConsecutiveSession, String> colS2;

    
   private ConnectionUtil conUtil = new ConnectionUtil();
    private Connection conn;
    private Statement st;
    private PreparedStatement ps;
    private ResultSet rs;
    private String id;
    private String session1;
    private String session2;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showConsecutiveSession();
        cbC1.setItems(getSession1());
        cbC2.setItems(getSession2());
    }    

    @FXML
    private void handleButton(ActionEvent event) {
         ConsecutiveSession consecutivesession = tvConsec.getSelectionModel().getSelectedItem();
        if(event.getSource() == btnAddSession){
            insertRecord();
        }else if(event.getSource() == btnDelete && consecutivesession != null){
            deleteRecord();
        }
    }

    @FXML
    private void changeSession(ActionEvent event) {
        cbC1.setItems(getSession1());
        cbC2.setItems(getSession2());
    } 

    @FXML
    private void handleMouseSession(MouseEvent event) {
        ConsecutiveSession consecutivesession = tvConsec.getSelectionModel().getSelectedItem();
        id = consecutivesession.getId();
        cbC1.setValue("Select Session1");
        cbC2.setValue("Select Session2");
    }

    private ObservableList<String> getSession1() {
                  ObservableList<String> sessionList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query = "SELECT session FROM sessions";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            String session1;
            while(rs.next()){
                session1 = rs.getString("session");
                sessionList.add(session1);
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

    private ObservableList<String> getSession2() {
        ObservableList<String> sessionList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query = "SELECT session FROM sessions";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            String session;
            while(rs.next()){
                session2 = rs.getString("session");
                sessionList.add(session2);
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

    private void insertRecord() {
        conn = conUtil.getConnection(); 
        IDGenerator nextId = new IDGenerator();
        String nextgeneratedId = nextId.generateId("consecutives", "CS");
        try{   
            // create a prepared statement
            String query = "insert into consecutives(`id`,`session1`,`session2`) values (?, ?, ?)"; 
            ps = conn.prepareStatement(query); 
            // binding values
            ps.setString(1, nextgeneratedId); 
            ps.setString(2, session1); 
            ps.setString(3, session2); 
 
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
        showConsecutiveSession();
    }

    private void deleteRecord() {
        conn = conUtil.getConnection();
        try{
            // create a prepared statement
            String query = "delete from consecutives where id=?"; 
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
        showConsecutiveSession();
    }

    private void showConsecutiveSession() {
                ObservableList<ConsecutiveSession> list = getConsecutiveSessionList();
        colId.setCellValueFactory(new PropertyValueFactory<ConsecutiveSession, String>("id"));
        colS1.setCellValueFactory(new PropertyValueFactory<ConsecutiveSession, String>("session1"));
        colS2.setCellValueFactory(new PropertyValueFactory<ConsecutiveSession, String>("session2"));
             
        tvConsec.setItems(list);
    }

    private ObservableList<ConsecutiveSession> getConsecutiveSessionList() {
        ObservableList<ConsecutiveSession> ConsecutiveSessionList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query = "SELECT * FROM consecutives";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            ConsecutiveSession consecutivesession;
            while(rs.next()){
                consecutivesession = new ConsecutiveSession(rs.getString("id"), rs.getString("session1"), rs.getString("session2"));
                ConsecutiveSessionList.add(consecutivesession);
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
        ObservableList<ConsecutiveSession> getConsecutiveSessionList = FXCollections.observableArrayList();
        return ConsecutiveSessionList;
    }
    
   
   
    
}
