package com.presentation.repositories;

import com.presentation.entities.Presentation;
import org.springframework.data.repository.CrudRepository;

public interface PresentationRepository extends CrudRepository<Presentation, Integer> {
}
