package com.example.Operator.myapplication.backend;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Serialize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Operator on 08.03.2016.
 */
@Entity

public class ManagerManager  {

    /** */
    /*
    public static Key<Employee> key(long id) {
        return Key.create(Employee.class, id);
    }
    */
    @Id
    Long id;
    @Index
    private String regId;

    @Index
    private String email;
    CircleLabel cl;

    public void setIsManager(Boolean isManager) {
        this.isManager = isManager;
    }

    public Boolean getIsManager() {
        return isManager;
    }

    private  Boolean isManager ;

    private String secretName;

    public List<String> getListEmails() {
        return listEmails;
    }

    public void setListEmails(List<String> listEmails) {
        this.listEmails = listEmails;
    }

    private List<String> listEmails = new ArrayList<>();


    public Map<String,Key<Employee>> getEmployeeEmailKeyMap() {
    return employeeEmailKeyMap;
    }


   //not used yet

    @Serialize
    private Map<String , Key<Employee>> employeeEmailKeyMap = new HashMap<>();

    /*
    @Index
    @Load
    Ref <Manager> managerRef;
    */





    public ManagerManager (){};



    public Long getId(){
        return id;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
