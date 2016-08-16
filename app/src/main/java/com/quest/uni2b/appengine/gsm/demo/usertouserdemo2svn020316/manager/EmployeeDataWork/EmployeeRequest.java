package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.EmployeeDataWork;

/**
 * Created by Operator on 17.03.2016.
 */
public class EmployeeRequest {


  private   String email;


    private   Boolean box;

    public EmployeeRequest(String email, Boolean box){
        this.email = email;
        this.box = box;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof EmployeeRequest))
            return false;
        EmployeeRequest entry = (EmployeeRequest) obj;
        return email.equals(entry.email);
    }

    public String getEmail() {
        return email;
    }


    public Boolean getBox() {
        return box;
    }

    public void setBox(Boolean box) {
        this.box = box;
    }

}
