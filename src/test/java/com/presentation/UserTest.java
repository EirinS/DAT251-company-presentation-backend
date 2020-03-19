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
@DataJpaTest
public class UserTest {

    @Autowired
    private UserRepository userRepo;

    @Before
    public void addUser(){
        User user = new User();
        user.setName("test");
        user.setEmail("email");
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
        user.setName("second");
        user.setEmail("email");
        userRepo.save(user);
        assertEquals(current_count+1, userRepo.count());
    }

    @Test
    public void modifyingUserDoesNotChangeCount(){
        long current_count = userRepo.count();
        // Get user and change name
        User user = getUser();
        user.setName("changed");
        userRepo.save(user);

        // Check that count is the same
        assertEquals(current_count, userRepo.count());
    }

    @Test
    public void changingUserNameChangesName(){
        String newName = "changed";
        User user = getUser();
        user.setName(newName);
        userRepo.save(user);

        // Check that count is the same
        assertEquals(newName, userRepo.findById(user.getId()).get().getName());
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
        Iterable<User> users = userRepo.findAll();
        users.forEach(user -> userRepo.delete(user));
        assertEquals(0, userRepo.count());
    }

}
