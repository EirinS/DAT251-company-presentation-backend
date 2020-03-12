package com.presentation;

import com.presentation.AccessingDataMysqlApplication;
import com.presentation.entities.User;
import com.presentation.repositories.UserRepository;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= AccessingDataMysqlApplication.class)
public class UserTest {

    @Autowired
    private UserRepository userRepo;

    @Test
    public void userRepositoryIsCreated(){
        assertNotNull(userRepo);
    }


    @Test
    public void dbHasUserTest(){
        User user = new User();
        user.setName("test");
        user.setEmail("email");
        userRepo.save(user);
        assertNotEquals(0, userRepo.count());

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
        Iterator<User> allUsers = userRepo.findAll().iterator();

        // No users in db, count not modified
        if(!allUsers.hasNext())
            return;

        // Get user and change name
        User user = allUsers.next();
        user.setName("changed");
        userRepo.save(user);

        // Check that count is the same
        assertEquals(current_count, userRepo.count());
    }

}
