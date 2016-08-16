package com.example.Operator.myapplication.backend;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID;
import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p/>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "employeeApi",
        version = "v1",
        resource = "employee",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.Operator.example.com",
                ownerName = "backend.myapplication.Operator.example.com",
                packagePath = ""
        )
)
public class EmployeeEndpoint {

    private static final Logger logger = Logger.getLogger(EmployeeEndpoint.class.getName());

    /** Api Keys can be obtained from the google cloud console */
    private static final String API_KEY = System.getProperty("gcm.api.key");

    private static final int DEFAULT_LIST_LIMIT = 20;
    private RelationsGroup relationsGroup;
    private String employeeRegId;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Employee.class);
    }

    /**
     * Returns the {@link Employee} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Employee} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "employee/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Employee get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting Employee with ID: " + id);
        Employee employee = ofy().load().type(Employee.class).id(id).now();
        if (employee == null) {
            throw new NotFoundException("Could not find Employee with ID: " + id);
        }
        return employee;
    }

    /**
     * Inserts a new {@code Employee}.
     */
    @ApiMethod(
            name = "insert",
            path = "employee",
            httpMethod = ApiMethod.HttpMethod.POST,
            clientIds = {
                    Ids.WEB_CLIENT_ID,
                    Ids.ANDROID_CLIENT_ID,
                    API_EXPLORER_CLIENT_ID },
            audiences = { Ids.ANDROID_AUDIENCE},
            scopes = {
        "https://www.googleapis.com/auth/userinfo.email"})

    public Employee insert(Employee employee, User user) throws UnauthorizedException {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that employee.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        if (user == null)
            throw new UnauthorizedException("User is not valid!");

        employee.setEmail(user.getEmail());

        ofy().save().entity(employee).now();
        logger.info("Created Employee with ID: " + employee.getId());

        return ofy().load().entity(employee).now();
    }

    /**
     * Updates an existing {@code Employee}.
     *
     * @param id       the ID of the entity to be updated
     * @param employee the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Employee}
     */
    @ApiMethod(
            name = "update",
            path = "employee/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Employee update(@Named("id") Long id, Employee employee) throws NotFoundException {
        //  You should validate your ID parameter against your resource's ID here.

       // Long idManager =  5707702298738688L;
        //Key<ManagerManager>managerKey=  Key.create(ManagerManager.class, idManager);

        //employee.setRelationKey(managerKey);
        checkExists(id);
        ofy().save().entity(employee).now();

        /*List<Employee> greetings = ObjectifyService.ofy()
                .load()
                .type(Employee.class)
                .ancestor(managerKey)
                .list();

        Employee ee = greetings.get(0);
        String key = ee.getRelationKey().toString();
        ee.setEmail( key);
        checkExists(id);
        ofy().save().entity(employee).now();*/
        logger.info("Updated Employee: " + employee);

        return ofy().load().entity(employee).now();
        //return ee;
    }

    /**
     * Deletes the specified {@code Employee}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Employee}
     */
    @ApiMethod(
            name = "remove",
            path = "employee/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(Employee.class).id(id).now();
        logger.info("Deleted Employee with ID: " + id);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "employee",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Employee> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Employee> query = ofy().load().type(Employee.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Employee> queryIterator = query.iterator();
        List<Employee> employeeList = new ArrayList<Employee>(limit);
        while (queryIterator.hasNext()) {
            employeeList.add(queryIterator.next());
        }
        return CollectionResponse.<Employee>builder().setItems(employeeList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(Employee.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Employee with ID: " + id);
        }
    }

    /*
    *
    *  MyApiMethods for app logic.
    *
    */

    // Method for creating employees group with manager, as chief.
    //To enter in this group (get hired by manager), employee must enter email of manager and secret name of group (invented by manager)
    //This method uses features of objectify framework, where manager becomes parent and employees became children.

/*
    @ApiMethod(
            name = "hired",
            path = "employee/gethired/{id}",
            httpMethod = ApiMethod.HttpMethod.POST)
    public void hired (@Named("id") Long employeeId, @Named("manageremail") String managerEmail  ) throws NotFoundException {

           RelationsGroup relationsGroup = ofy().load().type(RelationsGroup.class).filter("managerEmail",managerEmail).first().now();
            String email = ofy().load().key(Key.create(Employee.class, employeeId)).now().getEmail();
            relationsGroup.getEmployeeRequestsList().put(email, Key.create(Employee.class,employeeId));
    }
*/



    @ApiMethod(
            name = "HELP",
            path = "employee/sender/{id}",
            httpMethod = ApiMethod.HttpMethod.POST)
    public void help (@Named("id") Long employeeId ) throws NotFoundException{

        String regId = null;
        Employee employee= ofy().load().key(Key.create( Employee.class,employeeId)).now();
        //regId = employee.getManagerEmailRegId().get(managerEmail);



        for( Map.Entry<String, String> entry : employee.getManagerEmailRegId().entrySet()){
            regId = entry.getValue();


            String m = "HELP!!Someone wants to kill me!"+"\n"+ "Sincerely yours, "+"\n "+employee.getEmail();
            CreateMessage cm = new CreateMessage (m, employee.getEmail());
            SendMessages(regId, cm.returnMessage());


            String m2 = "Help has been sent to "+entry.getKey();
            CreateMessage cm2 = new CreateMessage (m2, "server");
            SendMessages(employee.getRegId(), cm2.returnMessage());

            //SendMessages(regId, "HELP!!Someone wants to kill me!"+"\n"+ "Sincerely yours, "+"\n "+employee.getEmail());
            //SendMessages(employee.getRegId(),"Help has been sent to "+entry.getKey());
        }

        }



    @ApiMethod(
            name = "Outside",
            path = "employee/sender/outside/{id}",
            httpMethod = ApiMethod.HttpMethod.POST)


    public void outside(@Named("id") Long employeeId, @Named("spymessage")String employeeOutsideMessage) {

        String regId = null;
        Employee employee= ofy().load().key(Key.create( Employee.class,employeeId)).now();
        //regId = employee.getManagerEmailRegId().get(managerEmail);



        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(employeeOutsideMessage);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = (JSONObject) obj;

        jsonObject.put("from",employee.getEmail());

        //JSONObject reader = new JSONObject();
            //get detect constant
            //kindMessage = reader.getInt("kind");
          //  reader.put("from", employee.getEmail());



        for( Map.Entry<String, String> entry : employee.getManagerEmailRegId().entrySet()){
            regId = entry.getValue();



            // String m = "HELP!!Someone wants to kill me!"+"\n"+ "Sincerely yours, "+"\n "+employee.getEmail();
           // CreateMessage cm = new CreateMessage (m, employee.getEmail());
            try {
                SendMessages(regId,jsonObject.toString() );
            } catch (NotFoundException e) {
                e.printStackTrace();
            }




            //SendMessages(regId, "HELP!!Someone wants to kill me!"+"\n"+ "Sincerely yours, "+"\n "+employee.getEmail());
            //SendMessages(employee.getRegId(),"Help has been sent to "+entry.getKey());
        }

    }



    private void SendMessages (  String regId, String msgString) throws NotFoundException {

        Sender sender = new Sender(API_KEY);
        Message msg = new Message.Builder().addData("message", msgString).build();
        try {
            Result result = sender.send(msg,regId, 5);
        } catch (IOException e) {
            e.printStackTrace();
            throw new NotFoundException("ups");
        }

    }




    @ApiMethod(
            name = "Hired",
            path = "employee/gethired/{id}",
            httpMethod = ApiMethod.HttpMethod.POST)
    public void hired (@Named("id") Long employeeId, @Named("managerEmail") String managerEmail )  {
            int amountReq;

        Employee employee = ofy().load().key(Key.create(Employee.class, employeeId)).now();

            relationsGroup = ofy().load().type(RelationsGroup.class).filter("managerEmail", managerEmail).first().now();



        //save employee in requestList to manager

        relationsGroup.getEmployeeRequestsList().put(employee.getEmail(), Key.create(Employee.class, employeeId));



        //for notification to manager
      // amountReq =  relationsGroup.getEmployeeList().size();



        //and save it in datastore againe!
        ofy().save().entity(relationsGroup).now();



        //for notification to manager
       ManagerManager manager = ofy().load().type(ManagerManager.class).filter("email", managerEmail).first().now();
       if( manager == null)  new IllegalArgumentException("manager null");


        //Sending message
        //
        String m = "Your request has been submitted successfully.";
        CreateMessage cm = new CreateMessage (m, "server");
        //
        //SendMessages(employee.getRegId(), "Your request has been submitted successfully.");
        try {
            SendMessages(employee.getRegId(), cm.returnMessage());
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        //<!---
        //TODO_DONE_01_07_16: send notification message to manager new request id 25
        // It have amount of new requests

        CreateMessage newMssg = new CreateMessage( employee.getEmail(),Ids.NEW_EMPLOYEE_REQUEST,  relationsGroup.getEmployeeRequestsList().size() );
        try {
            SendMessages(manager.getRegId(), newMssg.returnMessage() );
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        //-->

    }


    @ApiMethod(
            name = "getEmployesTest",
            path = "employee/getEmployees/{id}",
            httpMethod = ApiMethod.HttpMethod.POST)
    public void getEmplyeesTest(@Named("id") Long employeeId ) throws NotFoundException {
        StringBuilder sb = new StringBuilder();


        Employee employee = ofy().load().key(Key.create(Employee.class, employeeId)).now();

        for (String me : employee.getManagerEmailRegId().keySet()){
            sb.append(me+"; "+"\n");}

        //Sending message
        //
        String m = "You have entered as: " + employee.getEmail() +"\n"+ "Your manager(s) is(are): " + sb;
        CreateMessage cm = new CreateMessage (m, "server");
        //
        //SendMessages(employee.getRegId(),  "You have entered as: " + employee.getEmail() +"\n"+ "Your manager(s) is(are): " + sb);
        SendMessages(employee.getRegId(), cm.returnMessage());
    }



    @ApiMethod(
            name = "sendEmployeetLocation",
            path = "employee/spy",
            httpMethod = ApiMethod.HttpMethod.GET)
    public void sendEmployeeLocation (@Named("id") Long employeeId, @Named ("latitude")double latitude,@Named ("longitude")double longitude)  {



        double[] myLocation = new double[]{latitude,longitude};
        String regId = null;
        Employee employee= ofy().load().key(Key.create( Employee.class,employeeId)).now();
        //regId = employee.getManagerEmailRegId().get(managerEmail);



        for( Map.Entry<String, String> entry : employee.getManagerEmailRegId().entrySet()) {

            regId = entry.getValue();
            CreateMessage cm = new CreateMessage (employee.getEmail(),Ids.TAKE_LOCATION, myLocation);
            try {
                SendMessages(regId, cm.returnMessage());
            } catch (NotFoundException e) {
                e.printStackTrace();
            }

        }




    }





    @ApiMethod(
            name = "setEmployeeRegId",
            path = "employee/setEmployeeRegId/{id}",
            httpMethod = ApiMethod.HttpMethod.POST
    )

    //<!---10june16_16:14
   //TODO_d+++: also set new employeeRegId in managerList if such exist in employee's friend
   //this method set new regId for employee And must called setEmployeeRegId
    public void setManagerRegId(@Named("id") Long employeeId, @Named("setRegId")String setRegId) throws UnauthorizedException, NotFoundException {
        //<--
        // user.

        String employeeEmail;

        //we will use manager object as a container to transport employees( which belong to the manager) emails
        //for bulding list of employees in manager cabinet
        Employee employee = ofy().load().key(Key.create(Employee.class, employeeId)).now();

        employee.setRegId(setRegId);
        ofy().save().entity(employee).now();

        //<!---10june16_16:14
        //TODO_d+??: change reg id in all relations group with the employee


        employeeEmail = employee.getEmail();
        for (String managerEmail : employee.getManagerEmailRegId().keySet()){

            relationsGroup = ofy().load().type(RelationsGroup.class).filter("managerEmail", managerEmail).first().now();

            relationsGroup.getEmployeeList().put(employeeEmail, setRegId);

            //update relations group
            try {
                //ofy().load().type(RelationsGroup.class).id(relationsGroup.id).safe();
                ofy().load().type(RelationsGroup.class).filter("managerEmail", managerEmail).first().safe();

            } catch (com.googlecode.objectify.NotFoundException e) {
                throw new NotFoundException("Could not find relationGroup with ID: " + relationsGroup.id);
            }
            ofy().save().entity(relationsGroup).now();


        }

        //<--

    }

    //<--
}











