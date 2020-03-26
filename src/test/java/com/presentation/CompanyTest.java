package com.presentation;

import com.presentation.entities.Company;
import com.presentation.entities.Presentation;
import com.presentation.repositories.CompanyRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyTest {

    @Autowired
    private CompanyRepository companyRepo;

    @Test
    public void companyRepositoryIsCreated() {
        assertNotNull(companyRepo);
    }

    @Test
    public void companyMustHaveId() {
        Company company = new Company();
        assertThrows(JpaSystemException.class, () -> companyRepo.save(company));
    }

    @Test
    public void companyWithIdCanBeAddedToDB() {
        Company company = new Company();
        company.setCompanyName("BedCompany");
        assertDoesNotThrow(() -> companyRepo.save(company));
    }

    @Test
    public void companyCanBeFetchedFromDB() {
        Company company = new Company();
        company.setCompanyName("BedCompany");
        companyRepo.save(company);
        Company dbCompany = companyRepo.findById("BedCompany").get();
        assertEquals(company.getCompanyName(), dbCompany.getCompanyName());
    }

    public Presentation addPresentationToCompany() {
        Company company = new Company();
        company.setCompanyName("BedCompany");

        Set<Presentation> presentations = new HashSet<>();
        Presentation p = new Presentation();
        p.setId(1);
        p.setDateOfPresentation(new Date());
        p.setCompanyPresenting(company);
        presentations.add(p);
        company.setPresentations(presentations);
        companyRepo.save(company);
        return p;
    }

    public int iteratorCount(Iterator iterator) {
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        return count;
    }

    @Test
    public void addingPresentationToCompanyAddsOne() {
        addPresentationToCompany();
        assertEquals(1, companyRepo.findById("BedCompany").get().getPresentations().size());
    }

    @Test
    public void canFindPresentationsRelatedToCompany() {
        Presentation pres = addPresentationToCompany();
        Presentation stored = companyRepo.findById("BedCompany").get().getPresentations().iterator().next();

        // Check that date is the same, needs to be compare to because formats can be different
        assertEquals(0, stored.getDateOfPresentation().compareTo(pres.getDateOfPresentation()));

        // Check that presenting company is the same
        assertEquals(stored.getCompanyPresenting().getCompanyName(), pres.getCompanyPresenting().getCompanyName());
    }
}
