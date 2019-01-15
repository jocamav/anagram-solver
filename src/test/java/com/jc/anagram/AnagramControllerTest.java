package com.jc.anagram;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.jc.anagram.service.AnagramService;
import com.jc.anagram.service.DictionaryService;
import com.jc.anagram.web.AnagramController;

import org.springframework.restdocs.JUnitRestDocumentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.util.Arrays;
import java.util.HashSet;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AnagramController.class)
public class AnagramControllerTest {
    private MockMvc mockMvc;
    
    @MockBean
    private AnagramService anagramService;
    
    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();
    
    @Before
    public void setup() throws Exception {
        //this.mvc  = webAppContextSetup(webApplicationContext).build();
        this.mockMvc = webAppContextSetup(this.webApplicationContext)
    			.apply(documentationConfiguration(this.restDocumentation)) 
    			.build();
		String[] anagramsOfWord = {
				"cod writ",
				"cord wit",
				"cow dirt",
				"doc writ",
				"tic word"
		};
		HashSet<String> anagrams = new HashSet<String>(Arrays.asList(anagramsOfWord));
		given(anagramService.getAllAnagramsPhrases("IT-Crowd")).willReturn(anagrams);
    }

    @Test
    public void getAnagramsWithoutWord() throws Exception {
        this.mockMvc.perform(get("/api/anagrams").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void getAnagrams() throws Exception {
        this.mockMvc.perform(get("/api/anagrams?word=IT-Crowd").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("anagrams/{methodName}",
                		requestParameters( 
            					parameterWithName("word").description("Word to find all the anagrams")
            			), 
                		responseFields(
        						fieldWithPath("[]").description("An array of Anagrams of the Word"))
                		));
    }
}
