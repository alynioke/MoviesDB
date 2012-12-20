To start using the web-project you need:
 * to have a MySQL dabatase
 * to change settings in src/main/java/hibernate.cfg.xml:
    * for property "connection.username" set a login to your database
    * for property "connection.password" set a password to your database
    * for property "connection.url" set the URL of the database. E.g. "jdbc:mysql://localhost/mydatabase"
    * if it is the first time you are using this web-application you need to create tables - change the property "hbm2ddl.auto" to "create" and run the application. After the homepage is loaded change the property "hbm2ddl.auto" back to "verify".


After these actions you will have:
 * required tables 
 * user "admin" with password "admin"
 * movies list to browse  


Implemented:
 * Main page with static text
 * Movies list: 
    * paging
    * sorting by year
    * sorting by title 
 * Movie preview:
    * movie details
    * rating (for logged in users only)
 * Login


Persistence framework - Hibernate
Web framework - Wicket
Database - MySQL
Unit testing with JUnit and Mockito help
