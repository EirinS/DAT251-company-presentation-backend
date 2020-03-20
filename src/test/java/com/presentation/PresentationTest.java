package com.presentation;

import com.presentation.entities.Company;
import com.presentation.entities.Presentation;
import com.presentation.repositories.PresentationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

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

    public void setPresentationValues(Presentation pres) {
        // Set company
        Company c = new Company();
        c.setCompanyName("d");
        pres.setCompanyPresenting(c);

        // Set all fields that are not date
        pres.setMeetupAdress("Thormøhlensgate 55");
        pres.setMaxAttendance(100);
        pres.setDescription("desc");
        pres.setContactPerson("eirin");
        pres.setDateOfPresentation(new Date());
    }

    @Test
    public void presentationMustHaveDate() {
        Presentation pres = new Presentation();
        // Set all values of presentation, to make sure nothing else triggers the
        // exception
        setPresentationValues(pres);
        // set Date to null
        pres.setDateOfPresentation(null);

        // Inserting presentation with null value for date
        // should yield exception.
        assertThrows(DataIntegrityViolationException.class, () -> presentationRepo.save(pres));
    }
    






}
