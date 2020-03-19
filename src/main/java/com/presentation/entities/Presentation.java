package com.presentation.entities;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
public class Presentation {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @NotNull
    private Date dateOfPresentation;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Company companyPresenting;

    private int maxAttendance;

    private String contactPerson;

    private String meetupAddress;

    private String description;

    @OneToMany(mappedBy = "presentation", cascade = CascadeType.ALL)
    private Set<UserAttendingPresentation> usersAttending;



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

    public Company getCompanyPresenting() {
        return companyPresenting;
    }

    public void setCompanyPresenting(Company companyPresenting) {
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
