# Care4U
Family GPS locator Kid Control. This is android Client-Server app. App uses Google Maps, push – notifications, authorization OAuth 2.0. 

## Download
[APK from Github](https://github.com/SergeyBurlaka/Care4UApp/blob/master/APK/Care4U.apk)  

## Was done
### Registration:
Just make sign in for Google+ to begin using Care4You app. It works because of OAuth2.0  and Google+API. You can sign up as manager or employee.

### Relationship request:
Inputting manager email and clicking “Get Hired” employee sends a relationship request to manager. Manager gets request from employee and add it to his relationship.

### Manager working space:
Manager can see employees in his contact list. List is implemented [Recycler View List] (https://developer.android.com/training/material/lists-cards.html).

<img src="https://cloud.githubusercontent.com/assets/21062067/17781771/4f5a5eb8-6579-11e6-9c42-2ab4f3b368f2.png" width="250">
<img src="https://cloud.githubusercontent.com/assets/21062067/17880935/81f4ae80-6909-11e6-843e-dbb6004ea99a.jpg" width="250">
<img src="https://cloud.githubusercontent.com/assets/21062067/17784579/afff230a-6585-11e6-814c-b61133982493.jpg" width="250"> 

 Manager opens map to get location for each employee by clicking employee name in app contact list. How it works? To get employee location request goes to server and forwards to employee client. App gets employee location in telephone via [Fused Location Provider Api Google service] (https://developers.google.com/android/reference/com/google/android/gms/location/FusedLocationProviderApi). In the end manager gets employee location in push – notification, because of [Google Cloud Messaging service] (https://developers.google.com/cloud-messaging/). 

### Looking for employee with push-notification feature:
Manager can see help tips show in first time using map, because of [Showcase View Library] (https://github.com/amlcurran/ShowcaseView).
Manager adds a circular area around employee's location on map; clicks to start. And app became to monitor the employee. 

<img src="https://cloud.githubusercontent.com/assets/21062067/17880894/21e59dd8-6909-11e6-875d-6f6bc8ea29d8.jpg" width="250">
<img src="https://cloud.githubusercontent.com/assets/21062067/17783801/52ef9d78-6582-11e6-880b-ae11ea062a0e.jpg" width="250">
<img src="https://cloud.githubusercontent.com/assets/21062067/17784109/e5775284-6583-11e6-89a7-84be54d7ac5f.jpg" width="250">

<img src="https://cloud.githubusercontent.com/assets/21062067/17784962/6d716aaa-6587-11e6-8c95-f5e2c6efa74e.jpg" >

As soon as employee leaved the circular area, manager had given notification: "Alarm! Employee leaved the circular area. He isn't in Safe Zone, now!” Useful feature works, because of GPS and Google Cloud Messaging service. 

###  What is left to do?
Activity for employee needs to modify. To add alarm “SOS” button, for getting worry notifications from employee in case of emergencies. Due button employee will be able to make cell to manager. 

Employee can’t to add more than one manager! Otherwise app will work incorrect. Left to solve this problem.

To add: manager monitoring of employee internet connection; original sounds for push- notifications; alert dialogs for showing app exceptions. 


