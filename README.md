# **Guestbook**

Welcome to the guest book wiki!

* This program is developed on spring MVC and Thymeleaf. 
* Application can be accessed locally via https://localhost:8443/
* This service enables guests to make entry in the guest book and admin to approve or reject the enties.

Guest :

* On login user will land on home page where user can see user name and role assigned 
* To make an entry in a guest book user have to click on Make an entry button next to home button.
* User will be landed on entry dashboard where user can either add text or upload image of type jpg, jpeg or png.
* On click of Add entry Data will be saved to mySQL db
    * Image will be store on imgur site and image URL will be add in the db.

Admin :

* User with ADMIN role can access the admin dashboard where all the non rejected/deleted enties will be listed
* Admin can review, edit, approve or reject/delete the entry


Page view :

* Welcome page :
![image](https://user-images.githubusercontent.com/27283103/163859908-bc107d19-1f4a-4ce3-999d-2d46f3d66b64.png)

* Login page :
![image](https://user-images.githubusercontent.com/27283103/163860190-271e95d4-63f8-44ba-9c12-13e1c3ba0368.png)

* Home page :
![image](https://user-images.githubusercontent.com/27283103/163860280-9f014f4f-769d-4fee-9929-d0e9081b95f7.png)

* Make an entry page :
![image](https://user-images.githubusercontent.com/27283103/163860348-56ae3b53-9e9e-44fb-b038-27d193844195.png)

* Admin Dashboard :
![image](https://user-images.githubusercontent.com/27283103/163860501-02b2e9fd-bc30-4bcd-a8b2-73aeffe44bef.png)

Scope to improvement:
* Swagger Integration
* More validations
* UI/UX improvement

