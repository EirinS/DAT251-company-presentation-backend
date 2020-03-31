package com.presentation.repositories;

import com.presentation.entities.User;
import com.presentation.entities.UserAttendingPresentation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface UserAttendingPresentationRepository extends CrudRepository<UserAttendingPresentation, Integer> {

    @Query("select u.user from UserAttendingPresentation u where u.presentation.id = :id")
    public List<User> findByPresentation(@Param("id") int id);


}
