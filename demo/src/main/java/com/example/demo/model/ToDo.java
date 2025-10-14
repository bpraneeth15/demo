package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

@Entity
@Table
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String taskDesc;
    private String dueDate;
    private String time;
    private Boolean status;

    public ToDo() {
        // Required by JPA/Hibernate to create an instance when loading from the database
    }

    public ToDo(long id, String taskDesc, String dueDate, String time, boolean isCompleted) {
        this.id = id;
        this.taskDesc = taskDesc;
        this.dueDate = dueDate;
        this.time = time;
        this.status = isCompleted;
    }

    //getters
    public long getID() {
        return id;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getTime() {
        return time;
    }

    public Boolean getStatus() {
        return status;
    }

    //setters
    public void setID(long ID) {
        this.id = ID;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setStatus(boolean completed) {
        status = completed;
    }

}
