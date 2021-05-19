/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timetablemanager.controllers;
import com.timetablemanager.models.NonOverlap;
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
public class NonoverlapController implements Initializable {

    @FXML
    private Button btnAddSession;
    @FXML
    private TableView<NonOverlap> tvNonoverlap;
    @FXML
    private TableColumn<NonOverlap, String> colId;
    @FXML
    private TableColumn<NonOverlap, String> colN1;
    @FXML
    private TableColumn<NonOverlap, String> colN2;
    @FXML
    private Button btnDelete;
    @FXML
    private ComboBox<String> cbNs2;
    @FXML
    private ComboBox<String> cbNs1;

    
    private ConnectionUtil conUtil = new ConnectionUtil();
    private Connection conn;
    private Statement st;
    private PreparedStatement ps;
    private ResultSet rs;
    private String id;
    private String session1;
    private String session2;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showNonOverlap();
        cbNs1.setItems(getSession1());
        cbNs2.setItems(getSession2());
    }    

    @FXML
    private void handleButton(ActionEvent event) {
        NonOverlap nonoverlap = tvNonoverlap.getSelectionModel().getSelectedItem();
        if(event.getSource() == btnAddSession){
            insertRecord();
        }else if(event.getSource() == btnDelete && nonoverlap != null){
            deleteRecord();
        }
    }

    @FXML
    private void handleMouseSession(MouseEvent event) {
        NonOverlap nonoverlap = tvNonoverlap.getSelectionModel().getSelectedItem();
        id = nonoverlap.getId();
        cbNs1.setValue("Select Session1");
        cbNs2.setValue("Select Session2");
    }

    private void handleButton(MouseEvent event) {
                NonOverlap nonoverlap = tvNonoverlap.getSelectionModel().getSelectedItem();
        if(event.getSource() == btnAddSession){
            insertRecord();
        }else if(event.getSource() == btnDelete && nonoverlap != null){
            deleteRecord();
        }
    }

    @FXML
    private void changeSession(ActionEvent event) {
        session1 = cbNs1.getValue();
        session2 = cbNs2.getValue();
    }

    private void showNonOverlap() {
                ObservableList<NonOverlap> list = getNonOverlapList();
        colId.setCellValueFactory(new PropertyValueFactory<NonOverlap, String>("id"));
        colN1.setCellValueFactory(new PropertyValueFactory<NonOverlap, String>("session1"));
        colN2.setCellValueFactory(new PropertyValueFactory<NonOverlap, String>("session2"));
             
        tvNonoverlap.setItems(list);
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
        String nextgeneratedId = nextId.generateId("nonoverlaps", "NO");
        try{   
            // create a prepared statement
            String query = "insert into nonoverlaps(`id`,`session1`,`session2`) values (?, ?, ?)"; 
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
        showNonOverlap();
    }

    private void deleteRecord() {
      conn = conUtil.getConnection();
        try{
            // create a prepared statement
            String query = "delete from nonoverlaps where id=?"; 
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
        showNonOverlap();
    }

    private ObservableList<NonOverlap> getNonOverlapList() {
                        ObservableList<NonOverlap> NonOverlapList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query = "SELECT * FROM nonoverlaps";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            NonOverlap nonoverlap;
            while(rs.next()){
                nonoverlap = new NonOverlap(rs.getString("id"), rs.getString("session1"), rs.getString("session2"));
                NonOverlapList.add(nonoverlap);
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
        ObservableList<NonOverlap> getNonOverlapList = FXCollections.observableArrayList();
        return getNonOverlapList;
    }
    
}
