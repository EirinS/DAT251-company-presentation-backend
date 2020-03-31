package com.presentation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PresentationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Before
    @Transactional
    public void addCompany() throws Exception {
        mockMvc.perform(post("/addCompany" +
                "?companyName=BEKK" +
                "&logo=hey" +
                "&website=bekk.no" +
                "&contactPerson=noone")
                .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Transactional
    public void addPresentationReturns200OK() throws Exception {
        mockMvc.perform(post("/addPresentation" +
                "?dateOfPresentation=2020-02-0" +
                "2&companyPresenting=BEKK" +
                "&maxAttendance=100" +
                "&contactPerson=Eirin" +
                "&meetupAddress=Hoytek" +
                "&description=This is fun!").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
