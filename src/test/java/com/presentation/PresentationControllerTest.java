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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PresentationControllerTest {

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
        userRepository.save(admin);
        return admin;
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
        userRepository.save(admin);
        return admin;
    }

    @Before
    @Transactional
    public void addCompany() throws Exception {
        String password = "password";
        User admin = addAdmin("Henrik", "Hexeberg", "henrik@ok.no", "sikkerhet", 4, password);
        String token = authenticate(admin.getId(), password);
        mockMvc.perform(post("/addCompany" +
                "?companyName=BEKK" +
                "&logo=hey" +
                "&website=bekk.no" +
                "&contactPerson=noone")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Transactional
    public void addPresentationWithoutTokenShouldBeForbidden() throws Exception {
        mockMvc.perform(post("/api/admin/addPresentation" +
                "?dateOfPresentation=2020-02-0" +
                "2&companyPresenting=BEKK" +
                "&maxAttendance=100" +
                "&contactPerson=Eirin" +
                "&meetupAddress=Hoytek" +
                "&description=This is fun!").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }


    @Test
    @Transactional
    public void addPresentationAsAdminReturns200OK() throws Exception {
        String password = "password";
        User admin = addAdmin("Henrik", "Hexeberg", "henrik@ok.no", "sikkerhet", 4, password);
        String token = authenticate(admin.getId(), password);

        mockMvc.perform(post("/api/admin/addPresentation" +
                "?dateOfPresentation=2020-02-0" +
                "2&companyPresenting=BEKK" +
                "&maxAttendance=100" +
                "&contactPerson=Eirin" +
                "&meetupAddress=Hoytek" +
                "&description=This is fun!")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void addPresentationAsUserIsForbidden() throws Exception {
        String password = "password";
        User user = addUser("Henrik", "Hexeberg", "henrik@ok.no", "sikkerhet", 4, password);
        String token = authenticate(user.getId(), password);

        mockMvc.perform(post("/api/admin/addPresentation" +
                "?dateOfPresentation=2020-02-0" +
                "2&companyPresenting=BEKK" +
                "&maxAttendance=100" +
                "&contactPerson=Eirin" +
                "&meetupAddress=Hoytek" +
                "&description=This is fun!")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }


    @Test
    @Transactional
    public void editPresentationWithoutTokenShouldBeForbidden() throws Exception {
        String password = "password";
        User admin = addAdmin("Henrik", "Hexeberg", "henrik@ok.no", "sikkerhet", 4, password);
        String token = authenticate(admin.getId(), password);

        //add presentation
        ResultActions addPresentationResult = mockMvc.perform(post("/api/admin/addPresentation" +
                "?dateOfPresentation=2020-02-0" +
                "2&companyPresenting=BEKK" +
                "&maxAttendance=100" +
                "&contactPerson=Eirin" +
                "&meetupAddress=Hoytek" +
                "&description=This is fun!")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON));
        int id = new JSONObject(addPresentationResult.andReturn().getResponse().getContentAsString()).getInt("id");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("contactPerson", "Henrik");

        //request without token
        mockMvc.perform(patch("/api/admin/editPresentation?id=" + id)
                .content(jsonObject.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }


    @Test
    @Transactional
    public void editPresentationAsAdminIsOK() throws Exception {
        String password = "password";
        User admin = addAdmin("Henrik", "Hexeberg", "henrik@ok.no", "sikkerhet", 4, password);
        String token = authenticate(admin.getId(), password);

        //add presentation
        ResultActions addPresentationResult = mockMvc.perform(post("/api/admin/addPresentation" +
                "?dateOfPresentation=2020-02-0" +
                "2&companyPresenting=BEKK" +
                "&maxAttendance=100" +
                "&contactPerson=Eirin" +
                "&meetupAddress=Hoytek" +
                "&description=This is fun!")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON));
        int id = new JSONObject(addPresentationResult.andReturn().getResponse().getContentAsString()).getInt("id");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("contactPerson", "Henrik");

        mockMvc.perform(patch("/api/admin/editPresentation?id=" + id)
                .header("Authorization", "Bearer " + token)
                .content(jsonObject.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void editPresentationAsUserIsForbidden() throws Exception {
        String password = "password";
        User user = addUser("Henrik", "Hexeberg", "henrik@ok.no", "sikkerhet", 4, password);
        User admin = addAdmin("Henrik", "Hexeberg", "henrik@ok.no", "sikkerhet", 4, password);

        String userToken = authenticate(user.getId(), password);
        String adminToken = authenticate(admin.getId(), password);

        //add presentation
        ResultActions addPresentationResult = mockMvc.perform(post("/api/admin/addPresentation" +
                "?dateOfPresentation=2020-02-0" +
                "2&companyPresenting=BEKK" +
                "&maxAttendance=100" +
                "&contactPerson=Eirin" +
                "&meetupAddress=Hoytek" +
                "&description=This is fun!")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON));
        int id = new JSONObject(addPresentationResult.andReturn().getResponse().getContentAsString()).getInt("id");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("contactPerson", "Henrik");

        mockMvc.perform(patch("/api/admin/editPresentation?id=" + id)
                .header("Authorization", "Bearer " + userToken)
                .content(jsonObject.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }


    @Test
    @Transactional
    public void editPresentationAsAdminChangePresentationOK() throws Exception {
        String password = "password";
        User admin = addAdmin("Henrik", "Hexeberg", "henrik@ok.no", "sikkerhet", 4, password);
        String token = authenticate(admin.getId(), password);

        //add presentation
        ResultActions addPresentationResult = mockMvc.perform(post("/api/admin/addPresentation" +
                "?dateOfPresentation=2020-02-0" +
                "2&companyPresenting=BEKK" +
                "&maxAttendance=100" +
                "&contactPerson=Eirin" +
                "&meetupAddress=Hoytek" +
                "&description=This is fun!")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON));
        int id = new JSONObject(addPresentationResult.andReturn().getResponse().getContentAsString()).getInt("id");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("contactPerson", "Henrik");

        ResultActions patchResult = mockMvc.perform(patch("/api/admin/editPresentation?id=" + id)
                .header("Authorization", "Bearer " + token)
                .content(jsonObject.toString())
                .contentType(MediaType.APPLICATION_JSON));

        assertEquals(new JSONObject(patchResult.andReturn().getResponse().getContentAsString()).get("contactPerson"), jsonObject.get("contactPerson"));

    }



    @Test
    @Transactional
    public void getAllPresentationsWithoutTokenShouldBeForbidden() throws Exception {
        mockMvc.perform(get("/api/user/allPresentations")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    public void getAllPresentationsAsAdminIsOK() throws Exception {
        String password = "password";
        User admin = addAdmin("Henrik", "Hexeberg", "henrik@ok.no", "sikkerhet", 4, password);
        String token = authenticate(admin.getId(), password);

        ResultActions allPresentations = mockMvc.perform(get("/api/user/allPresentations")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void getAllPresentationsAsUserIsOK() throws Exception {
        String password = "password";
        User user = addUser("Henrik", "Hexeberg", "henrik@ok.no", "sikkerhet", 4, password);
        String token = authenticate(user.getId(), password);

        ResultActions allPresentations = mockMvc.perform(get("/api/user/allPresentations")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }







}
