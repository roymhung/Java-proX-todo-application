package vn.proX.todoapplication.controller;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.proX.todoapplication.entity.User;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void createUser_ShouldReturnUser_whenValid() throws Exception {

        // arrange
        User inputUser = new User(null, "John Doe", UUID.randomUUID() + "@example.com");

        // act
        String resultStr = mockMvc
                .perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputUser)))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(inputUser.getName()))
                .andExpect(jsonPath("$.email").value(inputUser.getEmail())).andReturn()
                .getResponse().getContentAsString();

        // assert
        System.out.println("resultStr = " + resultStr);
        User outputUser = objectMapper.readValue(resultStr, User.class);

        assertEquals(inputUser.getName(), outputUser.getName());
        assertEquals(inputUser.getEmail(), outputUser.getEmail());
    }
}
