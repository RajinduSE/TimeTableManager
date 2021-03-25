/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timetablemanager.utils;

import com.timetablemanager.models.Location;
import java.sql.*;
/**
 *
 * @author Rajindu's PC
 */
public class IDGenerator {
    private ConnectionUtil conUtil = new ConnectionUtil();
    private Connection conn;
    private Statement st;
    private ResultSet rs;
    
    public String generateId(String tableName, String str){
        String currentId = null;
        String nextId = null;
        conn = conUtil.getConnection();
        String query = "SELECT id FROM " + tableName + " ORDER BY id DESC LIMIT 1";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
             while(rs.next()){
                currentId = rs.getString("id"); 
            }   
             nextId = str + (Integer.parseInt(currentId.substring(str.length())) + 1);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return nextId;
    }
}
