package com.eemrezcn.example;

import org.testcontainers.containers.MySQLContainer;

/*This Code Represents A Class Named Abstractcontainerbasetest. Inside This Class, There Is A Code Block That Creates And Starts
A Mysql Docker Container For Use In Tests. This Enables The Tests To Run Using A Real Mysql Database.*/
public class AbstractContainerBaseTest
{
    static final MySQLContainer MY_SQL_CONTAINER;

    static
    {
        MY_SQL_CONTAINER = new MySQLContainer("mysql:latest");

        MY_SQL_CONTAINER.start();
    }
}
