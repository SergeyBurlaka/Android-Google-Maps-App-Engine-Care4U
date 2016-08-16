package com.example.Operator.myapplication.backend;

import com.google.api.server.spi.config.ApiTransformer;

/**
 * Created by Operator on 16.03.2016.
 */
@ApiTransformer( Employee.class)
public class EmailIdBar {
    private String email;
    private String regId;

    public String getEmail() {
        return email;
    }

    public String getRegId() {
        return regId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }





}
