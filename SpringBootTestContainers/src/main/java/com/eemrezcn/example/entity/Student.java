package com.eemrezcn.example.entity;


import jakarta.persistence.*;
import lombok.*;

/*This Class Typically Represents Student Records In A Database Table. Each Field Corresponds To A Column In The Database Table,
And This Class Is Designed To Perform Database Operations Using The Features Provided By Jpa*/

@Setter /*These Annotations, Which Come From The Lombok Library, Automatically Add Setter And Getter Methods To The Class.*/
@Getter /*These Annotations, Which Come From The Lombok Library, Automatically Add Setter And Getter Methods To The Class.*/
@Builder /*It Is An Annotation From The Lombok Library And Is Used Specifically To Simplify The Process Of Object Creation*/
@NoArgsConstructor /*It Is An Annotation From The Lombok Library And Adds A Parameterless Constructor (Constructor Method) To A Class.*/
@AllArgsConstructor /*It Is An Annotation That Comes From The Lombok Library And Adds A Constructor (Constructor Method) To A Class That Includes All Fields.*/
@Entity /*This Annotation Signifies That The Class Corresponds To A Database Table And That Objects Of This Class Can Be Stored In The Database*/
@Table(name = "students") /*It Is A Java Persistence Api (Jpa) Annotation That Specifies Which Table In The Database Corresponds To A Class.*/

public class Student
{
    /*The Id Field Is Used As A Column Representing The Primary Key In The Database,
    And A New Primary Key Value Is Automatically Assigned Each Time A New Record Is Added*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    /*It Will Map The Firstname Field In The Java Class To A Database Column Named "Firstname"
    And Enable Operations On The Corresponding Column In The Database.*/
    @Column(name = "firstName")
    public String firstName;

    /*Jpa Will Map The Lastname Field In The Java Class To A Database Column Named "Lastname"
    And Enable Operations On The Corresponding Column In The Database*/
    @Column(name = "lastName")
    public String lastName;

    public String email;
}
