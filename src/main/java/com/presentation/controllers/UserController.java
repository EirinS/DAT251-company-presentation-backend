package com.presentation.controllers;

import com.presentation.entities.Presentation;
import com.presentation.entities.UserAttendingPresentation;
import com.presentation.repositories.PresentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.presentation.entities.User;
import com.presentation.repositories.UserRepository;

import java.sql.Date;


@Controller	// This means that this class is a Controller
public class UserController {
	@Autowired // This means to get the bean called userRepository
			   // Which is auto-generated by Spring, we will use it to handle the data
	private UserRepository userRepository;


	@PostMapping(path="/addUser") // Map ONLY POST Requests
	public @ResponseBody String addNewUser (
			@RequestParam String firstName,
			@RequestParam String lastName,
			@RequestParam String email,
			@RequestParam String study,
			@RequestParam String year
	){
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request

		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setStudy(study);
		user.setYear(year);
		userRepository.save(user);
		return "Saved";
	}

	@GetMapping(path="/allUsers")
	public @ResponseBody Iterable<User> getAllUsers() {
		// This returns a JSON or XML with the users
		return userRepository.findAll();
	}


}
