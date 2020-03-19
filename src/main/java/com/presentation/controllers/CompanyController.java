package com.presentation.controllers;

import com.presentation.entities.Company;
import com.presentation.entities.Presentation;
import com.presentation.repositories.CompanyRepository;
import com.presentation.repositories.PresentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;

@Controller
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;


    @PostMapping(path="/addCompany") // Map ONLY POST Requests
    public @ResponseBody
    String addNewPresentation (
            @RequestParam String companyName,
            @RequestParam String logo,
            @RequestParam String website,
            @RequestParam String contactPerson) {

        Company company = new Company();
        company.setCompanyName(companyName);
        company.setLogo(logo);
        company.setWebsite(website);
        company.setContactPerson(contactPerson);

        companyRepository.save(company);
        return "Saved";
    }

    @GetMapping(path="/allCompanies")
    public @ResponseBody Iterable<Company> getCompanies() {
        return companyRepository.findAll();
    }

}