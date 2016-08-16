package com.example.Operator.myapplication.backend;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Serialize;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Operator on 15.03.2016.
 */

@Entity
public class RelationsGroup {

    @Parent
    Key<ManagerManager> managerKey;
    @Id
    Long id;
    @Index
    private String managerEmail;

   @Serialize
    private HashMap<String,Key <Employee>> employeeRequestsList = new HashMap<>();

    @Serialize
    private Map<String,String> employeeList = new HashMap<>();

    public HashMap<String, Key<Employee>> getEmployeeEmailKeyMap() {
        return EmployeeEmailKeyMap;
    }

    @Serialize
    private HashMap<String,Key <Employee>> EmployeeEmailKeyMap = new HashMap<>();


    RelationsGroup (){};
    RelationsGroup(Long idParent){
        managerKey = Key.create(ManagerManager.class, idParent);

    }



    public  Map<String,Key <Employee>> getEmployeeRequestsList (){
        return employeeRequestsList;
    }

    public  Map <String,String> getEmployeeList (){
        return employeeList;

    }


    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public void setManagerRelationKey(Key<ManagerManager> managerKey){
        this.managerKey = managerKey;
    }

    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public Key<ManagerManager> getManagerRelationKey(){
        return managerKey;
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }


}
