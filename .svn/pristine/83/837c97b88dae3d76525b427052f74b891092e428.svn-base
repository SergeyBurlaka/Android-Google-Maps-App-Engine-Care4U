package com.example.Operator.myapplication.backend;

import org.json.simple.JSONObject;

/**
 * Created by Ветал on 24.03.2016.
 */
// private final String typeSpy = "Alert";
public class CreateSpyMessage {


    // private final String typeSpy = "Alert";
    JSONObject message;
    JSONObject data;


    private double longitude;
    private double latitude;
    private int radius;
    private String managerEmail;
    public static final int KIND_SPY = 7;

    public CreateSpyMessage(double latitude, double longitude, int radius, String managerEmail) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        // this.eEmail = employeeEmail;
        this.managerEmail = managerEmail;

    }

    public String getSpyMessage() {
        data = new JSONObject();

            data.put("latitude", latitude);

            data.put("longitude", longitude);
            data.put("radius", radius);


        message = new JSONObject();

            message.put("kind", KIND_SPY);
            message.put("from", managerEmail);
            message.put("data", data);

        String spyMessage = message.toString();

        return spyMessage;
    }

}
