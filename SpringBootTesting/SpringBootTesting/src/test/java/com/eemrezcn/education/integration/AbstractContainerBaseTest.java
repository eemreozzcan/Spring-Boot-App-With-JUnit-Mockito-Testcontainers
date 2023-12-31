package com.eemrezcn.education.integration;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

/*This AbstractContainerBaseTest class contains the necessary configuration for tests to run in a MySQL Docker container.
The class creates a static MySQLContainer object, starts this container, and uses DynamicPropertySource to dynamically provide the database
 connection information for the Spring Boot application. This allows tests to run in a Docker container instead of a real MySQL database.*/
public abstract class AbstractContainerBaseTest
{
    static final MySQLContainer MY_SQL_CONTAINER;

    static {
        MY_SQL_CONTAINER=new MySQLContainer("mysql:latest")
                .withUsername("root")
                .withPassword("Eo198957")
                .withDatabaseName("ems");

        MY_SQL_CONTAINER.start();
    }

    @DynamicPropertySource
    public static void dynamicPropertySource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", MY_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", MY_SQL_CONTAINER::getPassword);
    }


}
