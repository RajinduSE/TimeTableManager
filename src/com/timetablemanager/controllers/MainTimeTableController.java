/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timetablemanager.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Rajindu's PC
 */
public class MainTimeTableController implements Initializable {

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
    private GridPane grid;
    @FXML
    private Label col1row1;
    @FXML
    private ComboBox<?> cbLecturer;
    @FXML
    private Button btnGenerate;
    @FXML
    private Button btnPrint;
    @FXML
    private Button btnBack;
    
    //new Label("Donedjsbdjsbdjsbdjsbdjsbdjsabdj\nSuccess");
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        col1row1 = new Label("Ms Kamala-IT4130-IUP-Practical-Y4.S1.IT.8.1-30-2");
        grid.add(col1row1, 1, 1);
    }    

    @FXML
    private void mouseClick(MouseEvent event) {
    }
    
    public String[] splitMe(String[] arr){
    ArrayList<String> retList = new ArrayList<String>();
    for(String t:arr){
        String[] splits = t.split("-");
        for(String s: splits){
            retList.add(s);
        }
    }
    return retList.toArray(new String[retList.size()]);
}

    @FXML
    private void changeLecturer(ActionEvent event) {
    }

    @FXML
    private void generate(ActionEvent event) {
    }

    @FXML
    private void print(ActionEvent event) {
    }

    @FXML
    private void back(ActionEvent event) {
    }
    
}
