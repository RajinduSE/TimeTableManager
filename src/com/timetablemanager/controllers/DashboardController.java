/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timetablemanager.controllers;

import com.timetablemanager.utils.ConnectionUtil;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Rajindu's PC
 */
public class DashboardController implements Initializable {

    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private NumberAxis yHours;
    @FXML
    private CategoryAxis xDay;
    @FXML
    private Button btnLocations;
    @FXML
    private Button btnWorkDaysHrs;
    @FXML
    private Button btnLectures;
    @FXML
    private Button btnSubjects;
    @FXML
    private Button btnTags;
    @FXML
    private Button btnManage;
    @FXML
    private Button btnGenerate;
    @FXML
    private TextField tfDate;
    @FXML
    private Button btnStudentGroups;
    @FXML
    private ListView<String> lecturerList;
    
    private ConnectionUtil conUtil = new ConnectionUtil();
    private Connection conn;
    private Statement st;
    private ResultSet rs;
    @FXML
    private Label lblLecCount;
    @FXML
    private Label lblStdCount;
    @FXML
    private Label lblSubCount;
    @FXML
    private Label lblRoomCount;
    @FXML
    private Button btnDashboard;
    @FXML
    private Button btnViewLectures;
    @FXML
    private Button btnTimeTable;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Date date = new Date();
        SimpleDateFormat formattedDate = new SimpleDateFormat("MM/dd/yyyy");
        tfDate.setText(String.valueOf(formattedDate.format(date)));
        
        // TODO
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Total Hours For Lectures, Tutes & Labs");
        series1.getData().add(new XYChart.Data<>("Lecture",getTotLecHrs()));
        series1.getData().add(new XYChart.Data<>("Tutorial",getTotTuteHrs()));
        series1.getData().add(new XYChart.Data<>("Lab",getTotLabHrs()));
       
        barChart.getData().add(series1);
        
        lecturerList.setItems(getLecturerList());
        lblLecCount.setText(String.valueOf(getLecturerCount()));
        lblSubCount.setText(String.valueOf(getSubjectCount()));
        lblRoomCount.setText(String.valueOf(getRoomsCount()));
        lblStdCount.setText(String.valueOf(getStudentsCount()));
    }    

    @FXML
    private void navigateLocations(ActionEvent event) {
        try {
            Stage stage = (Stage)btnLocations.getScene().getWindow();
            Stage locationStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/timetablemanager/views/Location.fxml"));
            locationStage.setResizable(false);
            Scene scene = new Scene(root);
            locationStage.setScene(scene);
            locationStage.show();
            stage.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void navigateWorkDaysHrs(ActionEvent event) {
        try {
            Stage stage = (Stage)btnWorkDaysHrs.getScene().getWindow();
            Stage locationStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/timetablemanager/views/WorkingDayHour.fxml"));
            locationStage.setResizable(false);
            Scene scene = new Scene(root);
            locationStage.setScene(scene);
            locationStage.show();
            stage.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void navigateLectures(ActionEvent event) {
        try {
            Stage stage = (Stage)btnLectures.getScene().getWindow();
            Stage locationStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/timetablemanager/views/Lecturer.fxml"));
            locationStage.setResizable(false);
            Scene scene = new Scene(root);
            locationStage.setScene(scene);
            locationStage.show();
            stage.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void navigateSubjects(ActionEvent event) {
        try {
            Stage stage = (Stage)btnSubjects.getScene().getWindow();
            Stage locationStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/timetablemanager/views/Subject.fxml"));
            locationStage.setResizable(false);
            Scene scene = new Scene(root);
            locationStage.setScene(scene);
            locationStage.show();
            stage.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void navigateTags(ActionEvent event) {
        try {
            Stage stage = (Stage)btnManage.getScene().getWindow();
            Stage locationStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/timetablemanager/views/Tag.fxml"));
            locationStage.setResizable(false);
            Scene scene = new Scene(root);
            locationStage.setScene(scene);
            locationStage.show();
            stage.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void navigateManage(ActionEvent event) {
        try {
            Stage stage = (Stage)btnManage.getScene().getWindow();
            Stage locationStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/timetablemanager/views/Manage.fxml"));
            locationStage.setResizable(false);
            Scene scene = new Scene(root);
            locationStage.setScene(scene);
            locationStage.show();
            stage.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void generateTimeTable(ActionEvent event) {
        try {
            Stage stage = (Stage)btnGenerate.getScene().getWindow();
            Stage locationStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/timetablemanager/views/TimeTableManager.fxml"));
            locationStage.setResizable(false);
            Scene scene = new Scene(root);
            locationStage.setScene(scene);
            locationStage.show();
            stage.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void navigateStudentGroups(ActionEvent event) {
        try {
            Stage stage = (Stage)btnManage.getScene().getWindow();
            Stage locationStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/timetablemanager/views/ManageStudent.fxml"));
            locationStage.setResizable(false);
            Scene scene = new Scene(root);
            locationStage.setScene(scene);
            locationStage.show();
            stage.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void navigateDashboard(ActionEvent event) {
        try {
            Stage stage = (Stage)btnManage.getScene().getWindow();
            Stage locationStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/timetablemanager/views/Dashboard.fxml"));
            locationStage.setResizable(false);
            Scene scene = new Scene(root);
            locationStage.setScene(scene);
            locationStage.show();
            stage.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void viewAllLecturers(ActionEvent event) {
        try {
            Stage stage = (Stage)btnManage.getScene().getWindow();
            Stage locationStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/timetablemanager/views/Lecturer.fxml"));
            locationStage.setResizable(false);
            Scene scene = new Scene(root);
            locationStage.setScene(scene);
            locationStage.show();
            stage.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<String> getLecturerList(){
        ObservableList<String> lecturerList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query = "SELECT name FROM lecturers ORDER BY level,empId";
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
    
    public int getLecturerCount(){
        int count = 0;
        conn = conUtil.getConnection();
        String query = "SELECT count(id) AS count FROM lecturers";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
                while(rs.next()){
                count = rs.getInt("count");
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
        return count;
    }
    
    public int getSubjectCount(){
        int count = 0;
        conn = conUtil.getConnection();
        String query = "SELECT count(id) AS count FROM subjects";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
                while(rs.next()){
                count = rs.getInt("count");
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
        return count;
    }
    
    public int getRoomsCount(){
        int count = 0;
        conn = conUtil.getConnection();
        String query = "SELECT count(id) AS count FROM locations";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
                while(rs.next()){
                count = rs.getInt("count");
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
        return count;
    }
    
    public int getStudentsCount(){
        int count = 0;
        conn = conUtil.getConnection();
        String query = "SELECT count FROM studentscount";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
                while(rs.next()){
                count = rs.getInt("count");
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
        return count;
    }
    
    public int getTotLecHrs(){
        int total = 0;
        conn = conUtil.getConnection();
        String query = "SELECT sum(lecHrs) AS total FROM subjects";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
                while(rs.next()){
                total = rs.getInt("total");
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
        return total;
    }
    
    public int getTotTuteHrs(){
        int total = 0;
        conn = conUtil.getConnection();
        String query = "SELECT sum(tuteHrs) AS total FROM subjects";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
                while(rs.next()){
                total = rs.getInt("total");
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
        return total;
    }
    
    public int getTotLabHrs(){
        int total = 0;
        conn = conUtil.getConnection();
        String query = "SELECT sum(labHrs) AS total FROM subjects";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
                while(rs.next()){
                total = rs.getInt("total");
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
        return total;
    }

    

    
}
