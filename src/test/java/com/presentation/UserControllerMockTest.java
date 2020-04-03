package com.presentation;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    public ResultActions addUser(String firstname, String lastName, String email, String study, int year, String password)throws Exception{
        return mockMvc.perform(post("/api/register" +
                "?firstName="+ firstname +
                "&lastName=" + lastName +
                "&email=" + email +
                "&study=" + study +
                "&year=" + year +
                "&password=" + password)
                .contentType("application/json"));
    }


    public ResultActions getUserById(int id, String token) throws Exception{
        if (token == null) token = "";
        return mockMvc.perform(get("/api/userByID?id=" + id)
                .header("Authorization", "Bearer " + token)
                .contentType("application/json"));
    }

    public ResultActions authenticate(int id, String password) throws Exception{
        String requestAuthJSON = "{\"id\":" + id + ", \"password\":\"" + password + "\"}";

        return mockMvc.perform(post("/api/authenticate")
                .contentType("application/json")
                .content(requestAuthJSON));
    }



    @Test
    @Transactional
    public void addingAUserShouldReturn200Ok() throws Exception {
        ResultActions result = addUser("Eivind", "Halderaker", "eivind@gmail.com", "PU", 4, "passord123" );
        result.andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void getUserByIdShouldBeForbiddenIfNotAuthenticated() throws Exception{
        ResultActions addUserResult = addUser("Eivind", "Halderaker", "eivind@gmail.com", "PU", 4, "passord123" );

        JSONObject idJSON = new JSONObject(addUserResult.andReturn().getResponse().getContentAsString());
        int id = idJSON.getInt("id");

        ResultActions result = getUserById(id, "");
        result.andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    public void authenticationTokenReceived() throws Exception {
        String password = "MockPassword";
        ResultActions addUserResult = addUser("Henrik", "Hexeberg", "henrik@hotmail.com", "Sikkerhet", 4, password);

        JSONObject idJSON = new JSONObject(addUserResult.andReturn().getResponse().getContentAsString());
        int id = idJSON.getInt("id");

        ResultActions result = authenticate(id, password);
        result.andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void authenticationTokenNotReceivedIfWrongCredentials() throws Exception {
        String password = "password";
        String wrongPassword = "WrongPassword";

        ResultActions addUserResult = addUser("Henrik", "Hexeberg", "henrik@hotmail.com", "Sikkerhet", 4, password);

        JSONObject idJSON = new JSONObject(addUserResult.andReturn().getResponse().getContentAsString());
        int id = idJSON.getInt("id");

        ResultActions result = authenticate(id, wrongPassword);
        result.andExpect(status().isForbidden());
    }


    @Test
    @Transactional
    public void getUserByIdShouldWorkIfAtLeastOneUserIsInDB() throws Exception{
        String password = "password";

        ResultActions addUserResult = addUser("Henrik", "Hexeberg", "henrik@hotmail.com", "Sikkerhet", 4, password);

        JSONObject idJSON = new JSONObject(addUserResult.andReturn().getResponse().getContentAsString());
        int id = idJSON.getInt("id");

        ResultActions authResult = authenticate(id, password);

        JSONObject json = new JSONObject(authResult.andReturn().getResponse().getContentAsString());
        String token = json.getString("jwt");
        System.out.println(token);

        ResultActions result = getUserById(id, token);
        result.andExpect(status().isOk());
    }



}
