package com.presentation.entities;

import com.presentation.entities.IDs.UserAttendingPresentationID;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
//@IdClass(UserAttendingPresentationID.class)
public class UserAttendingPresentation {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;


    //@Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private User user;

    //@Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Presentation presentation;

    @NotNull
    private boolean wantFood;

    //@NotNull
    private Date signupDateAndTime;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Presentation getPresentation() {
        return presentation;
    }

    public void setPresentation(Presentation presentation) {
        this.presentation = presentation;
    }

    public boolean isWantFood() {
        return wantFood;
    }

    public void setWantFood(boolean wantFood) {
        this.wantFood = wantFood;
    }

    public Date getSignupDateAndTime() {
        return signupDateAndTime;
    }

    public void setSignupDateAndTime(Date signupDateAndTime) {
        this.signupDateAndTime = signupDateAndTime;
    }
}
