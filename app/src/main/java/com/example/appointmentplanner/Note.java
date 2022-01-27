package com.example.appointmentplanner;

import net.sourceforge.jtds.jdbc.DateTime;

import java.io.Serializable;

public class Note implements Serializable {

    private int  id;
    private String title;
    private String noteText;
    private String current_Date;
    private String color;
    private String imagePath;
    private String link;

    protected int getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    protected String getTitle() {
        return title;
    }

    protected void setTitle(String title) {
        this.title = title;
    }

    protected String getNoteText() {
        return noteText;
    }

    protected void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    protected String getCurrent_Date() {
        return current_Date;
    }

    protected void setCurrent_Date(String current_Date) {
        this.current_Date = current_Date;
    }

    protected String getColor() {
        return color;
    }

    protected void setColor(String color) {
        this.color = color;
    }

    protected String getImagePath() {
        return imagePath;
    }

    protected void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    protected String getLink() {
        return link;
    }

    protected void setLink(String link) {
        this.link = link;
    }
}
