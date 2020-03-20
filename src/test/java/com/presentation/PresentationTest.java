package com.presentation;

import com.presentation.repositories.PresentationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PresentationTest {

    @Autowired
    private PresentationRepository presentationRepo;

    @Test
    public void presentationRepositoryIsCreated() {
        assertNotNull(presentationRepo);
    }

}
