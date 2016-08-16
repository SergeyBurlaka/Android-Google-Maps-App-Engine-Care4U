package com.example.Operator.myapplication.backend;

import com.google.api.server.spi.config.Transformer;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Serialize;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Operator on 08.03.2016.
 */
 @Entity

public class Employee implements Transformer<EmailIdBar, String> {

    /** */
    /*
    public static Key<Employee> key(long id) {
        return Key.create(Employee.class, id);
    }
    */


    @Id
    Long id;

    private String regId;

    @Index
    private String email;


    /*
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public List<Key<ManagerManager>> getSubordinates() {
        return subordinates;
    }
    */

    /*
    @Index
    List <Key<ManagerManager>> subordinates = new ArrayList<>();
    */

    public Map<String, String> getManagerEmailRegId() {
        return managerEmailRegId;
    }

    public void setManagerEmailRegId(Map<String, String> managerEmailRegId) {
        this.managerEmailRegId = managerEmailRegId;
    }

    @Serialize
    private Map<String , String> managerEmailRegId = new HashMap<>();





    //private String secretName;

    /*
    @Index
    @Load
    Ref <Manager> managerRef;
    */


    public Employee (){};


    /*
    Map <String , Key<RelationsGroup>> getLinking (){

        return linking;
    }*/

    /*
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public void setRelationKey(Key<ManagerManager> managerKey){
    this.managerKey = managerKey;
    }

    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public Key<ManagerManager> getRelationKey(){
        return managerKey;

    }
    */

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


    @Override
    public String transformTo(EmailIdBar emailIdBar) {


        return null;
    }

    @Override
    public EmailIdBar transformFrom(String s) {
        return null;
    }
}
