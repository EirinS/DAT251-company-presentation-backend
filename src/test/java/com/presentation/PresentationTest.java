package com.presentation;

import com.presentation.entities.Company;
import com.presentation.entities.Presentation;
import com.presentation.entities.User;
import com.presentation.entities.UserAttendingPresentation;
import com.presentation.repositories.PresentationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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


    @Test
    public void presentationMustHaveCompany() {
        Presentation pres = new Presentation();
        // Set all values of presentation, to make sure nothing else triggers the
        // exception
        setPresentationValues(pres);
        // set Company to null
        pres.setCompanyPresenting(null);

        // Inserting presentation with null values company
        // should yield exception.
        assertThrows(TransactionSystemException.class, () -> presentationRepo.save(pres));
    }

    @Test
    public void presentationCanBeInsertedInDB(){
        Presentation pres = new Presentation();
        setPresentationValues(pres);
        assertDoesNotThrow(() -> presentationRepo.save(pres));
    }

    @Test
    public void presentationCanHaveAttendees(){
        Presentation pres = new Presentation();
        setPresentationValues(pres);

        Set<UserAttendingPresentation> users = new HashSet<>();
        // Create some users
        User u = new User(); u.setFirstName("Eirin");
        UserAttendingPresentation ua = new UserAttendingPresentation(); ua.setPresentation(pres); ua.setUser(u);
        users.add(ua);
        pres.setUsersAttending(users);

        Presentation returned = presentationRepo.save(pres);
        //assertTrue(returned.getUsersAttending().contains(ua));
        //Presentation p = presentationRepo.findById(returned.getId()).get();
    }





}
