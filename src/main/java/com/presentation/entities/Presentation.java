package com.presentation.entities;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Presentation {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private Date dateOfPresentation;

    private String companyPresenting;

    private int maxAttendance;

    private String contactPerson;

    private String meetupAddress;

    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateOfPresentation() {
        return dateOfPresentation;
    }

    public void setDateOfPresentation(Date dateOfPresentation) {
        this.dateOfPresentation = dateOfPresentation;
    }

    public String getCompanyPresenting() {
        return companyPresenting;
    }

    public void setCompanyPresenting(String companyPresenting) {
        this.companyPresenting = companyPresenting;
    }

    public int getMaxAttendance() {
        return maxAttendance;
    }

    public void setMaxAttendance(int maxAttendance) {
        this.maxAttendance = maxAttendance;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getMeetupAdress() {
        return meetupAddress;
    }

    public void setMeetupAdress(String meetupAdress) {
        this.meetupAddress = meetupAdress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
