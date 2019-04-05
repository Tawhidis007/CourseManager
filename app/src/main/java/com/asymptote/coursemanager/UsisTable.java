package com.asymptote.coursemanager;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "UsisTable")
public class UsisTable {
    @PrimaryKey(autoGenerate = true)
    int sl;

    @ColumnInfo(name = "course_code")
    String course_code;

    @ColumnInfo(name = "course_name")
    String course_name;

    @ColumnInfo(name = "faculty")
    String faculty;

    @ColumnInfo(name = "section")
    int section;

    @ColumnInfo(name = "total_seat")
    int total_seat;

    @ColumnInfo(name = "seat_booked")
    int seat_booked;

    @ColumnInfo(name = "seat_remaining")
    int seat_remaining;

    @ColumnInfo(name = "class_days")
    String class_days;

    @ColumnInfo(name = "class_time")
    String class_time;

    @ColumnInfo(name = "lab_day")
    String lab_day;

    @ColumnInfo(name = "lab_time ")
    String lab_time;

    public UsisTable(String course_code, String course_name, String faculty, int section,
                     int total_seat, int seat_booked, int seat_remaining, String class_days,
                     String class_time, String lab_day, String lab_time) {
        this.course_code = course_code;
        this.course_name = course_name;
        this.faculty = faculty;
        this.section = section;
        this.total_seat = total_seat;
        this.seat_booked = seat_booked;
        this.seat_remaining = seat_remaining;
        this.class_days = class_days;
        this.class_time = class_time;
        this.lab_day = lab_day;
        this.lab_time = lab_time;
    }

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public int getTotal_seat() {
        return total_seat;
    }

    public void setTotal_seat(int total_seat) {
        this.total_seat = total_seat;
    }

    public int getSeat_booked() {
        return seat_booked;
    }

    public void setSeat_booked(int seat_booked) {
        this.seat_booked = seat_booked;
    }

    public int getSeat_remaining() {
        return seat_remaining;
    }

    public void setSeat_remaining(int seat_remaining) {
        this.seat_remaining = seat_remaining;
    }

    public String getClass_days() {
        return class_days;
    }

    public void setClass_days(String class_days) {
        this.class_days = class_days;
    }

    public String getClass_time() {
        return class_time;
    }

    public void setClass_time(String class_time) {
        this.class_time = class_time;
    }

    public String getLab_day() {
        return lab_day;
    }

    public void setLab_day(String lab_day) {
        this.lab_day = lab_day;
    }

    public String getLab_time() {
        return lab_time;
    }

    public void setLab_time(String lab_time) {
        this.lab_time = lab_time;
    }
}
