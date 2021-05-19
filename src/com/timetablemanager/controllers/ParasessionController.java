/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timetablemanager.controllers;

import com.timetablemanager.models.Parasession;
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
public class ParasessionController implements Initializable {

    @FXML
    private Button btnAddSession;
    @FXML
    private TableView<Parasession> tvParallel;
    @FXML
    private TableColumn<Parasession, String> colID;
    @FXML
    private TableColumn<Parasession, String> colL1;
    @FXML
    private TableColumn<Parasession, String> colL2;
    @FXML
    private Button btnDelete;
    @FXML
    private ComboBox<String> cbP2;
    @FXML
    private ComboBox<String> cbP1;
    
    
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
        showParasession();
        cbP1.setItems(getSession1());
        cbP2.setItems(getSession2());
    }    




    private void handleButton(MouseEvent event) {
        Parasession parasession = tvParallel.getSelectionModel().getSelectedItem();
        if(event.getSource() == btnAddSession){
            insertRecord();
        }else if(event.getSource() == btnDelete && parasession != null){
            deleteRecord();
        }
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
        String nextgeneratedId = nextId.generateId("parallels", "PS");
        try{   
            // create a prepared statement
            String query = "insert into parallels(`id`,`session1`,`session2`) values (?, ?, ?)"; 
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
        showParasession();
    }

    private void deleteRecord() {
               conn = conUtil.getConnection();
        try{
            // create a prepared statement
            String query = "delete from parallels where id=?"; 
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
        showParasession();

    }


    private void showParasession() {
        ObservableList<Parasession> list = getParasessionList();
        colID.setCellValueFactory(new PropertyValueFactory<Parasession, String>("id"));
        colL1.setCellValueFactory(new PropertyValueFactory<Parasession, String>("session1"));
        colL2.setCellValueFactory(new PropertyValueFactory<Parasession, String>("session2"));
             
        tvParallel.setItems(list);
    }

    private ObservableList<Parasession> getParasessionList() {
                ObservableList<Parasession> ParasessionList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query = "SELECT * FROM parallels";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Parasession parasession;
            while(rs.next()){
                parasession = new Parasession(rs.getString("id"), rs.getString("session1"), rs.getString("session2"));
                ParasessionList.add(parasession);
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
        ObservableList<Parasession> getParasessionList = FXCollections.observableArrayList();
        return getParasessionList;
    }



    @FXML
    private void changeSession(ActionEvent event) {
        session2 = cbP2.getValue();
        session1 = cbP1.getValue();
    }

    @FXML
    private void handleButton(ActionEvent event) {
                Parasession parasession = tvParallel.getSelectionModel().getSelectedItem();
        if(event.getSource() == btnAddSession){
            insertRecord();
        }else if(event.getSource() == btnDelete && parasession != null){
            deleteRecord();
        }
    }

    @FXML
    private void handleMouseSession(MouseEvent event) {
         Parasession parasession = tvParallel.getSelectionModel().getSelectedItem();
        id = parasession.getId();
        cbP1.setValue("Select Session1");
        cbP2.setValue("Select Session2");

    }


    
    
}
