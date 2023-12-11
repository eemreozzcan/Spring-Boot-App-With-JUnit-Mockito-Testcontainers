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

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class SpringBootTestContainersApplicationTests extends AbstractContainerBaseTest{

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void givenStudents_whenGetAllStudents_thenListOfStudents() throws Exception {
		List<Student> students = List.of(
				Student.builder().firstName("Emre1").lastName("Ozcan1").email("eozcan1@hotmail.com").build(),
				Student.builder().firstName("Ali1").lastName("Ozcan1").email("aozcan1@hotmail.com").build());

		studentRepository.saveAll(students);
		int expectedSize = studentRepository.findAll().size();

		//when-action
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/students"));

		//then-verify the output
		response.andExpect(MockMvcResultMatchers.status().isOk());
		response.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(expectedSize)));
	}

}
