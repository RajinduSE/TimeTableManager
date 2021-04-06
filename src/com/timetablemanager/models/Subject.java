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
public class Subject {
    private String id;
    private String subject;
    private String code;
    private int year;
    private int semester;
    private int lecHrs;
    private int tuteHrs;
    private int labHrs;
    private int evalHrs;

    public Subject(String id, String subject, String code, int year, int semester, int lecHrs, int tuteHrs, int labHrs, int evalHrs) {
        this.id = id;
        this.subject = subject;
        this.code = code;
        this.year = year;
        this.semester = semester;
        this.lecHrs = lecHrs;
        this.tuteHrs = tuteHrs;
        this.labHrs = labHrs;
        this.evalHrs = evalHrs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getLecHrs() {
        return lecHrs;
    }

    public void setLecHrs(int lecHrs) {
        this.lecHrs = lecHrs;
    }

    public int getTuteHrs() {
        return tuteHrs;
    }

    public void setTuteHrs(int tuteHrs) {
        this.tuteHrs = tuteHrs;
    }

    public int getLabHrs() {
        return labHrs;
    }

    public void setLabHrs(int labHrs) {
        this.labHrs = labHrs;
    }

    public int getEvalHrs() {
        return evalHrs;
    }

    public void setEvalHrs(int evalHrs) {
        this.evalHrs = evalHrs;
    }
    
}
