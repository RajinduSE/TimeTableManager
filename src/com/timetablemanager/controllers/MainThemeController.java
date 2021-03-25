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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
