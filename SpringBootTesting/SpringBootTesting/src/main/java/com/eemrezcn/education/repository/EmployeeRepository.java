package com.eemrezcn.education.repository;

import com.eemrezcn.education.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /*"This Method Defines A Custom Spring Data Jpa Query That Searches For A Record In The Employee Table Based On An Email,
    And If Found, Returns That Record Wrapped In An Optional."*/
    Optional<Employee> findByEmail(String email);


    /*This Method Defines A Custom Jpql Query That Searches For A Record In The 'employee' Table Based On Specified Values For First Name And Last Name,
    And Returns The Corresponding 'employee' Object If Found.*/
    @Query("select e from Employee e where e.firstName = ?1 and e.lastName = ?2")
    Employee findByJPQL(String firstName, String lastName);

    /*This Java Spring Data Jpa Code Uses A Jpql (Java Persistence Query Language) Query To Retrieve An Employee In The 'employee' Class With A Specific
    First Name And Last Name, Taking These Parameters As Named Parameters*/
    @Query("select e from Employee e where e.firstName =:firstName and e.lastName =:lastName")
    Employee findByJPQLNamedParams(@Param("firstName") String firstName, @Param("lastName") String lastName);

    /*This Method Defines A Custom Spring Data Jpa Query That Searches For A Record In The 'employees' Table Based On Specified Values For
    The First Name And Last Name Using A Direct Sql Query, And Returns The Corresponding 'employee' Object If Found.*/
    @Query(value = "select * from employees e where e.first_name =?1 and e.last_name =?2", nativeQuery = true)
    Employee findByNativeSQL(String firstName, String lastName);

    /*This Method Defines A Custom Spring Data Jpa Query That Searches For A Record In The 'employees' Table Based On Specified Values For
    The First Name And Last Name Using A Direct Sql Query, And Returns The Corresponding 'employee' Object If Found;
    Parameters In This Query Are Used With Named Placeholders.*/
    @Query(value = "select * from employees e where e.first_name =:firstName and e.last_name =:lastName", nativeQuery = true)
    Employee findByNativeSQLNamed(@Param("firstName") String firstName, @Param("lastName") String lastName);

}
