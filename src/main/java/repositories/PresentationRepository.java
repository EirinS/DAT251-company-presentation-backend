package repositories;

import entities.Presentation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface PresentationRepository extends CrudRepository<Presentation, Integer> {
}
