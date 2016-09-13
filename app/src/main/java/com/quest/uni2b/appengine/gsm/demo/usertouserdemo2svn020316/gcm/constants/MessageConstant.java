package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.constants;

/**
 * Created by Operator on 24.03.2016.
 */
public class MessageConstant {
    //from this message began spying for employee
    public static final int KIND_SPY = 7;
    //just for showing toasts
    public static final int SIMPLE_MESSAGE = 1;
    //sends statuses where employee (inside or outside) during spying
    public static final int OUTSIDE_MESSAGE = 13;
    //for get EmployeeOfM location
    //when manager opens map he send request for employee's location
   //from manager to employee
    public static  final int GIVE_LOCATION = 6;
    //from employee to manager
    public static  final int TAKE_LOCATION = 8;
    //request from employee to manager to add to friends
    public static  final int NEW_EMPLOYEE_REQUEST = 25;
    //answer of manager to employee request
    public static  final int INFO_TO_EMPLOYEE_REQ = 16;
    //constant - statuses during spying for employee
    //witch sends in OUTSIDE message to manager
    public static final int EXIT_CIRCLE = 703;
    public static final int ENTER_CIRCLE = 705;
    public static final int BEGIN_OUTSIDE = 701;
    public static final int BEGIN_INSIDE = 702;

   /*
    * CONSTANTS FOR MANAGER SHARED_PREFERENCE:
    */
    //constant for shared preference
    //for status in list view in manager cabinet list activity
    public static final String STATUS_SPY_VIEW = "statusSPyViewForManagerEditor";//for sh p editor
    public static final int NO_SPY =-1;
    public static final boolean DISABLE_SPY =true;
    public static final boolean ENABLE_SPY =false;
    public static final int STOP_SPY = 23;

    public static final String CIRCLE_LOCATION = "circleLocationForManagerEditor";   //for sh p editor
    public static final String CIRCLE_LATITUDE = "circleLatitudeForManager";
    public static final String CIRCLE_LONGITUDE = "circleLongitudeForManager";
    public static final String CIRCLE_RADIUS = "circleRadiusForManager";
    public static final String EMPLOYEE_LOC_LATITUDE = "employeeLatitudeForManager";
    public static final String EMPLOYEE_lOC_LONGITUDE = "employeeLongitudeForManager";
    public static final String EMPLOYEE_LOC_STATUS_CONST = "employeeStatusForManager";
    public static final String EMPLOYEE_LOC_IS_HERE = "isHereForManager";
    public static final String EMPLOYEE_LOC_EMAIL = "employeeMapForManager";
    //isHere
    //employeeEmail
    public static final String OPEN_USER_MAP_FROM = "openMapFromForManagerEditor";//for sh p editor
    public static final int FROM_NOTIFICATION = 111;
    public static final int FROM_CABINET = 222;
    public static final String MANAGER_INFO = "managerInfo";

    public static final int GPS_FACE_CONTROL = 40000;
    public static final int GPS_DISABLE = 40001;
    public static final int SPY_DID_NOT_START = 40002;
    public static final int TURN_GPS_DISABLE = 40003;
    public static final int TURN_GPS_ENABLE = 40005;
    public static final int WIN_GET_LOC = 40016;
    public static final int  DECLINED_OFFER_GPS = 40013 ;
    public static final int FLIGHT_MODE_ON = 40002;

    public static final String ACTION_START_SPY = "com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.GCM.notifications";

   //For Shared Preference clicked spy item status
   //noMore4Notification
    public static final String  STOP_NOTIFICATIONS = "noMore4Notifications";
    public static final String  CLICKED_SPY = "clickedSpy";
    public static final int  CLICKED =  255;
    public static final int  ENABLE =  256;
    public static final int  DISABLE =  -255;

    //for check isOfflineEmployee
    //Shared preference const for saving list of spying employee
    public static final String  SPY_EMPLOYEE_LIST = "spyEmployeeList";
    //public static final String  USERS_SIZE = "Users_size";
    //public static final String  USER = "Users";
    //for check isOfflineEmployee
    public static final String  IS_PRESENT = "spyEmployeeList";
    public static final int  IS_PRESENT_DISABLE = 0;
    public static final int  IS_PRESENT_FALSE = 13;
    public static final int  IS_PRESENT_TRUE = 5;
    public static final String  IS_ONLINE_ALARM = "isEmployeeAlarm";
    public static final String  GET_ALTERNATE = "getAlternate";
    public static final int  AN_EVEN= 2;
    public static final int  ODD = 3;
    public static final int INTERRUPT = 4;
}
