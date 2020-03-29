package com.presentation;

import com.presentation.entities.Company;
import com.presentation.entities.Presentation;
import com.presentation.entities.User;
import com.presentation.entities.UserAttendingPresentation;
import com.presentation.repositories.CompanyRepository;
import com.presentation.repositories.PresentationRepository;
import com.presentation.repositories.UserAttendingPresentationRepository;
import com.presentation.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PresentationTest {

    @Autowired
    private PresentationRepository presentationRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAttendingPresentationRepository userAttendingPresentationRepository;

    @Autowired
    private CompanyRepository companyRepository;

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

        assertThrows(TransactionSystemException.class, () -> presentationRepo.save(pres));
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
    @Transactional
    public void presentationCanBeInsertedInDB() {
        // Får gjennbruk av samme company name 'd' som primary key
        /*
        Presentation pres = new Presentation();
        setPresentationValues(pres);
        assertDoesNotThrow(() -> presentationRepo.save(pres));
         */

        //tmp

        Company c = new Company();
        c.setCompanyName("tmp_d");

        Presentation pres = new Presentation();
        pres.setCompanyPresenting(c);
        pres.setMeetupAdress("Thormøhlensgate 55");
        pres.setMaxAttendance(100);
        pres.setDescription("desc");
        pres.setContactPerson("eirin");
        pres.setDateOfPresentation(new Date());

    }

    @Test
    @Transactional
    public void presentationCanHaveAttendees() {

        Presentation pres = new Presentation();

        Set<UserAttendingPresentation> users = new HashSet<>();
        // Create some users
        User u = new User();
        u.setFirstName("Eirin");
        u.setLastName("Sognnes");
        u.setEmail("hotmail");
        u.setStudy("PU");
        u.setYear("4");

        UserAttendingPresentation ua = new UserAttendingPresentation();
        ua.setPresentation(pres);
        ua.setUser(u);
        ua.setWantFood(true);

        users.add(ua);
        pres.setUsersAttending(users);

        Presentation returned = presentationRepo.save(pres);
        assertTrue(returned.getUsersAttending().contains(ua));
    }


}
