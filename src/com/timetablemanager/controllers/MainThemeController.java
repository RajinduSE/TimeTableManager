/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timetablemanager.controllers;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Rajindu's PC
 */
public class MainThemeController implements Initializable {

    @FXML
    private Button btnWorkDaysHrs;
    @FXML
    private Button btnLectures;
    @FXML
    private Button btnSubjects;
    @FXML
    private Button btnStudentGroups;
    @FXML
    private Button btnTags;
    @FXML
    private Button btnLocations;
    @FXML
    private Button btnManage;
    @FXML
    private Button btnGenerate;
    @FXML
    private TextField tfDate;
    @FXML
    private Button btnDashboard;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Date date = new Date();
        SimpleDateFormat formattedDate = new SimpleDateFormat("MM/dd/yyyy");
        tfDate.setText(String.valueOf(formattedDate.format(date)));
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
        }
        catch (IOException e) {
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
    
}
