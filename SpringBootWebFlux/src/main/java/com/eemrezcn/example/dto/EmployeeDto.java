package com.eemrezcn.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter /*These Annotations, Which Come From The Lombok Library, Automatically Add Setter And Getter Methods To The Class.*/
@Setter /*These Annotations, Which Come From The Lombok Library, Automatically Add Setter And Getter Methods To The Class.*/
@NoArgsConstructor /*It Is An Annotation From The Lombok Library And Adds A Parameterless Constructor (Constructor Method) To A Class.*/
@AllArgsConstructor /*It Is An Annotation That Comes From The Lombok Library And Adds A Constructor (Constructor Method) To A Class That Includes All Fields.*/
public class EmployeeDto {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
}
