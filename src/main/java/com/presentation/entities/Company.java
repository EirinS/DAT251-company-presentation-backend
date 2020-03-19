package com.presentation.entities;


import javax.persistence.*;
import javax.validation.constraints.Null;

@Entity
public class Company {
    @Id
    private String companyName;


    private String logo;


    private String website;


    private String contactPerson;

    @OneToOne
    private Presentation presentation;




    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
}
