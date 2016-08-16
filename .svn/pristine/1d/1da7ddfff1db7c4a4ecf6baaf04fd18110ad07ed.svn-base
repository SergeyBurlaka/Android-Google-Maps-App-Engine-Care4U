package com.example.Operator.myapplication.backend;

/**
 * Created by Operator on 25.03.2016.
 */
public class PersonRegIdInfo {
    private String employeeRegDeviceId;
   // private  String employeeEmail;

    public PersonRegIdInfo(String employeeRegDeviceId) {
        this.employeeRegDeviceId = employeeRegDeviceId;
    }

    @Override
    public String toString() {
        return employeeRegDeviceId;
    }


    public boolean equals(Object obj) {
        if (!(obj instanceof PersonRegIdInfo))
            return false;
       PersonRegIdInfo entry = (PersonRegIdInfo) obj;
        return employeeRegDeviceId.equals(entry.employeeRegDeviceId);
    }

}
