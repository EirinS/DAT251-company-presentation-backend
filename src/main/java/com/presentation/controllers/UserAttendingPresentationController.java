package com.presentation.controllers;

import com.presentation.entities.Company;
import com.presentation.entities.Presentation;
import com.presentation.entities.User;
import com.presentation.entities.UserAttendingPresentation;
import com.presentation.repositories.CompanyRepository;
import com.presentation.repositories.UserAttendingPresentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Instant;
import java.time.LocalDate;

public class UserAttendingPresentationController {

    @Autowired
    private UserAttendingPresentationRepository userAttendingPresentationRepository;


    @PostMapping(path="/addCompany") // Map ONLY POST Requests
    public @ResponseBody
    String addNewPresentation (
            @RequestParam User user,
            @RequestParam Presentation presentation,
            @RequestParam boolean wantFood){


        UserAttendingPresentation userAttendingPresentation = new UserAttendingPresentation();
        userAttendingPresentation.setUser(user);
        userAttendingPresentation.setPresentation(presentation);
        userAttendingPresentation.setWantFood(wantFood);
        userAttendingPresentation.setSignupDateAndTime(java.sql.Date.valueOf(LocalDate.now()));
        userAttendingPresentationRepository.save(userAttendingPresentation);
        return "Saved";
    }

    @GetMapping(path="/allCompanies")
    public @ResponseBody Iterable<UserAttendingPresentation> getUserAttendingPresentation() {
        return userAttendingPresentationRepository.findAll();
    }

}

