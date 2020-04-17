package com.presentation;

import com.presentation.entities.User;
import com.presentation.repositories.UserRepository;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;


    public String authenticate(int id, String password) throws Exception{
        String requestAuthJSON = "{\"id\":" + id + ", \"password\":\"" + password + "\"}";

        ResultActions resultActions = mockMvc.perform(post("/api/authenticate")
                .contentType("application/json")
                .content(requestAuthJSON));

        JSONObject json = new JSONObject(resultActions.andReturn().getResponse().getContentAsString());
        String token = json.getString("jwt");
        return token;
    }

    public User addAdmin(String firstname, String lastName, String email, String study, int year, String password)throws Exception{
        User admin = new User();
        admin.setFirstName(firstname);
        admin.setLastName(lastName);
        admin.setEmail(email);
        admin.setStudy(study);
        admin.setYear(year + "");
        admin.setPassword(password);
        admin.setRole("admin");
        return userRepository.save(admin);
    }

    public User addUser(String firstname, String lastName, String email, String study, int year, String password)throws Exception{
        User admin = new User();
        admin.setFirstName(firstname);
        admin.setLastName(lastName);
        admin.setEmail(email);
        admin.setStudy(study);
        admin.setYear(year + "");
        admin.setPassword(password);
        admin.setRole("user");
        return userRepository.save(admin);
    }


    @Test
    @Transactional
    public void addCompany() throws Exception {
        String password = "password";
        User admin = addAdmin("Henrik", "Hexeberg", "henrik@ok.no", "sikkerhet", 4, password);
        String token = authenticate(admin.getId(), password);
        mockMvc.perform(post("/api/admin/addCompany" +
                "?companyName=BEKK" +
                "&logo=hey" +
                "&website=bekk.no" +
                "&contactPerson=noone")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void getCompanies() throws Exception {
        String password = "password";
        User user = addUser("Henrik", "Hexeberg", "henrik@ok.no", "sikkerhet", 4, password);
        String token = authenticate(user.getId(), password);
        mockMvc.perform(get("/api/user/allCompanies")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }


}