package com.presentation.controllers;

import com.presentation.entities.Presentation;
import com.presentation.entities.User;
import com.presentation.entities.UserAttendingPresentation;
import com.presentation.repositories.UserAttendingPresentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class UserAttendingPresentationController {

    @Autowired
    private UserAttendingPresentationRepository userAttendingPresentationRepository;

    @PostMapping(path = "/api/user/attendPresentation") // Map ONLY POST Requests
    public @ResponseBody
    String addNewPresentation(
            @RequestParam User user,
            @RequestParam Presentation presentation,
            @RequestParam boolean wantFood) {

        UserAttendingPresentation userAttendingPresentation = new UserAttendingPresentation();
        userAttendingPresentation.setUser(user);
        userAttendingPresentation.setPresentation(presentation);
        userAttendingPresentation.setWantFood(wantFood);
        userAttendingPresentation.setSignupDateAndTime(java.sql.Date.valueOf(LocalDate.now()));
        userAttendingPresentationRepository.save(userAttendingPresentation);
        return "Saved";
    }

    @GetMapping(path = "/api/admin/allAttending/{presentationId}")
    public @ResponseBody
    List<User> getUserAttendingPresentation(@PathVariable("presentationId") int id) {
        return userAttendingPresentationRepository.findByPresentation(id);
    }

}
