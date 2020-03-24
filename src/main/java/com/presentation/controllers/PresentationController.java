package com.presentation.controllers;

import com.presentation.entities.Company;
import com.presentation.entities.Presentation;
import com.presentation.repositories.PresentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;

@Controller
public class PresentationController {

    @Autowired
    private PresentationRepository presentationRepository;

    @PostMapping(path = "/addPresentation") // Map ONLY POST Requests
    public @ResponseBody
    String addNewPresentation(
            @RequestParam Date dateOfPresentation,
            @RequestParam Company companyPresenting,
            @RequestParam int maxAttendance,
            @RequestParam String contactPerson,
            @RequestParam String meetupAddress,
            @RequestParam String description) {

        Presentation presentation = new Presentation();
        presentation.setDateOfPresentation(dateOfPresentation);
        presentation.setCompanyPresenting(companyPresenting);
        presentation.setMaxAttendance(maxAttendance);
        presentation.setContactPerson(contactPerson);
        presentation.setMeetupAdress(meetupAddress);
        presentation.setDescription(description);

        presentationRepository.save(presentation);
        return "Saved";
    }

    @GetMapping(path = "/allPresentations")
    public @ResponseBody
    Iterable<Presentation> getPresentations() {
        return presentationRepository.findAll();
    }


}
