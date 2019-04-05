package com.asymptote.coursemanager;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Courses")
public class Courses {
    @PrimaryKey(autoGenerate = true)
    int course_no;

    @ColumnInfo(name = "course_name")
    String course_name;

//    public String getBooks() {
//        return books;
//    }

    @ColumnInfo(name = "directory")
    String directory;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @ColumnInfo(name = "desc")
    String desc;

    public Courses(String course_name, String directory,String desc) {
        this.course_name = course_name;
        this.directory = directory;
        this.desc = desc;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    @Override
    public String toString() {
        return course_name+" : "+directory+"books : ";
    }
}
