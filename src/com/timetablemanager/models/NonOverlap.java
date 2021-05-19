/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timetablemanager.models;

/**
 *
 * @author Asus
 */
public class NonOverlap {
    private String id;
    private String session1;
    private String session2;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSession1() {
        return session1;
    }

    public void setSession1(String session1) {
        this.session1 = session1;
    }

    public String getSession2() {
        return session2;
    }

    public void setSession2(String session2) {
        this.session2 = session2;
    }

    public NonOverlap(String id, String session1, String session2) {
        this.id = id;
        this.session1 = session1;
        this.session2 = session2;
}
    
}
