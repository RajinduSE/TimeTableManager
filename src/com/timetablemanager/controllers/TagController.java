/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timetablemanager.controllers;

import com.timetablemanager.models.Tag;
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
public class TagController implements Initializable {

    @FXML
    private Button btnClear;
    @FXML
    private Button btnSave;
    @FXML
    private TableView<Tag> tvTags;
    @FXML
    private TableColumn<Tag, String> colId;
    @FXML
    private TableColumn<Tag, String> colTagName;
    @FXML
    private TableColumn<Tag, String> colTagCode;
    @FXML
    private TableColumn<Tag, String> colRTag;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private TextField tfTagName;
    @FXML
    private TextField tfTagCode;
    @FXML
    private TextField tfRTag;
    
    private ConnectionUtil conUtil = new ConnectionUtil();
    private Connection conn;
    private Statement st;
    private ResultSet rs;
    private String tagId = null;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showTags();
    }    

    @FXML
    private void handleButton(ActionEvent event) {
        Tag tag = tvTags.getSelectionModel().getSelectedItem();
        if(event.getSource() == btnSave){
            insertRecord();
        }else if(event.getSource() == btnUpdate && tag != null){
            updateRecord();
        }else if(event.getSource() == btnDelete && tag != null){
            deleteRecord();
            clearRecord();
        }else if(event.getSource() == btnClear){
            clearRecord();
        }
    }
    
    @FXML
    private void handleMouseAction(MouseEvent event) {
        Tag tag = tvTags.getSelectionModel().getSelectedItem();
        tagId = tag.getId();
        tfTagName.setText(tag.getTagName());
        tfTagCode.setText(tag.getTagCode());
        tfRTag.setText(tag.getRelateTag());
    }
    
    
    public ObservableList<Tag> getTagList(){
        ObservableList<Tag> tagList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query = "SELECT * FROM tags";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Tag tag;
            while(rs.next()){
                tag = new Tag(rs.getString("id"), rs.getString("tagName"), rs.getString("tagCode"), rs.getString("rTag"));
                tagList.add(tag);
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
        return tagList;
    }
    
    public void showTags(){
        ObservableList<Tag> list = getTagList();
       
        colId.setCellValueFactory(new PropertyValueFactory<Tag, String>("id"));
        colTagName.setCellValueFactory(new PropertyValueFactory<Tag, String>("tagName"));
        colTagCode.setCellValueFactory(new PropertyValueFactory<Tag, String>("tagCode"));
        colRTag.setCellValueFactory(new PropertyValueFactory<Tag, String>("relateTag"));
        
        tvTags.setItems(list);
    }
    
    private void insertRecord(){
        IDGenerator nextId = new IDGenerator();
        String nextgeneratedId = nextId.generateId("tags", "T");
        String query = "INSERT INTO tags VALUES(" + "'" + nextgeneratedId + "'" + ",'" + tfTagName.getText() + "','" + tfTagCode.getText() + "',"
                + "'" + tfRTag.getText() + "')";
        executeQuery(query);
        showTags();
    }
    
    private void updateRecord(){
        String query = "UPDATE  tags SET tagName  = '" + tfTagName.getText() + "', tagCode = '" + tfTagCode.getText() + "', rTag = " +
                "'" + tfRTag.getText() + "' WHERE id = " + "'" + tagId + "'";
        executeQuery(query);
        showTags();
    }  
    
     private void deleteRecord(){
        String query = "DELETE FROM tags WHERE id = " + "'" + tagId + "'";
        executeQuery(query);
        showTags();
    }
     
      private void clearRecord(){
        tfTagName.setText(null);
        tfTagCode.setText(null);
        tfRTag.setText(null);
        
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
