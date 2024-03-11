Complaint Management System:

The complaint management system provides students with a safe space to express their concerns without the fear of consequences.it is a technique for assessing,analyzing and responding to student complaints and to facilitate any other feedback.The final outcome of this project is an effective and user-friendly web-bases student complaint management software that meets its objectives and provides an efficient solution to the challenges faced by the students.

It uses com.mysql.jdbc.driverclass for the establish the connection with the database
Uses notepad/Visual Studio Code for writing the java code.

Database:complaint_db 
It contains three tables admin,user,complaints
Admin table:admin id,username,password
User:user id,username,password
complaints:complaint id,description,status and user_id as the foreign key.

Basically three functions are performed in these project insertion,viewing and updation of complaints.

Through these platform user or students can register,login into the platform using username and password and can submit their complaints,also they can view their complaints.

In complaint table admin can view the complaints submitted by the students.

The admin can register and login to the platform same like the students through a username and password.The admin can update the status of the complaint through updatestatus function and can view the updated status. 
