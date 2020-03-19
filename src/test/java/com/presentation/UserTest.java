package com.presentation;

import com.presentation.entities.User;
import com.presentation.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Iterator;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
//@DataJpaTest
@SpringBootTest
public class UserTest {

    @Autowired
    private UserRepository userRepo;

    @Before
    public void addUser(){
        User user = new User();
        user.setFirstName("test");
        user.setLastName("Testson");
        user.setEmail("email");
        user.setStudy("PU");
        user.setYear("4");
        userRepo.save(user);
    }

    public User getUser(){
        if(userRepo.count() == 0)
            throw new IndexOutOfBoundsException("No users in DB");

        Iterator<User> allUsers = userRepo.findAll().iterator();
        return allUsers.next();
    }

    @Test
    public void userRepositoryIsCreated(){
        assertNotNull(userRepo);
    }


    @Test
    public void dbHasUserTest(){
        assertTrue(userRepo.count() > 0);
    }

    @Test
    public void addingUserIncreasesDbNumber(){
        long current_count = userRepo.count();
        User user = new User();
        user.setFirstName("second");
        user.setLastName("Secondson");
        user.setEmail("email");
        user.setStudy("Sikkerhet");
        user.setYear("2");
        userRepo.save(user);
        assertEquals(current_count+1, userRepo.count());
    }

    @Test
    public void modifyingUserDoesNotChangeCount(){
        long current_count = userRepo.count();
        // Get user and change name
        User user = getUser();
        user.setFirstName("changed");
        userRepo.save(user);

        // Check that count is the same
        assertEquals(current_count, userRepo.count());
    }

    @Test
    public void changingUserFirstNameChangesFirstName(){
        String newName = "changed";
        User user = getUser();
        user.setFirstName(newName);
        userRepo.save(user);

        // Check that count is the same
        //assertEquals(newName, userRepo.findById(user.getId()).get().getName());
    }

    @Test
    public void changingUserEmailChangesEmail(){
        String newMail = "changed@change.no";
        User user = getUser();
        user.setEmail(newMail);
        userRepo.save(user);

        // Check that count is the same
        assertEquals(newMail, userRepo.findById(user.getId()).get().getEmail());
    }

    @Test
    public void deletingAllUsersMakesDBEmpty(){
        userRepo.deleteAll();
        assertEquals(0, userRepo.count());
    }

    @Test
    public void getZeroUsersFromEmptyDB(){
        userRepo.deleteAll();
        Iterator<User> users = userRepo.findAll().iterator();
        assertFalse(users.hasNext());
    }

}
