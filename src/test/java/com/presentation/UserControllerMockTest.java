package com.presentation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
public class UserControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void addingAUserShouldReturn200Ok() throws Exception {

        mockMvc.perform(post("/addUser?firstName=Eivind&lastName=Hexeberg&email=testmail&study=PU&year=4&password=MockPassword&salt=MockSalt123")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }


    @Test
    public void getUserByIdOneShouldWorkIfAtLeastOneUserIsInDB() throws Exception{
        mockMvc.perform(post("/addUser?firstName=Henrik&lastName=Hexeberg&email=hotmail&study=Sikkerhet&year=4&password=MockPassword&salt=MockSalt123")
                .contentType("application/json"));

        mockMvc.perform(get("/userByID?id=1")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }
}
