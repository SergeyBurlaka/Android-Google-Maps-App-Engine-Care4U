package com.example.Operator.myapplication.backend;

/**
 * Created by Operator on 25.03.2016.
 */
public class PersonEmailInfo  {
        //private String employeeRegDeviceId;
         private  String employeeEmail;

        public PersonEmailInfo   (String employeeEmail) {
            this.employeeEmail = employeeEmail;
        }

        @Override
        public String toString() {
            return employeeEmail;
        }


        public boolean equals(Object obj) {
            if (!(obj instanceof PersonEmailInfo ))
                return false;
            PersonEmailInfo  entry = (PersonEmailInfo ) obj;
            return employeeEmail.equals(entry.employeeEmail);
        }

}