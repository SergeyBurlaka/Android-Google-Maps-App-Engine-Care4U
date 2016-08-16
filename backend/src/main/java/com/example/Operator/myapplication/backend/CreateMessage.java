package com.example.Operator.myapplication.backend;

import org.json.simple.JSONObject;

/**
 * Created by Operator on 24.03.2016.
 */
public class CreateMessage {


    private int specialNumber;
    private boolean special;
    private  String messageText;
    private final int SIMPLE_MESSAGE = 1;
    JSONObject messageCreate;
    String emailFrom;
    private double [] data ;
    private int amountReq;


    public CreateMessage(String messageText, String emailFrom) {
        this.messageText = messageText;
        this.emailFrom = emailFrom;
        special =false;
    }


    //for sending info about request result to employee
    //info about denied or accepted request of employee
    public CreateMessage(String emailFrom,String messageText, int specialNumber) {
        this.messageText = messageText;
        this.emailFrom = emailFrom;
        this.specialNumber = specialNumber;
        special =true;
    }


    //for ask message of location from manager
    //number is give_location = 6
    public CreateMessage(String emailFrom,int specialNumber ) {


        this.emailFrom = emailFrom;
        this.specialNumber = specialNumber;
        this.special = true;
        this.data = new double[]{0., 0.};
    }


    //For to manager message
    // about new request "I am get hired" request
    public CreateMessage(String emailFrom,int specialNumber, int amountReq ) {

        this.emailFrom = emailFrom;
        this.specialNumber = specialNumber;
        this.special = true;
        this.amountReq = amountReq;

    }


    //for response to manager with employee location
    //number is take_location = 8
    public CreateMessage(String emailFrom,int specialNumber, double [] data ) {

        this.emailFrom = emailFrom;
        this.specialNumber = specialNumber;
        this.special = true;
        this.data = data;
    }







    public  String returnMessage (){
        messageCreate = new JSONObject();
        if (special){


            messageCreate.put("kind",specialNumber);
            messageCreate.put("from",emailFrom);

            switch (specialNumber ){
                case Ids.GIVE_LOCATION:
                case Ids.TAKE_LOCATION:
                    messageCreate.put("latitude",data[0]);
                    messageCreate.put("longitude",data[1]);


                    break;


                case Ids.NEW_EMPLOYEE_REQUEST:

                    messageCreate.put("amount requests", amountReq);
                    break;
                //TODO_done+02/07/16 case Ids.Infornation to employee
                //empty

                case Ids.INFO_TO_EMPLOYEE_REQ:
                    //put String messageText
                   messageCreate.put("message to employee",messageText );

                    break;

                //<--
            }

            special = false;

        }else {
            messageCreate.put("kind", SIMPLE_MESSAGE);
            messageCreate.put("from", emailFrom);
            messageCreate.put("text", messageText);
        }
        String message = messageCreate.toJSONString();
        return message;
    }



}
