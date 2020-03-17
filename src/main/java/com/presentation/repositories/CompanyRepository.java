package com.presentation.repositories;

import com.presentation.entities.Presentation;
import org.springframework.data.repository.CrudRepository;

public interface CompanyRepository extends CrudRepository<Presentation, Integer> {

}
