/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timetablemanager.models;

/**
 *
 * @author Rajindu's PC
 */
public class Location {
    private String id;
    private String building;
    private String room;
    private String type;
    private int capacity;

    public Location(String id, String building, String room, String type, int capacity) {
        this.id = id;
        this.building = building;
        this.room = room;
        this.type = type;
        this.capacity = capacity;
    }

    public String getId() {
        return id;
    }

    public String getBuilding() {
        return building;
    }

    public String getRoom() {
        return room;
    }

    public String getType() {
        return type;
    }

    public int getCapacity() {
        return capacity;
    }
    
    
    
}
