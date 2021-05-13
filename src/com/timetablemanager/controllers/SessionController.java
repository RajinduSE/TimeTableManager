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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Rajindu's PC
 */
public class SessionController implements Initializable {

    @FXML
    private ComboBox<String> cbLecturer1;
    @FXML
    private ComboBox<String> cbLecturer2;
    @FXML
    private ComboBox<String> cbTag;
    @FXML
    private ComboBox<String> cbSubject;
    @FXML
    private ComboBox<String> cbSubCode;
    @FXML
    private ComboBox<String> cbGroup;
    @FXML
    private TextField tfCount;
    @FXML
    private TextField tfDuration;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnGenerate;
    @FXML
    private Button btnSave;
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
    private Label lbSession;
    
    private ConnectionUtil conUtil = new ConnectionUtil();
    private Connection conn;
    private Statement st;
    private PreparedStatement ps;
    private ResultSet rs;
    
    private String lecturer1 = "";
    private String lecturer2 = "";
    private String tag;
    private String subject;
    private String id;    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showSessions();
        cbLecturer1.setItems(getLecturerList());
        cbLecturer2.setItems(getLecturerList());
        cbTag.setItems(getTagList());
        cbSubject.setItems(getSubjectList());
        
    }    

    @FXML
    private void changeLecturer1(ActionEvent event) {
        lecturer1 = cbLecturer1.getValue();
    }

    @FXML
    private void changeLecturer2(ActionEvent event) {
        lecturer2 = cbLecturer2.getValue();
    }

    @FXML
    private void changeTag(ActionEvent event) {
        tag = cbTag.getValue();
        cbGroup.setItems(getGroupList(tag));
        cbGroup.setDisable(false);
    }

    @FXML
    private void changeSubject(ActionEvent event) {
        subject = cbSubject.getValue();
        cbSubCode.setValue(getSubjectCode(subject));
        cbSubCode.setDisable(false);
        
    }

    @FXML
    private void changeGroup(ActionEvent event) {
    }

    @FXML
    private void buttonHandler(ActionEvent event) {
        if(event.getSource() == btnSave){
            insertRecord();
        }else if((event.getSource() == btnUpdate)){
            updateRecord();
        }else if(event.getSource() == btnDelete){
            deleteRecord();
            clearRecord();
        }else{
            clearRecord();
        }
    }

    @FXML
    private void generateSession(ActionEvent event) {
        lbSession.setText(generateSession());
    }

    @FXML
    private void hadleMouseAction(MouseEvent event) {
        Session session = tvSessions.getSelectionModel().getSelectedItem();
        id = session.getId();
        cbLecturer1.setValue(session.getLecturer1());
        cbLecturer2.setValue(session.getLecturer2());
        cbTag.setValue(session.getTag());
        cbSubject.setValue(session.getSubject());
        cbSubCode.setValue(session.getCode());
        cbGroup.setValue(session.getGroup());
        tfCount.setText(String.valueOf(session.getCount()));
        tfDuration.setText(String.valueOf(session.getDuration()));
    }
    
    public String generateSession(){
        String session;
        if(lecturer2.isEmpty()){
            session = lecturer1 + "-" + cbSubCode.getValue() + "-" + subject + "-" + tag + "-" + cbGroup.getValue() + "-" + tfCount.getText() + "-" + tfDuration.getText();
        }else{
            session = lecturer1 + "-" + lecturer2 + "-" + cbSubCode.getValue() + "-" + subject + "-" + tag + "-" + cbGroup.getValue() + "-" + tfCount.getText() + "-" + tfDuration.getText();
        }
        return session;        
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
    
    public String getSubjectCode(String subjectName){
        String code = "";
        conn = conUtil.getConnection();
        String query = "SELECT code FROM subjects WHERE subject = " + "'" + subjectName + "'";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            if(rs.next()){
                code = rs.getString("code");
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
        return code;
    }
    
    public ObservableList<String> getGroupList(String tag){
        ObservableList<String> groupList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query;
        if(tag.equals("Lecture") || tag.equals("Tutorial")){
            query = "SELECT groupId FROM studentgroups";
        }else{
            query = "SELECT subgroupId FROM studentgroups";
        }
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            String groups;
            while(rs.next()){
                if(tag.equals("Lecture") || tag.equals("Tutorial")){
                    groups = rs.getString("groupId");
                }else{
                    groups = rs.getString("subgroupId");
                }
                
                groupList.add(groups);
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
    
    public void insertRecord(){
        conn = conUtil.getConnection(); 
        IDGenerator nextId = new IDGenerator();
        String nextgeneratedId = nextId.generateId("sessions", "S");
        try{   
            // create a prepared statement
            String query = "INSERT INTO sessions(`id`, `lecturer1`, `lecturer2`, `tag`, `subject`, `code`, `groupId`, `count`, `duration`, `session`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; 
            ps = conn.prepareStatement(query); 
            // binding values
            ps.setString(1, nextgeneratedId); 
            ps.setString(2, lecturer1); 
            ps.setString(3, lecturer2);
            ps.setString(4, tag);
            ps.setString(5, subject);
            ps.setString(6, cbSubCode.getValue());
            ps.setString(7, cbGroup.getValue());
            ps.setInt(8, Integer.parseInt(tfCount.getText()));
            ps.setInt(9, Integer.parseInt(tfDuration.getText()));
            ps.setString(10, generateSession());
 
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
        showSessions();
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
    
    public void updateRecord(){
        conn = conUtil.getConnection(); 
        try{   
            // create a prepared statement
            String query = "UPDATE sessions SET lecturer1=?,lecturer2=?,tag=?,subject=?,code=?,groupId=?,count=?,duration=?,session=? WHERE id=?"; 
            ps = conn.prepareStatement(query); 
            // binding values
            ps.setString(1, lecturer1); 
            ps.setString(2, lecturer2);
            ps.setString(3, tag);
            ps.setString(4, subject);
            ps.setString(5, cbSubCode.getValue());
            ps.setString(6, cbGroup.getValue());
            ps.setInt(7, Integer.parseInt(tfCount.getText()));
            ps.setInt(8, Integer.parseInt(tfDuration.getText()));
            ps.setString(9, generateSession());
            ps.setString(10, id);
 
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
        showSessions();
    }  
    
    public void deleteRecord(){
        conn = conUtil.getConnection();
        try{
            // create a prepared statement
            String query = "delete from sessions where id=?"; 
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
        showSessions();
    }

    private void clearRecord(){
        cbLecturer1.setValue("Select Lecturer1");
        cbLecturer2.setValue("Select Lecturer1");
        cbTag.setValue("Select Tag");
        cbSubject.setValue("Select Subject");
        cbSubCode.setValue("Select Code");
        cbGroup.setValue("Select Group");
        tfCount.setText(String.valueOf(""));
        tfDuration.setText(String.valueOf(""));
    }
}
