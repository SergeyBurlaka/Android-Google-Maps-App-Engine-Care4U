package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee;

/**
 * Created by Operator on 10.03.2016.
 */
public enum AsynTaskForEmployee {

    SOS,// send anxiety notification to manager
    GET_Hired, // for  binding of employee to manager
    CHECK_INFO,// to get personal email of employee & his manager email
    SEND_SPY_STATUS, // when employee is out of circle during spy of manager
    TAKE_LOCATION,
    SET_NEW_REG_ID
}
