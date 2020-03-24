package com.presentation;

import com.presentation.entities.Company;
import com.presentation.repositories.CompanyRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.junit4.SpringRunner;

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
    public void companyMustHaveId(){
        Company company = new Company();
        assertThrows(JpaSystemException.class, () -> companyRepo.save(company));
    }
    
    @Test
    public void companyWithIdCanBeAddedToDB(){
        Company company = new Company();
        company.setCompanyName("BedCompany");
        assertDoesNotThrow(() -> companyRepo.save(company));
    }
}
