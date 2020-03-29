package com.presentation;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
public class UserControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    public ResultActions addUser(String firstname, String lastName, String email, String study, int year, String password)throws Exception{
        return mockMvc.perform(post("/addUser" +
                "?firstName="+ firstname +
                "&lastName=" + lastName +
                "&email=" + email +
                "&study=" + study +
                "&year=" + year +
                "&password=" + password)
                .contentType("application/json"));
    }

    public ResultActions authenticate(int id, String password) throws Exception{
        String requestAuthJSON = "{\"id\":1, \"password\":\"MockPassword\"}";

        return mockMvc.perform(post("/authenticate")
                .contentType("application/json")
                .content(requestAuthJSON));
    }

    public JSONObject resultActionsToJSON(ResultActions result) throws Exception{
        return new JSONObject(result.andReturn().getResponse().getContentAsString());
    }


    @Test
    public void addingAUserShouldReturn200Ok() throws Exception {
        ResultActions result = addUser("Eivind", "Halderaker", "eivind@gmail.com", "PU", 4, "passord123" );
        result.andExpect(status().isOk());
    }

    @Test
    public void getUserByIdOneShouldBeForbiddenIfNotAuthenticated() throws Exception{
        mockMvc.perform(post("/addUser?firstName=Henrik&lastName=Hexeberg&email=hotmail&study=Sikkerhet&year=4&password=MockPassword")
                .contentType("application/json"));

        mockMvc.perform(get("/userByID?id=1")
                .contentType("application/json"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void authenticationTokenReceived() throws Exception {
        mockMvc.perform(post("/addUser?firstName=Henrik&lastName=Hexeberg&email=hotmail&study=Sikkerhet&year=4&password=MockPassword")
                .contentType("application/json"));

        String json = "{\"id\":1, \"password\":\"MockPassword\"}";

        mockMvc.perform(post("/authenticate")
                .contentType("application/json")
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void authenticationTokenNotReceivedIfWrongCredentials() throws Exception {
        mockMvc.perform(post("/addUser?firstName=Henrik&lastName=Hexeberg&email=hotmail&study=Sikkerhet&year=4&password=MockPassword")
                .contentType("application/json"));

        String json = "{\"id\":1, \"password\":\"123\"}";

        mockMvc.perform(post("/authenticate")
                .contentType("application/json")
                .content(json))
                .andExpect(status().isForbidden());
    }


    @Test
    public void getUserByIdOneShouldWorkIfAtLeastOneUserIsInDB() throws Exception{
        mockMvc.perform(post("/addUser?firstName=Henrik&lastName=Hexeberg&email=hotmail&study=Sikkerhet&year=4&password=MockPassword&salt=MockSalt123")
                .contentType("application/json"));

        mockMvc.perform(post("/authenticate")
                .contentType("application/json"));

        String requestAuthJSON = "{\"id\":1, \"password\":\"MockPassword\"}";

        ResultActions result = mockMvc.perform(post("/authenticate")
                .contentType("application/json")
                .content(requestAuthJSON));

        JSONObject jsonObject = new JSONObject(result.andReturn().getResponse().getContentAsString());
        String token = jsonObject.getString("jwt");


        mockMvc.perform(get("/userByID?id=1")
                .header("Authorization", "Bearer " + token)
                .contentType("application/json"))
                .andExpect(status().isOk());

    }



}
