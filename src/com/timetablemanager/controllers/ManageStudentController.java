/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timetablemanager.controllers;

import com.timetablemanager.models.StudentGroup;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class ManageStudentController implements Initializable {

    @FXML
    private TableView<StudentGroup> tvStudentGroups;
    @FXML
    private TableColumn<StudentGroup, String> colId;
    @FXML
    private TableColumn<StudentGroup, String> colYear;
    @FXML
    private TableColumn<StudentGroup, String> colProg;
    @FXML
    private TableColumn<StudentGroup, String> colGoupNo;
    @FXML
    private TableColumn<StudentGroup, String> colGroupId;
    @FXML
    private TableColumn<StudentGroup, String> colSubGroupNo;
    @FXML
    private TableColumn<StudentGroup, String> colSubGroupId;
    @FXML
    private TextField tfYear;
    @FXML
    private TextField tfProgramme;
    @FXML
    private TextField tfGroupNo;
    @FXML
    private TextField tfSubGroupNo;
    @FXML
    private TextField tfGroupId;
    @FXML
    private TextField tfSubGroupId;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnGenerate;
    
    private ConnectionUtil conUtil = new ConnectionUtil();
    private Connection conn;
    private Statement st;
    private ResultSet rs;
    private String studentGroupId = null;
    
    /**
     * Initializes the controller class.
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showStudentGroup();
    }    

    @FXML      
    private void handleMouseClick(MouseEvent event) {
        StudentGroup studentgroups = tvStudentGroups.getSelectionModel().getSelectedItem();
        studentGroupId = studentgroups.getId();
        tfYear.setText(studentgroups.getYearSem());
        tfProgramme.setText(studentgroups.getProgramme());
        tfGroupNo.setText(studentgroups.getGroupNo());
        tfSubGroupNo.setText(studentgroups.getSubgroupNo());
        tfGroupId.setText(studentgroups.getGroupId());
        tfSubGroupId.setText(studentgroups.getSubgroupId());
    }

    @FXML
    private void handleButton(ActionEvent event) {
        StudentGroup studentgroups = tvStudentGroups.getSelectionModel().getSelectedItem();
        if(event.getSource() == btnSave){
            insertRecord();
        }else if(event.getSource() == btnUpdate && studentgroups != null){
            updateRecord();
        }else if(event.getSource() == btnDelete && studentgroups != null){
            deleteRecord();
            clearRecord();
        }else if(event.getSource() == btnClear){
            clearRecord();
        }
    }

    @FXML
    private void generateID(ActionEvent event) {
        tfGroupId.setText(tfYear.getText()+"."+tfProgramme.getText()+"."+tfGroupNo.getText());
        tfSubGroupId.setText(tfYear.getText()+"."+tfProgramme.getText()+"."+tfGroupNo.getText()+"."+tfSubGroupNo.getText());
    }
    
    public ObservableList<StudentGroup> getManageStudentGroupList(){
        ObservableList<StudentGroup> groupList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query = "SELECT * FROM studentgroups";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            StudentGroup group;
            
            while(rs.next()){
                group = new StudentGroup(rs.getString("id"), rs.getString("yearSem"), rs.getString("programme"), rs.getString("groupNo"),rs.getString("subgroupNo"), rs.getString("groupId"), rs.getString("subgroupId"));
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
    
    public void showStudentGroup(){
        ObservableList<StudentGroup> list = getManageStudentGroupList();
       
        colId.setCellValueFactory(new PropertyValueFactory<StudentGroup, String>("id"));
        colYear.setCellValueFactory(new PropertyValueFactory<StudentGroup, String>("yearSem"));
        colProg.setCellValueFactory(new PropertyValueFactory<StudentGroup, String>("programme"));
        colGoupNo.setCellValueFactory(new PropertyValueFactory<StudentGroup, String>("groupNo"));
        colSubGroupNo.setCellValueFactory(new PropertyValueFactory<StudentGroup, String>("subgroupNo"));
        colGroupId.setCellValueFactory(new PropertyValueFactory<StudentGroup, String>("groupId"));
        colSubGroupId.setCellValueFactory(new PropertyValueFactory<StudentGroup, String>("subgroupId"));
        
        tvStudentGroups.setItems(list);
    }
    
    private void insertRecord(){
        IDGenerator nextId = new IDGenerator();
        String nextgeneratedId = nextId.generateId("studentgroups", "G");
        String query = "INSERT INTO studentgroups VALUES (" + "'" + nextgeneratedId + "'" + ",'" + tfYear.getText() + "','" + tfProgramme.getText() + "',"
                + "'" + tfGroupNo.getText() + "','" + tfSubGroupNo.getText() + "','"+ tfGroupId.getText() + "','" + tfSubGroupId.getText() + "')";
        executeQuery(query);
        showStudentGroup();
    }
    
    private void updateRecord(){
        String query = "UPDATE  studentgroups SET yearSem  = '" + tfYear.getText() + "', programme = '" + tfProgramme.getText() + "', groupNo = " +
                "'" + tfGroupNo.getText() + "', sub'groupNo = " +"'" + tfSubGroupNo.getText()+"', groupid = '" + tfGroupId.getText() +"', subgroupid = '" + tfSubGroupId.getText() + "' WHERE id = " + "'" + studentGroupId + "'";
        executeQuery(query);
        showStudentGroup();
    }    

    private void deleteRecord(){
        String query = "DELETE FROM studentgroups WHERE id = " + "'" + studentGroupId + "'";
        executeQuery(query);
        showStudentGroup();
    }

    private void clearRecord(){
        tfYear.setText(null);
        tfProgramme.setText(null);
        tfGroupNo.setText(null);
        tfSubGroupNo.setText(null);
        tfGroupId.setText(null);
        tfSubGroupId.setText(null);
        
        
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
