package com.presentation.controllers;

import com.presentation.entities.Company;
import com.presentation.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    @PostMapping(path = "/api/admin/addCompany") // Map ONLY POST Requests
    public @ResponseBody
    String addNewPresentation(
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

    @GetMapping(path = "/api/user/allCompanies")
    public @ResponseBody
    Iterable<Company> getCompanies() {
        return companyRepository.findAll();
    }

}
