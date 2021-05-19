/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timetablemanager.models;

/**
 *
 * @author Shelani Wijesekera
 */
public class SessionPreferred {
    private String id;
    private String lecturer;
    private int time;
    private String session;
    private String day;
    

    public SessionPreferred(String id, String lecturer, int time, String session, String day) {
        this.id = id;
        this.lecturer = lecturer;
        this.time = time;
        this.session = session;
        this.day = day;
        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }
    
    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    
    
    
    
}
