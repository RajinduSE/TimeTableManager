/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timetablemanager.controllers;

import com.timetablemanager.utils.ConnectionUtil;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Rajindu's PC
 */
public class StudentGroupTimeTableController implements Initializable {

    @FXML
    private GridPane grid;
    @FXML
    private Label lbCol1;
    @FXML
    private Label lbCol2;
    @FXML
    private Label lbCol3;
    @FXML
    private Label lbCol4;
    @FXML
    private Label lbCol5;
    @FXML
    private Label lbRow1;
    @FXML
    private Label lbRow2;
    @FXML
    private Label lbRow3;
    @FXML
    private Label lbRow4;
    @FXML
    private Label lbRow5;
    @FXML
    private Label lbRow6;
    @FXML
    private Label lbRow7;
    @FXML
    private Label lbRow8;
    @FXML
    private Label lbRow9;
    @FXML
    private ComboBox<String> cbGroup;
    @FXML
    private Button btnGenerate;
    @FXML
    private Button btnPrint;
    @FXML
    private Button btnBack;
    
    private ConnectionUtil conUtil = new ConnectionUtil();
    private Connection conn;
    private Statement st;
    private PreparedStatement ps;
    private ResultSet rs;
    
    private String group;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cbGroup.setItems(getGroupList());
    }    

    @FXML
    private void mouseClick(MouseEvent event) {
    }

    @FXML
    private void changeGroup(ActionEvent event) {
        group = cbGroup.getValue();
        if(!group.equals("Select Room")){
            cbGroup.setDisable(true);
        }
    }

    @FXML
    private void generate(ActionEvent event) {
        for(int i = 1; i <= 5; i++){
            String day = getDay(i);
            for(int j = 1; j <= 9; j++){
                String slot = getSlot(j);
                ObservableList<String> sessionList = getSessions(day, slot);
                String matchSession = getMatchSession(group, sessionList);
                
                Label lebel = new Label(matchSession);
                grid.add(lebel, i, j);
            }
        }
    }

    @FXML
    private void print(ActionEvent event) {
        captureAndSaveDisplay();
    }

    @FXML
    private void back(ActionEvent event) {
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
    
    public ObservableList<String> getGroupList(){
        ObservableList<String> groupList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query = "SELECT DISTINCT groupId FROM studentgroups";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            String group;
            while(rs.next()){
                group = rs.getString("groupId");
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
    
    private String getDay(int i) {
        switch(i){
            case 1: return "Monday";
            case 2: return "Tuesday";
            case 3: return "Wednesday";
            case 4: return "Thursday";
            case 5: return "Friday";
        }
        return null;
    }

    private String getSlot(int j) {
        switch(j){
            case 1: return "8.30";
            case 2: return "9.30";
            case 3: return "10.30";
            case 4: return "11.30";
            case 5: return "12.30";
            case 6: return "13.30";
            case 7: return "14.30";
            case 8: return "15.30";
            case 9: return "16.30";
            
        }
        return null;
    }
    
    public ObservableList<String> getSessions(String day, String slot){
        ObservableList<String> sessionList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query = "SELECT session FROM time_table_all WHERE day = " + "'" + day + "' AND slot = " + "'" + slot + "'";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            String session;
            while(rs.next()){
                session = rs.getString("session");
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
    
    private String getMatchSession(String group, ObservableList<String> sessionList) {
        String output = "";
        for(String t:sessionList){
            String[] splits = t.split("-");
            for(String s: splits){
                if(s.equals(group) || s.equals(group + ".1") || s.equals(group + ".2") || s.equals(group + ".3") || s.equals(group + ".4")){
                    output += t + "\n";
                }
            }
        }
        return output;
    }
    
    public void captureAndSaveDisplay(){
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("png files (*.png)", "*.png"));

        //Prompt user to select a file
        File file = fileChooser.showSaveDialog(null);

        if(file != null){
            try {
                //Pad the capture area
                WritableImage writableImage = grid.snapshot(null, null);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                //Write the snapshot to the chosen file
                ImageIO.write(renderedImage, "png", file);
                
            } catch (IOException ex) { ex.printStackTrace(); }
        }
    }
    
}
