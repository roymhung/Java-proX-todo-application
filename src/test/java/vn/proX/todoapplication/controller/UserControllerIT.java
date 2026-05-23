package vn.proX.todoapplication.controller;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.proX.todoapplication.IntegrationTest;
import vn.proX.todoapplication.entity.User;
import vn.proX.todoapplication.repository.UserRepository;

@IntegrationTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerIT {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private ObjectMapper objectMapper;

        @BeforeEach
        public void init() {
                this.userRepository.deleteAll();
        }

        // ======== @PostMapping("/users") ========
        @Test
        public void createUser_ShouldReturnUser_whenValid() throws Exception {

                // arrange
                User inputUser = new User(null, "John Doe", UUID.randomUUID() + "@example.com");

                // act
                String resultStr = mockMvc
                                .perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper
                                                                .writeValueAsString(inputUser)))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.id").isNumber())
                                .andExpect(jsonPath("$.name").value(inputUser.getName()))
                                .andExpect(jsonPath("$.email").value(inputUser.getEmail()))
                                .andReturn().getResponse().getContentAsString();

                // assert
                System.out.println("resultStr = " + resultStr);
                User outputUser = objectMapper.readValue(resultStr, User.class);

                assertEquals(inputUser.getName(), outputUser.getName());
                assertEquals(inputUser.getEmail(), outputUser.getEmail());
        }

        // ======== @GetMapping("/users") ========

        @Test
        public void getAllUsers() throws Exception {
                // arrange
                User user1 = new User(null, "Alice", UUID.randomUUID() + "@example.com");
                User user2 = new User(null, "Bob", UUID.randomUUID() + "@example.com");

                List<User> users = List.of(user1, user2);

                this.userRepository.saveAll(users);

                // action assert
                String resultStr = this.mockMvc.perform(get("/users")).andExpect(status().isOk())
                                .andReturn().getResponse().getContentAsString();

                List<User> resultUsers = objectMapper.readValue(resultStr,
                                new TypeReference<List<User>>() {});
                // assert
                assertEquals(2, resultUsers.size());
                assertEquals("Alice", resultUsers.get(0).getName());
        }

        // ======== @GetMapping("/users/{id}") ========
        @Test
        public void getUserById() throws Exception {
                // arrange
                User user = new User(null, "Charlie", UUID.randomUUID() + "@example.com");
                User savedUser = this.userRepository.saveAndFlush(user);

                // action
                String resultStr = this.mockMvc.perform(get("/users/{id}", savedUser.getId()))
                                .andExpect(status().isOk()).andReturn().getResponse()
                                .getContentAsString();

                User resultUserOutput = objectMapper.readValue(resultStr, User.class);
                // assert
                assertEquals(savedUser.getId(), resultUserOutput.getId());
                assertEquals(savedUser.getName(), resultUserOutput.getName());
        }

        // ======== @PutMapping("/users/{id}") ========
        @Test
        public void updateUser() throws Exception {

                // arrange

                User user = new User(null, "old-name", "old@gmail.com");
                User userInput = this.userRepository.saveAndFlush(user);

                User updateUser = new User(userInput.getId(), "new-name", "new@gmail.com");

                // action
                String resultStr = this.mockMvc
                                .perform(put("/users/{id}", userInput.getId())
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper
                                                                .writeValueAsBytes(updateUser)))
                                .andExpect(status().isOk()).andReturn().getResponse()
                                .getContentAsString();

                User userOutput = this.objectMapper.readValue(resultStr, User.class);

                // assert
                assertEquals("new-name", userOutput.getName());
        }

        // ======== @DeleteMapping("/users/{id}") ========
        @Test
        public void deleteUser() throws Exception {

                // arrange

                User user = new User(null, "delete-name", "delete@gmail.com");

                User userInput = this.userRepository.saveAndFlush(user);

                // action
                this.mockMvc.perform(delete("/users/{id}", userInput.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNoContent());

                // assert
                long countDB = this.userRepository.count();

                assertEquals(0, countDB);
        }
}
