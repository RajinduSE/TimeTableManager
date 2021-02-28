/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timetablemanager.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 * FXML Controller class
 *
 * @author Rajindu's PC
 */
public class DashboardController implements Initializable {

    @FXML
    private NumberAxis yHours;
    @FXML
    private CategoryAxis xDay;
    @FXML
    private BarChart<String, Number> barChart;


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
    
}
