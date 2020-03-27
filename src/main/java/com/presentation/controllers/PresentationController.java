package com.presentation.controllers;

import com.presentation.entities.Company;
import com.presentation.entities.Presentation;
import com.presentation.entities.User;
import com.presentation.entities.UserAttendingPresentation;
import com.presentation.repositories.PresentationRepository;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.sql.Date;
import java.util.Map;
import java.util.Optional;

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

    @PatchMapping(path = "/editPresentation/{id}")
    public @ResponseBody
    Presentation editPresentation(@PathVariable("id") Integer id, @RequestBody Map<String, Object> fields) throws NotFound {
        Presentation presentation = presentationRepository.findById(id).orElseThrow(NotFound::new);
        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(Presentation.class, k);
            field.setAccessible(true);
            ReflectionUtils.setField(field, presentation, v);
        });
        presentationRepository.save(presentation);
        return presentation;
    }

}
