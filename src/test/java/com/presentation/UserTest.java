package com.presentation;

import com.presentation.entities.User;
import org.junit.Test;

import static org.junit.Assert.assertNull;

public class UserTest {

    @Test
    public void testNewUserHasId(){
        //TODO: This test does fail, comment out the user.setId(1);
        User user = new User();
        assertNull(user.getId());
    }

}
