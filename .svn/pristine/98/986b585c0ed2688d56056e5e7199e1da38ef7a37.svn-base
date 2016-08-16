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
        name = "managerManagerApi",
        version = "v1",
        resource = "managerManager",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.Operator.example.com",
                ownerName = "backend.myapplication.Operator.example.com",
                packagePath = ""
        )
)
public class ManagerManagerEndpoint {

    private static final Logger logger = Logger.getLogger(ManagerManagerEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;
    /** Api Keys can be obtained from the google cloud console */
    private static final String API_KEY = System.getProperty("gcm.api.key");


    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(ManagerManager.class);
    }

    /**
     * Returns the {@link ManagerManager} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code ManagerManager} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "managerManager/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public ManagerManager get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting ManagerManager with ID: " + id);
        ManagerManager managerManager = ofy().load().type(ManagerManager.class).id(id).now();
        if (managerManager == null) {
            throw new NotFoundException("Could not find ManagerManager with ID: " + id);
        }
        return managerManager;
    }

    @ApiMethod(
            name = "alreadyExists",
            path = "managerManager/exist",
            httpMethod = ApiMethod.HttpMethod.GET,
            clientIds = {
                    Ids.WEB_CLIENT_ID,
                    Ids.ANDROID_CLIENT_ID,
                    API_EXPLORER_CLIENT_ID },
            audiences = { Ids.ANDROID_AUDIENCE},
            scopes = {
                    "https://www.googleapis.com/auth/userinfo.email"})

    //<!---09june16_22:40
    //TODO_d+??: set this new parameter in method statement:  @Named ("regDeviceId") String regDeviceId
    public ManagerManager alreadyExists( User user, @Named ("regDeviceId") String regDeviceId ) throws UnauthorizedException, NotFoundException {
        //<---

        if (user == null)
            throw new UnauthorizedException("User is not valid!");


        String tempEmail = user.getEmail();

        ManagerManager manager = ofy().load().type(ManagerManager.class).filter("email", tempEmail).first().now();


        if (manager == null) {
            //throw new NotFoundException("Could not find ManagerManager with ID: " + id);

            Employee employee = ofy().load().type(Employee.class).filter("email", tempEmail).first().now();

            if (employee == null) {
                throw new NotFoundException("Could not 5nd  ManagerManager with ID: " );

            }


            //<!---09june16_22:40
            //TODO_d+??: check if employees's regDevId is the same employees's reDevId from base
            //TODO_d+??: change it if they are  different
            //TODO_aborted: send message (simple type for toast in client) to client about result: true or false  rewrite device

             if (regDeviceId !=  employee.getRegId()){

                 employee.setRegId(regDeviceId);

                 try {
                     ofy().load().type(Employee.class).id(employee.id).safe();
                 } catch (com.googlecode.objectify.NotFoundException e) {
                     throw new NotFoundException("Could not find employee with ID: " + employee.id);
                 }

                 ofy().save().entity(employee).now();

                 changeEmployeeRegIInRelationsGroup ( employee, regDeviceId );



             }



            //<---

            ManagerManager tempManager = new ManagerManager();
            tempManager.id = employee.id;
            tempManager.setIsManager(false);
            return tempManager;
        }


        //<!---09june16_22:40
        //TODO_d+??: check if manager's regDevId is the same manager's reDevId from base
        //TODO_d+??: change it in base if they are  different
        //TODO_aborted: send message (simple type for toast in client) to client about result: true or false  rewrite device

        if (regDeviceId !=  manager.getRegId()){


            Key <ManagerManager> managerKey = Key.create(ManagerManager.class, manager.getId());
            String managerEmail = manager.getEmail();

            manager.setRegId(regDeviceId);

            checkExists(manager.getId());
            ofy().save().entity( manager).now();
          //  employee.setEmail(regDeviceId);

           // changeEmployeeRegIInRelationsGroup ( employee, regDeviceId );


            //<!---
            //TODO_done+07/june/16: load manager's relation group
            //TODO_done+07/june/16/11:44 : get each employee from emailKeyMap list in rg
            //TODO_done+_-"-"-/11:18 set new manager's regId in all  employee's managerRegId fields

            RelationsGroup relationsGroup = ofy().load().type(RelationsGroup.class).ancestor(managerKey).first().now();

            Map <String, String> managerEmailRegId ;

            Map <String, Key <Employee>> emailKeyEmployeeMap = relationsGroup.getEmployeeEmailKeyMap();
            for ( Key <Employee> employeeKey : emailKeyEmployeeMap.values()  ){

                Employee employee =  ofy().load().key(employeeKey ).now();

                managerEmailRegId =  employee.getManagerEmailRegId();

                managerEmailRegId.put( managerEmail, regDeviceId  );

                try {
                    ofy().load().type(Employee.class).id(employee.id).safe();
                } catch (com.googlecode.objectify.NotFoundException e) {
                    throw new NotFoundException("Could not find employee with ID: " + employee.id);
                }

                ofy().save().entity(employee).now();

            }


        }





        //<---

        manager.setIsManager(true);
        return manager;

    }



    public void changeEmployeeRegIInRelationsGroup ( Employee employee,  @Named ("regDeviceId") String regDeviceId  ) throws NotFoundException {

        RelationsGroup relationsGroup;
        String employeeEmail = employee.getEmail();

        for (String managerEmail : employee.getManagerEmailRegId().keySet()){

            relationsGroup = ofy().load().type(RelationsGroup.class).filter("managerEmail", managerEmail).first().now();

            relationsGroup.getEmployeeList().put(employeeEmail, regDeviceId);

            //update relations group
            try {
                //ofy().load().type(RelationsGroup.class).id(relationsGroup.id).safe();
                ofy().load().type(RelationsGroup.class).filter("managerEmail", managerEmail).first().safe();

            } catch (com.googlecode.objectify.NotFoundException e) {
                throw new NotFoundException("Could not find relationGroup with ID: " + relationsGroup.id);
            }
            ofy().save().entity(relationsGroup).now();


        }

    }


    /**
     * Inserts a new {@code ManagerManager}.
     */
    @ApiMethod(
            name = "insert",
            path = "managerManager",
            httpMethod = ApiMethod.HttpMethod.POST,
            clientIds = {
            Ids.WEB_CLIENT_ID,
            Ids.ANDROID_CLIENT_ID,
            API_EXPLORER_CLIENT_ID },
            audiences = { Ids.ANDROID_AUDIENCE},
            scopes = {
                    "https://www.googleapis.com/auth/userinfo.email"})

    public ManagerManager insert(ManagerManager managerManager, User user) throws UnauthorizedException {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that managerManager.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.

        if (user == null)
            throw new UnauthorizedException("User is not valid!");

        //ManagerManager manager = new ManagerManager();
        managerManager.setEmail(user.getEmail());

        ofy().save().entity(managerManager).now();
        ofy().load().entity(managerManager).now();

        RelationsGroup relationsGroup = new RelationsGroup(managerManager.getId());
        //set his personal email
        relationsGroup.setManagerEmail(managerManager.getEmail());
        ofy().save().entity(relationsGroup).now();

        logger.info("Created ManagerManager with ID: " + managerManager.getId());

        return ofy().load().entity(managerManager).now();
    }

    /**
     * Updates an existing {@code ManagerManager}.
     *
     * @param id             the ID of the entity to be updated
     * @param managerManager the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code ManagerManager}
     */
    @ApiMethod(
            name = "update",
            path = "managerManager/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public ManagerManager update(@Named("id") Long id, ManagerManager managerManager) throws NotFoundException {
        // TODO_: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(managerManager).now();
        logger.info("Updated ManagerManager: " + managerManager);
        return ofy().load().entity(managerManager).now();
    }

    /**
     * Deletes the specified {@code ManagerManager}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code ManagerManager}
     */
    @ApiMethod(
            name = "remove",
            path = "managerManager/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(ManagerManager.class).id(id).now();
        logger.info("Deleted ManagerManager with ID: " + id);
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
            path = "managerManager",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<ManagerManager> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<ManagerManager> query = ofy().load().type(ManagerManager.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<ManagerManager> queryIterator = query.iterator();
        List<ManagerManager> managerManagerList = new ArrayList<ManagerManager>(limit);
        while (queryIterator.hasNext()) {
            managerManagerList.add(queryIterator.next());
        }
        return CollectionResponse.<ManagerManager>builder().setItems(managerManagerList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }



    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(ManagerManager.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find ManagerManager with ID: " + id);
        }
    }


    @ApiMethod(
            name = "confirm",
            path = "managerManager/confirm/{id}",
            httpMethod = ApiMethod.HttpMethod.POST)
    public void confirm (@Named("id") Long managerId, @Named ("confirmemails")List <String> confirmEmails) throws NotFoundException {

        String employeeRegId = "";
        Employee employee;
        Key<ManagerManager> managerKey = Key.create(ManagerManager.class, managerId);
        ManagerManager manager = ofy().load().key(managerKey).now();
        //String regId = manager.getRegId();
        RelationsGroup relationsGroup = ofy().load().type(RelationsGroup.class).ancestor(managerKey).first().now();
        //List<RelationsGroup> relationsGroups =  ofy().load().type(RelationsGroup.class).ancestor(Key.create(ManagerManager.class, managerId)).list();
        //RelationsGroup relationsGroup = relationsGroups.get(0);

        //get id of Employees
        //RelationsGroup relationsGroup1 = relationsGroupList.get(0);

        //get our map email-key (request list ) email-reg device id (employee list in group)
         Map< String, String > employeeListInGroup = relationsGroup.getEmployeeList();
        Map<String, Key<Employee>> requestEmployee =  relationsGroup.getEmployeeRequestsList();


        Map<String, Key<Employee>> employeeEmailKeyMapInGroup = relationsGroup.getEmployeeEmailKeyMap();




       // PersonRegIdInfo employeeInfo;
        for (String tempEmail : confirmEmails){
         Key <Employee> employeeKey =  requestEmployee.get(tempEmail);
            //put employees to selected list of employees
            //relationsGroup.getEmployeeList().put(tempEmail, employeeKey);
             employee = ofy().load().key(employeeKey).now();
            //set manager reg device id to employee list
            employee.getManagerEmailRegId().put(manager.getEmail(), manager.getRegId());
            //employeeListInGroup.put(tempEmail, new PersonRegIdInfo(employee.getRegId()));
            employeeListInGroup.put(tempEmail, employee.getRegId());




           //TODO_done08/june/2016/ save in  map key - employee  email  --- value employee Key in base server
            employeeEmailKeyMapInGroup.put(tempEmail, employeeKey  );


            //save list of employee in manager entity
          // manager.getEmployeeEmailKeyMap().put(tempEmail,employeeKey );



            employeeRegId = employee.getRegId();
            try {
                ofy().load().type(Employee.class).id(employee.id).safe();
            } catch (com.googlecode.objectify.NotFoundException e) {
                throw new NotFoundException("Could not find employee with ID: " + employee.id);
            }


            ofy().save().entity(employee).now();
        }
       // relationsGroup.getEmployeeRequestsList().clear();

        //and save relationGroupEntity again!
        //

        //<---TO_DO_LIST(DONE+) 23.05.16                        {+}done
        //change list of employee request
        //#Urgently
        //#matrix reload //#matrix reload //#matrix reload //#matrix reload
        //clear our request of employees
        relationsGroup.getEmployeeRequestsList().remove(confirmEmails.get(0));
       // relationsGroup.getEmployeeList().remove(confirmEmails.get(0));
       // <----


        try {
            //ofy().load().type(RelationsGroup.class).id(relationsGroup.id).safe();
            ofy().load().type(RelationsGroup.class).ancestor(managerKey).first().safe();

        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find relationGroup with ID: " + relationsGroup.id);
        }
        ofy().save().entity(relationsGroup).now();


        /*
        try {
            ofy().load().type(ManagerManager.class).id(manager.id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find manager with ID: " + manager.id);
        }
        ofy().save().entity(manager).now();*/



        //#matrix reload //#matrix reload //#matrix reload //#matrix reload

       // SendMessages (regId, "Checking employees have been added to your manager list of employees.");


        //<!---
        //TODO_done+01/07/16 send message to employee, you are confirmed
       // employee.getRegId();

        CreateMessage cm = new CreateMessage(manager.getEmail(),"Congratulations! Your request has been accepted", Ids.INFO_TO_EMPLOYEE_REQ );
        //your request was accepted
        //Your request has been denied
        try {
            SendMessages( employeeRegId,cm.returnMessage()  );
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        //<--
    }



    @ApiMethod(
            name = "getEmployeeRequest",
            path = "managerManager/confirm/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public ManagerManager getEmployeeRequest (@Named("id") Long managerId)  {


        String regId = ofy().load().key(Key.create(ManagerManager.class, managerId)).now().getRegId();


        List<RelationsGroup> relationsGroup =  ofy().load().type(RelationsGroup.class).ancestor(Key.create(ManagerManager.class, managerId)).list();
        RelationsGroup getRelatG = relationsGroup.get(0);


        ManagerManager container = new ManagerManager( );
            List <String> employeesEmails= container.getListEmails();

           for(String nextEmail:getRelatG.getEmployeeRequestsList().keySet()){
               employeesEmails.add(nextEmail);

               /*
               Sender sender = new Sender(API_KEY);
               Message msg = new Message.Builder().addData("message", nextEmail).build();
               try {
                   Result result = sender.send(msg,regId, 5);
               } catch (IOException e) {
                   e.printStackTrace();

               }*/

           }

       // ofy().save().entity(container);
       //container= ofy().load().entity(container).now();

        return container;
    }



    @ApiMethod(
            name = "chekingId",
            path = "managerManager/check/{id}",
            httpMethod = ApiMethod.HttpMethod.GET
    )

    public void checking (@Named("id") Long managerId ) throws UnauthorizedException{

      // user.



        ManagerManager manager = ofy().load().key(Key.create(ManagerManager.class, managerId)).now();

        String regId = manager.getRegId();



        StringBuilder sb = new StringBuilder();


        List<RelationsGroup> relationsGroup =  ofy().load().type(RelationsGroup.class).ancestor(Key.create(ManagerManager.class,managerId)).list();

        RelationsGroup getRelatGroup = relationsGroup.get(0);

        for(String nextEmail:getRelatGroup.getEmployeeList().keySet()){
            sb.append(nextEmail+";" +"\n");}


        //
        //sending message
        //
        String m = "My email: " +manager.getEmail()+"\n"+" My employees emails:"+sb;
        CreateMessage cm2 = new CreateMessage (m, "server");
        try {
            SendMessages(regId, cm2.returnMessage());
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        /*
        Sender sender = new Sender(API_KEY);
        Message msg = new Message.Builder().addData("message","My email: " +manager.getEmail()+"\n"+" My employees emails:"+sb).build();
        try {
            Result result = sender.send(msg,regId, 5);
        } catch (IOException e) {
            e.printStackTrace();

        }*/

    }



    @ApiMethod(
            name = "getManager",
            path = "managerManager/getManager/{id}",
            httpMethod = ApiMethod.HttpMethod.GET
    )

    public ManagerManager getManager (@Named("id") Long managerId ) throws UnauthorizedException{

        // user.



        //we will use manager object as a container to transport employees( which belong to the manager) emails
        //for bulding list of employees in manager cabinet
        ManagerManager manager = ofy().load().key(Key.create(ManagerManager.class, managerId)).now();

        List <String> listEmails =  manager.getListEmails();

        List<RelationsGroup> relationsGroup =  ofy().load().type(RelationsGroup.class).ancestor(Key.create(ManagerManager.class,managerId)).list();

        RelationsGroup getRelatGroup = relationsGroup.get(0);

        for(String nextEmail:getRelatGroup.getEmployeeList().keySet()){

            listEmails.add(nextEmail);
           // sb.append(nextEmail+";" +"\n");

        }

       // manager.setListEmails(listEmails);



       return manager;
       /*
        String regId = manager.getRegId();



        StringBuilder sb = new StringBuilder();


        List<RelationsGroup> relationsGroup =  ofy().load().type(RelationsGroup.class).ancestor(Key.create(ManagerManager.class,managerId)).list();

        RelationsGroup getRelatGroup = relationsGroup.get(0);

        for(String nextEmail:getRelatGroup.getEmployeeList().keySet()){
            sb.append(nextEmail+";" +"\n");}


        //
        //sending message
        //
        String m = "My email: " +manager.getEmail()+"\n"+" My employees emails:"+sb;
        CreateMessage cm2 = new CreateMessage (m, "server");
        try {
            SendMessages(regId, cm2.returnMessage());
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        /*
        Sender sender = new Sender(API_KEY);
        Message msg = new Message.Builder().addData("message","My email: " +manager.getEmail()+"\n"+" My employees emails:"+sb).build();
        try {
            Result result = sender.send(msg,regId, 5);
        } catch (IOException e) {
            e.printStackTrace();

        }
        */

    }


    @ApiMethod(
            name = "SPY",
            path = "managerManager/SPY/{id}",
            httpMethod = ApiMethod.HttpMethod.POST)

    public void spy (@Named("id") Long managerId, @Named("employeeEmails")List <String>employeeEmails, @Named("spymessage")String spyMessage) {

        //List<RelationsGroup> relationsGroup =  ofy().load().type(RelationsGroup.class).ancestor(Key.create(ManagerManager.class, managerId)).list();
        //RelationsGroup getRelatG = relationsGroup.get(0);

        Key<ManagerManager> managerKey = Key.create(ManagerManager.class, managerId);
       // ManagerManager manager = ofy().load().key(managerKey).now();
       // String regId = manager.getRegId();

        RelationsGroup relationsGroup = ofy().load().type(RelationsGroup.class).ancestor(managerKey).first().now();

        Map<String,String> employeeList = relationsGroup.getEmployeeList();

        for (String elementEmail : employeeEmails) {

           String regEmployeeId  = employeeList.get(elementEmail);

           // String employeeRegId = ofy().load().key(key).now().getRegId();
            try {
                SendMessages( regEmployeeId,spyMessage );
            } catch (NotFoundException e) {
                e.printStackTrace();
            }


        }

    }



    @ApiMethod(
            name = "giveEmployeetLocation",
            path = "managerManager/spy",
            httpMethod = ApiMethod.HttpMethod.GET)
    public void giveEmployeeLocation (@Named("id") Long managerId, @Named ("employeeEmail")String employeeEmail)  {

        Key<ManagerManager> managerKey = Key.create(ManagerManager.class, managerId);
        // ManagerManager manager = ofy().load().key(managerKey).now();
        // String regId = manager.getRegId();

        RelationsGroup relationsGroup = ofy().load().type(RelationsGroup.class).ancestor(managerKey).first().now();

        Map<String,String> employeeList = relationsGroup.getEmployeeList();
        String regEmployeeId  = employeeList.get(employeeEmail);

        //put number for create ask request message  to get location of employee
        CreateMessage cm = new CreateMessage("fromManager",Ids.GIVE_LOCATION);
        try {
            SendMessages( regEmployeeId,cm.returnMessage()  );
        } catch (NotFoundException e) {
            e.printStackTrace();
        }


    }


    private void SendMessages (  String regId, String msgString) throws NotFoundException {

        Sender sender = new Sender(API_KEY);
        Message msg = new Message.Builder().addData("message", msgString).build();
        try {
            Result result = sender.send(msg,regId, 5);
        } catch (IOException e) {
            e.printStackTrace();

        }

    }


    @ApiMethod(
            name = "deleteRequest",
            path = "managerManager/deleteRequest/{id}",
            httpMethod = ApiMethod.HttpMethod.POST)
    public void deleteRequest (@Named("id") Long managerId, @Named ("deleteEmail")String deleteEmail) throws NotFoundException {

        Employee employee;
        Key<ManagerManager> managerKey = Key.create(ManagerManager.class, managerId);
        ManagerManager manager = ofy().load().key(managerKey).now();
        //String regId = manager.getRegId();
        RelationsGroup relationsGroup = ofy().load().type(RelationsGroup.class).ancestor(managerKey).first().now();

        Map<String, Key<Employee>> requestEmployee =  relationsGroup.getEmployeeRequestsList();

       //get key for object employee
        Key <Employee> employeeKey =  requestEmployee.get(deleteEmail);

        employee = ofy().load().key(employeeKey).now();

        // PersonRegIdInfo employeeInfo;
        // Key <Employee> employeeKey =  requestEmployee.get(deleteEmail);
        //delete from requests list
        requestEmployee.remove(deleteEmail);
        ofy().save().entity(relationsGroup).now();


        //<!---
        //TODO_done+02/07/16 send message to employee, you are deleted from request

        CreateMessage cm = new CreateMessage(manager.getEmail(),"Sorry, but Your request has been has been denied", Ids.INFO_TO_EMPLOYEE_REQ );
        //your request was accepted
        //Your request has been denied
        try {
            SendMessages( employee.getRegId(),cm.returnMessage());
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        //<--

        }


    @ApiMethod(
            name = "setManagerRegId",
            path = "managerManager/setManagerRegId/{id}",
            httpMethod = ApiMethod.HttpMethod.POST
    )

    public void setManagerRegId(@Named("id") Long managerId, @Named("setRegId")String setRegId) throws UnauthorizedException, NotFoundException {

        // user.

        Map<String,String> mapEmployeeList;
        RelationsGroup manager_rg;
        String managerEmail;
        ManagerManager manager;
        Key managerKey= Key.create(ManagerManager.class, managerId);

        //we will use manager object as a container to transport employees( which belong to the manager) emails
        //for bulding list of employees in manager cabinet
         manager = (ManagerManager) ofy().load().key(managerKey).now();

        manager.setRegId(setRegId);


        ofy().save().entity( manager).now();

     //   Map <String, Key<Employee>> employeeEmailKeyMap = manager.getEmployeeEmailKeyMap();

        //List<RelationsGroup> results = ofy().load().type(RelationsGroup.class).ancestor( managerKey).list();
        //manager_rg = results.get(0);

        managerEmail = manager.getEmail();

        //<!---
        //TODO_done+07/june/16: load manager's relation group
        //TODO_done+07/june/16/11:44 : get each employee from emailKeyMap list in rg
        //TODO_done+_-"-"-/11:18 set new manager's regId in all  employee's managerRegId fields

        RelationsGroup relationsGroup = ofy().load().type(RelationsGroup.class).ancestor(managerKey).first().now();

        Map <String, String> managerEmailRegId ;

        Map <String, Key <Employee>> emailKeyEmployeeMap = relationsGroup.getEmployeeEmailKeyMap();
        for ( Key <Employee> employeeKey : emailKeyEmployeeMap.values()  ){

            Employee employee =  ofy().load().key(employeeKey ).now();

            managerEmailRegId =  employee.getManagerEmailRegId();

            managerEmailRegId.put( managerEmail, setRegId   );

            try {
                ofy().load().type(Employee.class).id(employee.id).safe();
            } catch (com.googlecode.objectify.NotFoundException e) {
                throw new NotFoundException("Could not find employee with ID: " + employee.id);
            }

            ofy().save().entity(employee).now();

        }

        //<--


        //get each manager's employee and set new managerRegId value in employee's managerId field

      /*
        for (Key<Employee> employeeKey  : employeeEmailKeyMap.values()) {

           Employee getEmployee =  ofy().load().key(employeeKey).now();
            getEmployee.getManagerEmailRegId().put(managerEmail, setRegId );
            ofy().save().entity( getEmployee).now();
        }*/

        }


    @ApiMethod(
            name = "workAroundFillList",
            path = "managerManager/workAroundFiilList/{id}",
            httpMethod = ApiMethod.HttpMethod.POST)

    public void workAroundFillList (@Named("id") Long managerId) throws NotFoundException {

        // user.



        Key managerKey= Key.create(ManagerManager.class, managerId);

        //we will use manager object as a container to transport employees( which belong to the manager) emails
        //for bulding list of employees in manager cabinet
        //manager = (ManagerManager) ofy().load().key(managerKey).now();



        RelationsGroup relationsGroup = ofy().load().type(RelationsGroup.class).ancestor(managerKey).first().now();

        relationsGroup.getEmployeeList().clear();

        //uptade relation group
        try {
            //ofy().load().type(RelationsGroup.class).id(relationsGroup.id).safe();
            ofy().load().type(RelationsGroup.class).ancestor(managerKey).first().safe();

        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find relationGroup with ID: " + relationsGroup.id);
        }
        ofy().save().entity(relationsGroup).now();




    }



    //<!---
    //TODO_done+08/june/16/14:40: delete from list of manager
    //TODO_done+08/june/16/14:40: method get email employee's ;
    // TODO_done+08/june/16/14:40: delete from relation group ; delete manager email from employee's field
    //TODO_done+08/june/16/14:40: send notification to employee id info to employee



    @ApiMethod(
            name = "deleteEFromFriend",
            path = "managerManager/deleteEFromFriend/{id}",
            httpMethod = ApiMethod.HttpMethod.POST
    )

    public void deleteEFromFriend(@Named("id") Long managerId, @Named("employeeEmail")String employeeEmail) throws UnauthorizedException, NotFoundException {

        // user.

        String managerEmail;
        ManagerManager manager;
        Key managerKey= Key.create(ManagerManager.class, managerId);

        //we will use manager object as a container to transport employees( which belong to the manager) emails
        //for bulding list of employees in manager cabinet
        manager = (ManagerManager) ofy().load().key(managerKey).now();




        ofy().save().entity( manager).now();

        //   Map <String, Key<Employee>> employeeEmailKeyMap = manager.getEmployeeEmailKeyMap();

        //List<RelationsGroup> results = ofy().load().type(RelationsGroup.class).ancestor( managerKey).list();
        //manager_rg = results.get(0);

        managerEmail = manager.getEmail();


        //Employee employee =  ofy().load().key(employeeKey ).now();

        //<!---
        //TODO_done+07/june/16: load manager's relation group
        //TODO_done+07/june/16/11:44 : get each employee from emailKeyMap list in rg
        //TODO_done+_-"-"-/11:18 set new manager's regId in all  employee's managerRegId fields

        RelationsGroup relationsGroup = ofy().load().type(RelationsGroup.class).ancestor(managerKey).first().now();

        //get employee key

       Key <Employee> employeeKey = relationsGroup.getEmployeeEmailKeyMap().get( employeeEmail );
        Employee employee =  ofy().load().key(employeeKey ).now();


        //deleting all info
        employee.getManagerEmailRegId().remove(managerEmail);
        relationsGroup.getEmployeeEmailKeyMap().remove(employeeEmail);
        relationsGroup.getEmployeeList().remove(employeeEmail);
      // manager.getListEmails().remove(employeeEmail);







        //update employee
        try {
            ofy().load().type(Employee.class).id(employee.id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find employee with ID: " + employee.id);
        }

        ofy().save().entity(employee).now();


        //uptade relation group
        try {
            //ofy().load().type(RelationsGroup.class).id(relationsGroup.id).safe();
            ofy().load().type(RelationsGroup.class).ancestor(managerKey).first().safe();

        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find relationGroup with ID: " + relationsGroup.id);
        }
        ofy().save().entity(relationsGroup).now();






        CreateMessage cm = new CreateMessage(manager.getEmail(),"Sorry. ): But you manager deleted you from his care", Ids.INFO_TO_EMPLOYEE_REQ );
        //your request was accepted
        //Your request has been denied
        try {
            SendMessages(  employee.getRegId(),cm.returnMessage()  );
        } catch (NotFoundException e) {
            e.printStackTrace();
        }


    }


     //<!---10june16_15:50
    //TODO_d+?? : make deleteListEFromFriend  request
    @ApiMethod(
            name = "deleteListEFromFriend",
            path = "managerManager/deleteListEFromFriend/{id}",
            httpMethod = ApiMethod.HttpMethod.POST
    )

    public void deleteListEFromFriend(@Named("id") Long managerId, @Named("employeeListEmails")ArrayList<String> employeeListEmails) throws UnauthorizedException, NotFoundException {

        // user.
        String managerEmail;
        ManagerManager manager;
        Key managerKey= Key.create(ManagerManager.class, managerId);
        //we will use manager object as a container to transport employees( which belong to the manager) emails
        //for bulding list of employees in manager cabinet
        manager = (ManagerManager) ofy().load().key(managerKey).now();

        ofy().save().entity( manager).now();
        managerEmail = manager.getEmail();

        //<!---
        //TODO_done+07/june/16: load manager's relation group
        //TODO_done+07/june/16/11:44 : get each employee from emailKeyMap list in rg
        //TODO_done+_-"-"-/11:18 set new manager's regId in all  employee's managerRegId fields

        RelationsGroup relationsGroup = ofy().load().type(RelationsGroup.class).ancestor(managerKey).first().now();

        //get employee key

        for (String employeeEmail : employeeListEmails){

            Key <Employee> employeeKey = relationsGroup.getEmployeeEmailKeyMap().get( employeeEmail );
            Employee employee =  ofy().load().key(employeeKey ).now();

            //deleting all info
            employee.getManagerEmailRegId().remove(managerEmail);
            relationsGroup.getEmployeeEmailKeyMap().remove(employeeEmail);
            relationsGroup.getEmployeeList().remove(employeeEmail);
            // manager.getListEmails().remove(employeeEmail);

            //update employee
            try {
                ofy().load().type(Employee.class).id(employee.id).safe();
            } catch (com.googlecode.objectify.NotFoundException e) {
                throw new NotFoundException("Could not find employee with ID: " + employee.id);
            }

            ofy().save().entity(employee).now();


            CreateMessage cm = new CreateMessage(manager.getEmail(),"Sorry. ): But you manager deleted you from his care", Ids.INFO_TO_EMPLOYEE_REQ );
            //your request was accepted
            //Your request has been denied
            try {
                SendMessages(  employee.getRegId(),cm.returnMessage()  );
            } catch (NotFoundException e) {
                e.printStackTrace();
            }

        }

        //uptade relation group
        try {
            //ofy().load().type(RelationsGroup.class).id(relationsGroup.id).safe();
            ofy().load().type(RelationsGroup.class).ancestor(managerKey).first().safe();

        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find relationGroup with ID: " + relationsGroup.id);
        }
        ofy().save().entity(relationsGroup).now();


    }

 //<--


    //<!---18^00 17june16
    //TODO_d+:1------
    //TODO_d+18min: stop spy request to employee just push notification message with kind id - STOP_SPY

    @ApiMethod(
            name = "stopSpy",
            path = "managerManager/stopSpy{id}",
            httpMethod = ApiMethod.HttpMethod.POST)
    public void stopSpy (@Named("id") Long managerId, @Named ("email")String employeeEmail) throws NotFoundException {



        Employee employee = ofy().load().type(Employee.class).filter("email", employeeEmail).first().now();



        CreateMessage cm = new CreateMessage("matrix_exist","matrix_exist", Ids.STOP_SPY );
        //your request was accepted
        //Your request has been denied
        try {
            SendMessages( employee.getRegId(),cm.returnMessage()  );
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        //<--
    }





    //-->
}












