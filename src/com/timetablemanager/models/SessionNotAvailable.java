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
public class SessionNotAvailable {
    private String id;
    private String lecturer;
    private String session;
    private String day;
    private int time;
    private String group;
    private String sgroup;

    public SessionNotAvailable(String id, String lecturer, String session, String day, int time, String group, String sgroup) {
        this.id = id;
        this.lecturer = lecturer;
        this.session = session;
        this.day = day;
        this.time = time;
        this.group = group;
        this.sgroup = sgroup;
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

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getSgroup() {
        return sgroup;
    }

    public void setSgroup(String sgroup) {
        this.sgroup = sgroup;
    }
    
    
    
}
