package com.anant.SpringSecurity_2X.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anant.SpringSecurity_2X.common.UserConstant;
import com.anant.SpringSecurity_2X.entity.User;
import com.anant.SpringSecurity_2X.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository repository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
     
	@PostMapping("/join")
	public String joinGroup(@RequestBody User user) {
		user.setRoles(UserConstant.DEFAULT_ROLE);
		String bCryptPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(bCryptPassword);
		repository.save(user);
		return "Hi"+" " + user.getUserName() +" "+ "Welcome to User Group";

	}

	// If loggedin user is ADMIN -> ADMIN OR MODERATOR
	// If loggedin user is MODERATOR -> MODERATOR
	// Principal is an interface that provides a way to obtain the identity of the
	// current user.
	// When a user logs in, the authentication process establishes the identity of
	// the user and creates an Authentication object.
	// The Authentication object typically contains a Principal representing the
	// authenticated user.
	  @GetMapping("/access/{userId}/{userRole}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
	public String giveAccessToUser(@PathVariable int userId, @PathVariable String userRole, Principal principal) {
		User user = repository.findById(userId).get();
		List<String> activeRoles = getRolesByLoggedInUser(principal);
		String newRole = "";
		if (activeRoles.contains(userRole)) {
			newRole = user.getRoles() + "," + userRole;
			user.setRoles(newRole);
		}
		repository.save(user);
		return "Hi " + user.getUserName() + " New Role assign to you by " + principal.getName();

	}

	private List<String> getRolesByLoggedInUser(Principal principal) {
		String roles = getLoggedInUser(principal).getRoles();
		List<String> assignRoles = Arrays.stream(roles.split(",")).collect(Collectors.toList());
		if (assignRoles.contains("ROLE_ADMIN")) {
			return Arrays.stream(UserConstant.ADMIN_ACCESS).collect(Collectors.toList());
		}
		if (assignRoles.contains("ROLE_MODERATOR")) {
			return Arrays.stream(UserConstant.MODERATOR_ACCESS).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	private User getLoggedInUser(Principal principal) {
		return repository.findByUserName(principal.getName()).get();
	}

	@GetMapping
	@Secured("ROLE_ADMIN")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public List<User> loadUsers() {
		return repository.findAll();
	}

	@GetMapping("/test")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String testUserAccess() {
		return "user can only access this !";
	}

}
