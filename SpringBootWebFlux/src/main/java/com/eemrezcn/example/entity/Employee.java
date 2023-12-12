package com.eemrezcn.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter /*These Annotations, Which Come From The Lombok Library, Automatically Add Setter And Getter Methods To The Class.*/
@Setter /*These Annotations, Which Come From The Lombok Library, Automatically Add Setter And Getter Methods To The Class.*/
@NoArgsConstructor /*It Is An Annotation From The Lombok Library And Adds A Parameterless Constructor (Constructor Method) To A Class.*/
@AllArgsConstructor /*It Is An Annotation That Comes From The Lombok Library And Adds A Constructor (Constructor Method) To A Class That Includes All Fields.*/
@Document(collection = "employees") /*It indicates that a Java class developed using Spring Data MongoDB represents a specific collection in the MongoDB database.*/
public class Employee
{
    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String email;

}
