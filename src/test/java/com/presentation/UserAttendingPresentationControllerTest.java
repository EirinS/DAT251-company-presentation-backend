package com.presentation;

import com.presentation.entities.Company;
import com.presentation.entities.Presentation;
import com.presentation.entities.User;
import com.presentation.repositories.PresentationRepository;
import com.presentation.repositories.UserRepository;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserAttendingPresentationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PresentationRepository presentationRepository;

    public String authenticate(int id, String password) throws Exception{
        String requestAuthJSON = "{\"id\":" + id + ", \"password\":\"" + password + "\"}";

        ResultActions resultActions = mockMvc.perform(post("/api/authenticate")
                .contentType("application/json")
                .content(requestAuthJSON));

        JSONObject json = new JSONObject(resultActions.andReturn().getResponse().getContentAsString());
        String token = json.getString("jwt");
        return token;
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

    public Presentation addPresentation(){
        Company c = new Company();
        c.setCompanyName("tmp_d");

        Presentation pres = new Presentation();
        pres.setCompanyPresenting(c);
        pres.setMeetupAdress("Thormøhlensgate 55");
        pres.setMaxAttendance(100);
        pres.setDescription("desc");
        pres.setContactPerson("eirin");
        pres.setDateOfPresentation(new Date());
        return presentationRepository.save(pres);
    }


    @Test
    @Transactional
    public void userCanAttendPresentation() throws Exception{
        String password = "password";
        User user = addUser("Henrik", "Hexeberg", "henrik@ok.no", "sikkerhet", 4, password);
        String token = authenticate(user.getId(), password);
        Presentation pres = addPresentation();


        mockMvc.perform(post("/api/user/attendPresentation" +
                "?user=" + user.getId() +
                "&presentation=" + pres.getId() +
                "&wantFood=true")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

}
