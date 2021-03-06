package com.presentation.controllers;

import com.presentation.entities.Company;
import com.presentation.entities.Presentation;
import com.presentation.repositories.PresentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.sql.Date;
import java.util.Map;

@Controller
public class PresentationController {

    @Autowired
    private PresentationRepository presentationRepository;

    @PostMapping(path = "/api/admin/addPresentation") // Map ONLY POST Requests
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
        return "{\"id\":" + presentation.getId() + "}";
    }

    @GetMapping(path = "/api/user/allPresentations")
    public @ResponseBody
    Iterable<Presentation> getPresentations() {
        return presentationRepository.findAll();
    }

    @PatchMapping(path = "/api/admin/editPresentation")
    public @ResponseBody
    Presentation editPresentation(@RequestParam("id") Integer id, @RequestBody Map<String, Object> fields) {
        Presentation presentation = presentationRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(Presentation.class, k);
            field.setAccessible(true);
            ReflectionUtils.setField(field, presentation, v);
        });
        presentationRepository.save(presentation);
        return presentation;
    }

}
