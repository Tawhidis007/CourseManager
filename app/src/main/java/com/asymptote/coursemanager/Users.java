package com.asymptote.coursemanager;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Users")
public class Users {
    @PrimaryKey(autoGenerate = true)
    int uid;

    @ColumnInfo(name = "userName")
    String userName;

    @ColumnInfo(name = "email")
    String email;

    @ColumnInfo(name = "password")
    String password;

    @ColumnInfo(name = "university")
    String university;

    public Users(String userName, String email, String password, String university) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.university = university;
    }

    public String getuserName() {
        return userName;
    }

    public void setuserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setSemester(String password) {
        this.password = password;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    @Override
    public String toString() {
        return "Name : "+userName+"\n"+
                "Email : "+email+"\n"+
                "Password : "+password+"\n"+
                "University : "+university +"\n"+"\n";
    }
}
