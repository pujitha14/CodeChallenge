package com.wipro.digital;

import java.text.DecimalFormat;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Matchers.anyFloat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCases {

	RestAPI resapi;
	JSONParser jsonparser;

	@Autowired
    private WebApplicationContext wac;
	private MockMvc mockMvc;
	@Before
	public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

	}

	@Test
	public void product_tc1() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/rest/product?number1=2f&number2=1f").accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(2))
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
            .andDo(print());
	}
	
	@Test
	public void product_tc2() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/rest/product?number1=2f").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.error_code").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Required float parameter 'number2' is not present"))
        .andDo(print());
	}
	
	@Test
	public void fileContent_tc1() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/rest/filecontent?filePath=C:/Users/PU343104/Desktop/123.txt").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(123))
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
        .andDo(print());
	}
	
	@Test
	public void fileContent_tc2() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/rest/filecontent?filePath=C:/Users/PU343104/Desktop/1233.txt").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.error_code").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("class java.nio.file.NoSuchFileException"))
        .andDo(print());
	}
	
	@Test
	public void writetofile_tc1() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/rest/writetofile?fileContent=abcdefghijklmnop").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").value("abcdefghijklmnop"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
        .andDo(print());
	}
	
	@Test
	public void nonrepeatingchar_tc1() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/rest/nonrepeatingchar?word=qqjjaf").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").value("a"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
        .andDo(print());
	}
	@Test
	public void nonrepeatingchar_tc2() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/rest/nonrepeatingchar?word=qjjqafaf").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").value("No non-repeating character"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
        .andDo(print());
	}
	
	@Test
	public void webcrawler_tc1() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/rest/webcrawler?url=google").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.error_code").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("class java.lang.IllegalArgumentException"))
        .andDo(print());
	}
	
}
