package com.eemrezcn.example;

import com.eemrezcn.example.entity.Student;
import com.eemrezcn.example.repository.StudentRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.xml.transform.Result;
import java.util.List;

@SpringBootTest //This Annotation Enables The Loading And Testing Of A Spring Boot Application With A Complete Application Context.

//This annotation enables the automatic configuration of Spring MVC (Model-View-Controller) based applications and allows testing of
// HTTP requests using the MockMvc class."
@AutoConfigureMockMvc
@Testcontainers //This Annotation Enables The Usage Of The Testcontainers Library Within Junit Tests.
class SpringBootTestContainersApplicationTests extends AbstractContainerBaseTest{

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private MockMvc mockMvc;


	/*This Code Represents A Junit Test Method For Testing An Endpoint In A Spring Boot Application's Controller That Retrieves Student Records.*/
	@Test
	public void givenStudents_whenGetAllStudents_thenListOfStudents() throws Exception {

		//A List Named 'students' Is Created Containing Two Student Objects.
		List<Student> students = List.of(
				Student.builder().firstName("Emre1").lastName("Ozcan1").email("eozcan1@hotmail.com").build(),
				Student.builder().firstName("Ali1").lastName("Ozcan1").email("aozcan1@hotmail.com").build());

		//The Created List Of Students Is Saved To The Database Using studentrepository.saveall(students).
		studentRepository.saveAll(students);
		//The Variable expectedSize Is Created, And This Variable Represents The Total Number Of Students In The Database.
		int expectedSize = studentRepository.findAll().size();

		//when-action
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/students"));

		//then-verify the output
		//To Ensure That The Request Is Successfully Completed (Http 200 Ok), response.andexpect(Mockmvcresultmatchers.status().isok()) Is Used.
		response.andExpect(MockMvcResultMatchers.status().isOk());

		//"To Verify Whether The Desired Number Of Students Is Accurately Reflected In The Json Response,
		// response.andexpect(Mockmvcresultmatchers.jsonpath("$.size()", Corematchers.is(Expectedsize))) Is Used."
		response.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(expectedSize)));
	}

}
