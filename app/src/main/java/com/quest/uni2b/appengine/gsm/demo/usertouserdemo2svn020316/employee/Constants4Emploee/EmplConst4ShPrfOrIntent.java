package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.Constants4Emploee;

/**
 * Created by Operator on 22.06.2016.
 */
public class EmplConst4ShPrfOrIntent {

    //<!--- 14:36 21 june 16
    //TODO_d+from2h 1>>>
    //TODO_d+8min : set consatnt for
    //TODO_d+ for saving circle
    //TODO_d+ for set status SPY or not spy
    //TODO_d+: fro set status open map from notification or from cabinet



    public static final String CIRCLE_LOCATION = "circleLocationForEmployeeEditor";   //for sh p editor

    public static final String CIRCLE_LATITUDE = "circleLatitudeForEmployee";

    public static final String CIRCLE_LONGITUDE = "circleLongitudeForEmployee";
    public static final String CIRCLE_RADIUS = "circleRadiusForEmployee";




    public static final String STATUS_SPY = "statusSPyViewForEmployeeEditor";//for sh p editor
    public static final int NO_SPY =-16;
    public static final int SPY =0076;



    public static final String OPEN_MAP_FROM = "openMapFromForEmployeeEditor";//for put extra
    public static final int FROM_NOTIFICATION = 1116;
    public static final int FROM_CABINET = 2226;


    //-->


    public static final int NOTIFY_SPY_FOR_EMPLOYEE_ID = 101;


    public static final String EMPLOYEE_INFO = "employeeInfo";



    public static final String INFO_REQ_LOC = "infoForGetEmployeeLocation";
    public static final String INFO_REQ_SPY= "infoForGetEmployeeSpying";

    public static final String REQ_ACTIVE_LOC = "requestActiveForGetEmployeeLocation";
    public static final String REQ_ACTIVE_SPY= "requestActiveForGetEmployeeSPY";

}
