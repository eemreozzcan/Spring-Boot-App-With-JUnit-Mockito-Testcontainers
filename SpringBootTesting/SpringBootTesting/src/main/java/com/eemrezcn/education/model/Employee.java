package com.eemrezcn.education.model;

import jakarta.persistence.*;
import lombok.*;

@Setter/*These Annotations, Which Come From The Lombok Library, Automatically Add Setter And Getter Methods To The Class.*/
@Getter /*These Annotations, Which Come From The Lombok Library, Automatically Add Setter And Getter Methods To The Class.*/
@AllArgsConstructor /*It Is An Annotation That Comes From The Lombok Library And Adds A Constructor (Constructor Method) To A Class That Includes All Fields.*/
@NoArgsConstructor /*It Is An Annotation From The Lombok Library And Adds A Parameterless Constructor (Constructor Method) To A Class.*/
@Builder /*It Is An Annotation From The Lombok Library And Is Used Specifically To Simplify The Process Of Object Creation*/
@Entity /*This Annotation Signifies That The Class Corresponds To A Database Table And That Objects Of This Class Can Be Stored In The Database*/
@Table(name = "employees") /*It Is A Java Persistence Api (Jpa) Annotation That Specifies Which Table In The Database Corresponds To A Class.*/
public class Employee
{
    /*The Id Field Is Used As A Column Representing The Primary Key In The Database,
    And A New Primary Key Value Is Automatically Assigned Each Time A New Record Is Added*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*This Annotation Allows Mapping A Field To A Database Column. The 'name' Parameter Specifies The Name Of The Column In The Corresponding Database Table.
    In This Example, The 'firstname' Field In The Java Class Is Mapped To The 'first_name' Column In The Database.
    The 'nullable' Parameter Indicates Whether This Column Can Be Empty (Null) In The Database.
    A Value Of 'false' Signifies That The 'first_name' Column Cannot Be Empty.*/
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /*"@column(Name = "Last_name", Nullable = False): This Annotation Facilitates The Mapping Of A Field To A Database Column. The 'name' Parameter Specifies
    The Name Of The Column In The Relevant Database Table. In This Example, The 'lastname' Field In The Java Class Is Mapped To The 'last_name' Column In The Database.
    The 'nullable' Parameter Indicates Whether This Column Can Be Empty (Null) In The Database.
    A Value Of 'false' Signifies That The 'last_name' Column Cannot Be Empty."*/
    @Column(name = "last_name", nullable = false)
    private String lastName;

    /*"@column(Nullable = False): This Annotation Facilitates The Mapping Of A Field To A Database Column. The 'nullable' Parameter Specifies Whether
    The Corresponding Column In The Relevant Database Table Can Be Empty (Null). In This Example, The 'email' Field In The Java Class Is Mapped To
    A Column In The Database, And It Is Specified That This Column Cannot Be Empty."*/
    @Column(nullable = false)
    private String email;
}
