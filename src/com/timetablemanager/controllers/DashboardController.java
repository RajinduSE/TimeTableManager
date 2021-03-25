/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timetablemanager.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Total Hours For Lectures, Tutes & Labs");
        series1.getData().add(new XYChart.Data<>("Lecture",20));
        series1.getData().add(new XYChart.Data<>("Tutorial",12));
        series1.getData().add(new XYChart.Data<>("Lab",4));
       
        barChart.getData().add(series1);
        
              
    }    

    @FXML
    private void navigateLocations(ActionEvent event) {
        try {
            Stage stage = (Stage)btnLocations.getScene().getWindow();
            stage.close();
            Stage locationStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/timetablemanager/views/Location.fxml"));
            locationStage.setResizable(false);
            Scene scene = new Scene(root);
            locationStage.setScene(scene);
            locationStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void navigateWorkDaysHrs(ActionEvent event) {
    }

    @FXML
    private void navigateLectures(ActionEvent event) {
        try {
            Stage stage = (Stage)btnLectures.getScene().getWindow();
            stage.close();
            Stage locationStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/timetablemanager/views/Lecturer.fxml"));
            locationStage.setResizable(false);
            Scene scene = new Scene(root);
            locationStage.setScene(scene);
            locationStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void navigateSubjects(ActionEvent event) {
        try {
            Stage stage = (Stage)btnSubjects.getScene().getWindow();
            stage.close();
            Stage locationStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/timetablemanager/views/Subject.fxml"));
            locationStage.setResizable(false);
            Scene scene = new Scene(root);
            locationStage.setScene(scene);
            locationStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void navigateTags(ActionEvent event) {
    }

    @FXML
    private void navigateManage(ActionEvent event) {
        try {
            Stage stage = (Stage)btnManage.getScene().getWindow();
            stage.close();
            Stage locationStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/timetablemanager/views/Manage.fxml"));
            locationStage.setResizable(false);
            Scene scene = new Scene(root);
            locationStage.setScene(scene);
            locationStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void generateTimeTable(ActionEvent event) {
    }

    @FXML
    private void navigateStudentGroups(ActionEvent event) {
    }
    
}
