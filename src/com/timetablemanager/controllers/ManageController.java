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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Rajindu's PC
 */
public class ManageController implements Initializable {

    @FXML
    private Button btnMngSubject;
    @FXML
    private Button btnMngTag;
    @FXML
    private Button btnMngLecturer;
    @FXML
    private Button btnMngGrp;
    @FXML
    private Button btnMngSession;
    @FXML
    private Button btnMngConsecutive;
    @FXML
    private Button btnMngParallel;
    @FXML
    private Button btnMngNonOverlapping;
    @FXML
    private Button btnMngSessionRoom;
    @FXML
    private Button btnMngConsRoom;
    @FXML
    private Button btnMngPreffered;
    @FXML
    private Button btnNotAvailableTimes;
    @FXML
    private Button btnPreffered;
    @FXML
    private Button btnMngNotAvailableRoom;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ManageSubjectRoom(ActionEvent event) {
        try {
            Stage stage = (Stage)btnMngSubject.getScene().getWindow();
            Stage locationStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/timetablemanager/views/SubjectRoom.fxml"));
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
    private void ManageTagRoom(ActionEvent event) {
        try {
            Stage stage = (Stage)btnMngTag.getScene().getWindow();
            Stage locationStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/timetablemanager/views/TagRoom.fxml"));
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
    private void ManageLecturerRoom(ActionEvent event) {
        try {
            Stage stage = (Stage)btnMngLecturer.getScene().getWindow();
            Stage locationStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/timetablemanager/views/LecturerRoom.fxml"));
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
    private void ManageGrpRoom(ActionEvent event) {
        try {
            Stage stage = (Stage)btnMngGrp.getScene().getWindow();
            Stage locationStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/timetablemanager/views/GroupRoom.fxml"));
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
    private void ManageSession(ActionEvent event) {
        try {
            Stage stage = (Stage)btnMngSession.getScene().getWindow();
            Stage locationStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/timetablemanager/views/Session.fxml"));
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
    private void ManageConsecutiveSessions(ActionEvent event) {
        try {
            Stage stage = (Stage)btnMngSessionRoom.getScene().getWindow();
            Stage locationStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/timetablemanager/views/ConsecutiveSession.fxml"));
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
    private void ManageParallelSessons(ActionEvent event) {
        try {
            Stage stage = (Stage)btnMngSessionRoom.getScene().getWindow();
            Stage locationStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/timetablemanager/views/Parasession.fxml"));
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
    private void ManageNonOverlappingSessions(ActionEvent event) {
        try {
            Stage stage = (Stage)btnMngSessionRoom.getScene().getWindow();
            Stage locationStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/timetablemanager/views/NonOverlap.fxml"));
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
    private void ManageSessionRoom(ActionEvent event) {
        try {
            Stage stage = (Stage)btnMngSessionRoom.getScene().getWindow();
            Stage locationStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/timetablemanager/views/SessionRoom.fxml"));
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
    private void ManageConsRoom(ActionEvent event) {
        try {
            Stage stage = (Stage)btnMngConsRoom.getScene().getWindow();
            Stage locationStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/timetablemanager/views/ConsecutiveSessionRoom.fxml"));
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
    private void ManagePreffered(ActionEvent event) {
        try {
            Stage stage = (Stage)btnMngPreffered.getScene().getWindow();
            Stage locationStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/timetablemanager/views/PreferredRoom.fxml"));
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
    private void ManageNotAvailableTimes(ActionEvent event) {
        try {
            Stage stage = (Stage)btnMngPreffered.getScene().getWindow();
            Stage locationStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/timetablemanager/views/SessionNotAvailable.fxml"));
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
    private void ManagePrefferedSessions(ActionEvent event) {
        try {
            Stage stage = (Stage)btnMngPreffered.getScene().getWindow();
            Stage locationStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/timetablemanager/views/SessionPreferred.fxml"));
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
    private void ManageNotAvailableRoom(ActionEvent event) {
        try {
            Stage stage = (Stage)btnMngPreffered.getScene().getWindow();
            Stage locationStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/timetablemanager/views/LocationNotAvailable.fxml"));
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
