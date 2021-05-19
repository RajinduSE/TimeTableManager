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
public class Tag {
    
    private String id;
    private String tagName;
    private String tagCode;
    private String relateTag;

    public Tag(String id, String tagName, String tagCode, String relateTag) {
        this.id = id;
        this.tagName = tagName;
        this.tagCode = tagCode;
        this.relateTag = relateTag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagCode() {
        return tagCode;
    }

    public void setTagCode(String tagCode) {
        this.tagCode = tagCode;
    }

    public String getRelateTag() {
        return relateTag;
    }

    public void setRelateTag(String relateTag) {
        this.relateTag = relateTag;
    }

   
 

}
