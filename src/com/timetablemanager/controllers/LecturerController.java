/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timetablemanager.controllers;

import com.timetablemanager.models.Lecturer;
import com.timetablemanager.models.Location;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Rajindu's PC
 */
public class LecturerController implements Initializable {

    @FXML
    private TextField tfName;
    @FXML
    private TextField tfEmpId;
    @FXML
    private ComboBox<String> cbFaculty;
    @FXML
    private ComboBox<String> cbDepartment;
    @FXML
    private ComboBox<String> cbCenter;
    @FXML
    private ComboBox<String> cbBuilding;
    @FXML
    private ComboBox<Integer> cbLevel;
    @FXML
    private TextField tfRank;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnGenarate;
    @FXML
    private TableColumn<Lecturer, String> colId;
    @FXML
    private TableColumn<Lecturer, String> colName;
    @FXML
    private TableColumn<Lecturer, Integer> colEmpId;
    @FXML
    private TableColumn<Lecturer, String> colDepartment;
    @FXML
    private TableColumn<Lecturer, String> colFaculty;
    @FXML
    private TableColumn<Lecturer, String> colCenter;
    @FXML
    private TableColumn<Lecturer, String> colBuilding;
    @FXML
    private TableColumn<Lecturer, Integer> colLevel;
    @FXML
    private TableColumn<Lecturer, Integer> colRank;
    @FXML
    private TableView<Lecturer> tvLecturers;
    
    private ConnectionUtil conUtil = new ConnectionUtil();
    private Connection conn;
    private Statement st;
    private ResultSet rs;
    private String faculty = null;
    private String department = null;
    private String center = null;
    private Integer level = null;
    private String building = null;
    
    ObservableList<String> facultyList = FXCollections.observableArrayList("Computing" ,"Engineering","Business","Humanities & Sciences");
    ObservableList<String> departmentList = FXCollections.observableArrayList("IT" ,"SE","Network","ISE");
    ObservableList<String> centerList = FXCollections.observableArrayList("Malabe", "Metro", "Matara", "Kandy", "Kurunagala", "Jaffna");
    ObservableList<Integer> levelList = FXCollections.observableArrayList(1,2,3,4,5,6);
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showLecturers();
        cbFaculty.setItems(facultyList);
        cbDepartment.setItems(departmentList);
        cbCenter.setItems(centerList);
        cbLevel.setItems(levelList);
        cbBuilding.setItems(getBuildingList());
    }    

    @FXML
    private void buttonHandler(ActionEvent event) {
        if(event.getSource() == btnSave){
            insertRecord();
        }
    }

    public ObservableList<Lecturer> getLecturerList(){
        ObservableList<Lecturer> lecturerList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query = "SELECT * FROM lecturers";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Lecturer lecturer;
            while(rs.next()){
                lecturer = new Lecturer(rs.getString("id"), rs.getString("name"), rs.getInt("empId"), rs.getString("department"),rs.getString("center"),rs.getString("building"),rs.getString("faculty"),rs.getInt("level"),rs.getInt("rank")); //"<sql table column name not model attribute name>"
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
    
    public void showLecturers(){
        ObservableList<Lecturer> list = getLecturerList();
       
        colId.setCellValueFactory(new PropertyValueFactory<Lecturer, String>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<Lecturer, String>("name"));
        colEmpId.setCellValueFactory(new PropertyValueFactory<Lecturer, Integer>("empId"));
        colDepartment.setCellValueFactory(new PropertyValueFactory<Lecturer, String>("department"));
        colFaculty.setCellValueFactory(new PropertyValueFactory<Lecturer, String>("faculty"));
        colCenter.setCellValueFactory(new PropertyValueFactory<Lecturer, String>("center"));
        colBuilding.setCellValueFactory(new PropertyValueFactory<Lecturer, String>("building"));
        colLevel.setCellValueFactory(new PropertyValueFactory<Lecturer, Integer>("level"));
        colRank.setCellValueFactory(new PropertyValueFactory<Lecturer, Integer>("rank"));
        
        tvLecturers.setItems(list);
    }
    
    private void insertRecord(){
        IDGenerator nextId = new IDGenerator();
        String nextgeneratedId = nextId.generateId("lecturers", "L");
        String query = "INSERT INTO lecturers VALUES (" + "'" + nextgeneratedId + "'" + ",'" + tfName.getText() + "','" + tfEmpId.getText() + "','" + department + "',"
                + "'" + center + "'" + "," + "'" + building + "'" + "," + "'" + faculty + "'" + "," + "'" + level + "'" + "," + "'" + 0 + "'" + ")";
        executeQuery(query);
        showLecturers();
    }
    
    private void updateRecord(){
        String query = "UPDATE  lecturers SET name  = '" + tfName.getText() + "', empId = '" + tfEmpId.getText() + "', department = " +
                "'" + department + "'" + ", center = " + center + "'" + ", building = " + building + "'" + ", faculty = " + faculty + "'" + ", level = " + level + " WHERE id = L1";
        executeQuery(query);
        showLecturers();
    }

      
    public ObservableList<String> getBuildingList(){
        ObservableList<String> buildingList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query = "SELECT building FROM locations";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            String building;
            while(rs.next()){
                building = rs.getString("building");
                buildingList.add(building);
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
        return buildingList;
    }
    
    @FXML
    private void facultyChange(ActionEvent event) {
        faculty = cbFaculty.getValue();
    }

    @FXML
    private void departmentChange(ActionEvent event) {
        department = cbDepartment.getValue();
    }

    @FXML
    private void centerChange(ActionEvent event) {
        center = cbCenter.getValue();
    }

    @FXML
    private void buildingChange(ActionEvent event) {
        building = cbBuilding.getValue();
    }

    @FXML
    private void levelChange(ActionEvent event) {
        level = cbLevel.getValue();
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
