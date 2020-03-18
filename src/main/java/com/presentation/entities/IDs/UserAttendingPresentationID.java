package com.presentation.entities.IDs;

import com.presentation.entities.Presentation;
import com.presentation.entities.User;

import java.io.Serializable;


/**
 * A class to allow a composite primary key in UserAttendingPresentation
 */
public class UserAttendingPresentationID implements Serializable {
    private User user;
    private Presentation presentation;

    public UserAttendingPresentationID(User user, Presentation presentation) {
        this.user = user;
        this.presentation = presentation;
    }
}
