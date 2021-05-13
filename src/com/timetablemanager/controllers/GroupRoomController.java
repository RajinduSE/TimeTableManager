/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timetablemanager.controllers;

import com.timetablemanager.models.GroupRoom;
import com.timetablemanager.models.SubGroupRoom;
import com.timetablemanager.utils.ConnectionUtil;
import com.timetablemanager.utils.IDGenerator;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Rajindu's PC
 */
public class GroupRoomController implements Initializable {

    @FXML
    private TableView<GroupRoom> tvGroupRooms;
    @FXML
    private TableColumn<GroupRoom, String> colGroupId;
    @FXML
    private TableColumn<GroupRoom, String> colGroup;
    @FXML
    private TableColumn<GroupRoom, String> colGroupRoom;
    @FXML
    private TableView<SubGroupRoom> tvSubGroupRoom;
    @FXML
    private TableColumn<SubGroupRoom, String> colSubGroupId;
    @FXML
    private TableColumn<SubGroupRoom, String> colSubgroup;
    @FXML
    private TableColumn<SubGroupRoom, String> colSubgroupRoom;
    @FXML
    private ComboBox<String> cbGroup;
    @FXML
    private ComboBox<String> cbSubGroup;
    @FXML
    private ComboBox<String> cbRoom;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnSave;
    
    private ConnectionUtil conUtil = new ConnectionUtil();
    private Connection conn;
    private Statement st;
    private PreparedStatement ps;
    private ResultSet rs;
    
    private String group = "";
    private String groupId;
    private String subGroup = "";
    private String subGroupId;
    private String room;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showGroupRoomList();
        showSubGroupRoomList();
        cbGroup.setItems(getGroupList());
        cbSubGroup.setItems(getSubGroupList());
        cbRoom.setItems(getRoomList());
        
    }    

    @FXML
    private void handleMouseGroupRooms(MouseEvent event) {
        GroupRoom groupRoom = tvGroupRooms.getSelectionModel().getSelectedItem();
        groupId = groupRoom.getId();
        subGroupId = "";
        clearRecord();
    }

    @FXML
    private void changeGroup(ActionEvent event) {
        group = cbGroup.getValue();
        cbSubGroup.setValue("Select Sub-Group");

    }

    @FXML
    private void changeSubGroup(ActionEvent event) {
        subGroup = cbSubGroup.getValue();
        cbGroup.setValue("Select Group");
    }

    @FXML
    private void changeRoom(ActionEvent event) {
        room = cbRoom.getValue();
    }

    @FXML
    private void buttonHandler(ActionEvent event) {
        if(event.getSource() == btnSave){
            insertRecord();
        }else if(event.getSource() == btnDelete){
            deleteRecord();
            clearRecord();
        }
    }

    @FXML
    private void handleMouseSubGroupRooms(MouseEvent event) {
        SubGroupRoom subGroupRoom = tvSubGroupRoom.getSelectionModel().getSelectedItem();
        subGroupId = subGroupRoom.getId();
        groupId = "";
        clearRecord();
    }
    
    public ObservableList<String> getGroupList(){
        ObservableList<String> groupList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query = "SELECT groupId FROM studentgroups";
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
    
    public ObservableList<String> getSubGroupList(){
        ObservableList<String> groupList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query = "SELECT subgroupId FROM studentgroups";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            String group;
            while(rs.next()){
                group = rs.getString("subgroupId");
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
    
    public ObservableList<String> getRoomList(){
        ObservableList<String> roomList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query = "SELECT room FROM locations";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            String room;
            while(rs.next()){
                room = rs.getString("room");
                roomList.add(room);
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
        return roomList;
    }

    public void insertRecord(){
        conn = conUtil.getConnection(); 
        IDGenerator nextId = new IDGenerator();
        String nextgeneratedId;
        String query;
        try{  
            if(!group.equals("") && !group.equals("Select Group")){
                nextgeneratedId = nextId.generateId("group_rooms", "G");
                query = "insert into group_rooms(`id`,`groupId`,`room`) values (?, ?, ?)";
                
                ps = conn.prepareStatement(query); 
                // binding values
                ps.setString(1, nextgeneratedId);
                ps.setString(2, group);
                ps.setString(3, room); 
                
                 //execute the statement
                 ps.execute();   
            }
            
            if(!subGroup.equals("") && !subGroup.equals("Select Sub-Group")){
                nextgeneratedId = nextId.generateId("subgroup_rooms", "SG");
                query = "insert into subgroup_rooms(`id`,`subGroupId`,`room`) values (?, ?, ?)";
                
                ps = conn.prepareStatement(query); 
                // binding values
                ps.setString(1, nextgeneratedId);
                ps.setString(2, subGroup);
                ps.setString(3, room);
                
                 //execute the statement
                 ps.execute();   
            }
            
                        
        }catch (Exception e){ 
            e.printStackTrace();
        }finally{
            try{
                if(conn != null){
                    conn.close();
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        showGroupRoomList();
        showSubGroupRoomList();
    }  
    
    public ObservableList<GroupRoom> getGroupRoomList(){
        ObservableList<GroupRoom> groupRoomList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query = "SELECT * FROM group_rooms";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            GroupRoom groupRoom;
            while(rs.next()){
                groupRoom = new GroupRoom(rs.getString("id"),rs.getString("groupId"), rs.getString("room"));
                groupRoomList.add(groupRoom);
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
        return groupRoomList;
    }
    
    public void showGroupRoomList(){
        ObservableList<GroupRoom> list = getGroupRoomList();
       
        colGroupId.setCellValueFactory(new PropertyValueFactory<GroupRoom, String>("id"));
        colGroup.setCellValueFactory(new PropertyValueFactory<GroupRoom, String>("group"));
        colGroupRoom.setCellValueFactory(new PropertyValueFactory<GroupRoom, String>("room"));
             
        tvGroupRooms.setItems(list);
    }
    
    public ObservableList<SubGroupRoom> getSubGroupRoomList(){
        ObservableList<SubGroupRoom> subGroupRoomList = FXCollections.observableArrayList();
        conn = conUtil.getConnection();
        String query = "SELECT * FROM subgroup_rooms";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            SubGroupRoom subGroupRoom;
            while(rs.next()){
                subGroupRoom = new SubGroupRoom(rs.getString("id"),rs.getString("subGroupId"), rs.getString("room"));
                subGroupRoomList.add(subGroupRoom);
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
        return subGroupRoomList;
    }
    
    public void showSubGroupRoomList(){
        ObservableList<SubGroupRoom> list = getSubGroupRoomList();
       
        colSubGroupId.setCellValueFactory(new PropertyValueFactory<SubGroupRoom, String>("id"));
        colSubgroup.setCellValueFactory(new PropertyValueFactory<SubGroupRoom, String>("subGroup"));
        colSubgroupRoom.setCellValueFactory(new PropertyValueFactory<SubGroupRoom, String>("room"));
             
        tvSubGroupRoom.setItems(list);
    }
    
    private void clearRecord(){
        cbGroup.setValue("Select Group");
        cbSubGroup.setValue("Select Sub-Group");
        cbRoom.setValue("Select Room");
   
    }
    
    public void deleteRecord(){
        conn = conUtil.getConnection();
        String query;
        try{
            if(!groupId.equals("")){

                query = "delete from group_rooms where id=?";
                
                ps = conn.prepareStatement(query); 
                // binding values
                ps.setString(1, groupId);
                         
                 //execute the statement
                 ps.execute();   
            }
            
            if(!subGroupId.equals("")){
  
                query = "delete from subgroup_rooms where id=?";
                
                ps = conn.prepareStatement(query); 
                // binding values
                ps.setString(1, subGroupId);
                
                 //execute the statement
                 ps.execute();   
            }
        }catch (Exception e){ 
            e.printStackTrace();
        }finally{
            try{
                if(conn != null){
                    conn.close();
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        showGroupRoomList();
        showSubGroupRoomList();
    }
}
