/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timetablemanager.controllers;

import com.timetablemanager.models.Subject;
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
 * @author Rajindu's PC
 */
public class SubjectController implements Initializable {

    @FXML
    private TableView<Subject> tvSubjects;
    @FXML
    private TableColumn<Subject, String> colId;
    @FXML
    private TableColumn<Subject, String> colSubject;
    @FXML
    private TableColumn<Subject, String> colCode;
    @FXML
    private TableColumn<Subject, Integer> colYear;
    @FXML
    private TableColumn<Subject, Integer> colSemester;
    @FXML
    private TableColumn<Subject, Integer> colLectureHrs;
    @FXML
    private TableColumn<Subject, Integer> colTuteHrs;
    @FXML
    private TableColumn<Subject, Integer> colLabHrs;
    @FXML
    private TableColumn<Subject, Integer> colEvalHrs;
    @FXML
    private ComboBox<Integer> cbYear;
    @FXML
    private RadioButton rb1;
    @FXML
    private ToggleGroup rbSemester;
    @FXML
    private RadioButton rb2;
    @FXML
    private TextField tfSubject;
    @FXML
    private TextField tfCode;
    @FXML
    private TextField tfLecHrs;
    @FXML
    private TextField tfTuteHrs;
    @FXML
    private TextField tfLabHrs;
    @FXML
    private TextField tfEvalHrs;
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
    private int semester;
    private int year;
    private String subId;
    
    ObservableList<Integer> yearList = FXCollections.observableArrayList(1,2,3,4);

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showSubjects();
        cbYear.setItems(yearList);
    }    

    @FXML
    private void semesterHandler(ActionEvent event) {
        if(rb1.isSelected()){
            semester = 1;
        }else if(rb2.isSelected()){
            semester = 2;
        }else{
            System.out.println("Not Selected");
        }
    }

    @FXML
    private void handleAction(ActionEvent event) {
        Subject subject = tvSubjects.getSelectionModel().getSelectedItem();
        if(event.getSource() == btnSave){
            insertRecord();
        }else if(event.getSource() == btnUpdate && subject != null){
            updateRecord();
        }else if(event.getSource() == btnDelete && subject != null){
            deleteRecord();
            clearRecord();
        }else if(event.getSource() == btnClear){
            clearRecord();
        }
    }
    
    @FXML
    private void handleMouseAction(MouseEvent event) {
        Subject subject = tvSubjects.getSelectionModel().getSelectedItem();
        subId = subject.getId();
        tfSubject.setText(subject.getSubject());
        tfCode.setText(subject.getCode());
        cbYear.setValue(subject.getYear());
        if(subject.getSemester() == 1){
            rb1.setSelected(true);
        }else if(subject.getSemester() == 2){
            rb2.setSelected(true);
        }
        tfLecHrs.setText(String.valueOf(subject.getLecHrs()));
        tfTuteHrs.setText(String.valueOf(subject.getTuteHrs()));
        tfLabHrs.setText(String.valueOf(subject.getLabHrs()));
        tfEvalHrs.setText(String.valueOf(subject.getEvalHrs()));
    }

    @FXML
    private void yearChanger(ActionEvent event) {
        year = cbYear.getValue();
    }
    
    public ObservableList<Subject> getSubjectList(){
        ObservableList<Subject> subjectList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query = "SELECT * FROM subjects";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Subject subject;
            while(rs.next()){
                subject = new Subject(rs.getString("id"), rs.getString("subject"), rs.getString("code"), rs.getInt("year"),rs.getInt("semester"), rs.getInt("lecHrs"), rs.getInt("tuteHrs"), rs.getInt("labHrs"), rs.getInt("evalHrs"));
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
    
    public void showSubjects(){
        ObservableList<Subject> list = getSubjectList();
       
        colId.setCellValueFactory(new PropertyValueFactory<Subject, String>("id"));
        colSubject.setCellValueFactory(new PropertyValueFactory<Subject, String>("subject"));
        colCode.setCellValueFactory(new PropertyValueFactory<Subject, String>("code"));
        colYear.setCellValueFactory(new PropertyValueFactory<Subject, Integer>("year"));
        colSemester.setCellValueFactory(new PropertyValueFactory<Subject, Integer>("semester"));
        colLectureHrs.setCellValueFactory(new PropertyValueFactory<Subject, Integer>("lecHrs"));
        colTuteHrs.setCellValueFactory(new PropertyValueFactory<Subject, Integer>("tuteHrs"));
        colLabHrs.setCellValueFactory(new PropertyValueFactory<Subject, Integer>("labHrs"));
        colEvalHrs.setCellValueFactory(new PropertyValueFactory<Subject, Integer>("evalHrs"));
        
        tvSubjects.setItems(list);
    }
    
    private void insertRecord(){
        IDGenerator nextId = new IDGenerator();
        String nextgeneratedId = nextId.generateId("subjects", "S");
        String query = "INSERT INTO subjects VALUES (" + "'" + nextgeneratedId + "'" + "," + "'" + tfSubject.getText() + "'" + "," + "'" + tfCode.getText() + "'" + "," + "'" + year 
                     + "'" + "," + "'" + semester + "'" + "," + "'" + Integer.parseInt(tfLecHrs.getText()) + "'" + "," + "'" + Integer.parseInt(tfTuteHrs.getText()) 
                     + "'" + "," + "'" + Integer.parseInt(tfLabHrs.getText()) + "'" + "," + "'" + Integer.parseInt(tfEvalHrs.getText()) + "'" + ")";
        executeQuery(query);
        showSubjects();
    }
    
    private void updateRecord(){
        String query = "UPDATE  subjects SET subject  = '" + tfSubject.getText() + "', code = '" + tfCode.getText() + "', year = " +
                "'" + year + "'" + ", semester = " + "'" + semester +"'"+ ", lecHrs = " + "'" + tfLecHrs.getText() +"'" + ", tuteHrs = " + "'" + tfTuteHrs.getText() +"'" +", labHrs = " + "'" + tfLabHrs.getText() +"'" + ", evalHrs = " + "'" + tfEvalHrs.getText() +"'" +" WHERE id = " + "'" + subId + "'";
        executeQuery(query);
        showSubjects();
    }    
    
    private void deleteRecord(){
        String query = "DELETE FROM subjects WHERE id = " + "'" + subId + "'";
        executeQuery(query);
        showSubjects();
    }
    
    private void clearRecord(){
        tfSubject.setText(null);
        tfCode.setText(null);
        cbYear.setValue(1);
        rb1.setSelected(false);
        rb2.setSelected(false);
        tfLecHrs.setText(null);
        tfTuteHrs.setText(null);
        tfLabHrs.setText(null);
        tfEvalHrs.setText(null);
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
