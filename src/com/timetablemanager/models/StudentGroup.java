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
public class StudentGroup {
    
    private String id;
    private String yearSem;
    private String programme;
    private String groupNo;
    private String subgroupNo;
    private String groupId;
    private String subgroupId;

    public StudentGroup(String id, String yearSem, String programme, String groupNo, String subgroupNo, String groupId, String subgroupId) {
        this.id = id;
        this.yearSem = yearSem;
        this.programme = programme;
        this.groupNo = groupNo;
        this.subgroupNo = subgroupNo;
        this.groupId = groupId;
        this.subgroupId = subgroupId;
    }
    
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYearSem() {
        return yearSem;
    }

    public void setYearSem(String yearSem) {
        this.yearSem = yearSem;
    }

    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public String getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo;
    }

    public String getSubgroupNo() {
        return subgroupNo;
    }

    public void setSubgroupNo(String subgroupNo) {
        this.subgroupNo = subgroupNo;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getSubgroupId() {
        return subgroupId;
    }

    public void setSubgroupId(String subgroupId) {
        this.subgroupId = subgroupId;
    }

    
}
