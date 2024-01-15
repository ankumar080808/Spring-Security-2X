package com.anant.SpringSecurity_2X.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.anant.SpringSecurity_2X.entity.User;
import com.anant.SpringSecurity_2X.repository.UserRepository;

//In the context of Spring Security, the UserDetailsService interface is a core interface used for retrieving user-related information.
//It is a part of the Spring Security authentication process and is essential for loading user details during authentication.
//The primary method in this interface is loadUserByUsername, which takes a username as an argument and returns a UserDetails object.

@Service
public class GroupUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		           Optional<User> user= userRepo.findByUserName(username);
		return user.map(GroupUserDetails::new).orElseThrow(() -> new UsernameNotFoundException(username + " Not Found"));
	}

}

//user.map(GroupUserDetails::new)-- It will return optional<GroupUserDetails> in case optional<User> is present
// if optional<User> is not present then it will return empty optional<GroupUserDetails>.
//GroupUserDetails::new: This is a reference to a constructor method of the GroupUserDetails class. It's a shorthand way of saying "use the constructor of GroupUserDetails to create a new instance."
// orElseThrow(() -> new UsernameNotFoundException(username + " Not Found"))--- This will return GroupUserDetails object.
/*
 * The syntax Function<? super T, ? extends U> is a generic type declaration in
 * Java and is typically used to represent a function that transforms objects of
 * type T into objects of type U. Let's break down the elements of this type
 * declaration:
 * 
 * Function: Function is a functional interface in Java, available in the
 * java.util.function package. It represents a function that takes one argument
 * and produces a result. The interface has a single abstract method called
 * apply.
 * 
 * ? super T: This is a wildcard type declaration. It indicates that the
 * function can accept objects of type T or any supertype of T. In other words,
 * you can pass objects of type T or any class/interface that is a superclass of
 * T as an argument to the function.
 * 
 * ? extends U: Another wildcard type declaration, but this time indicating that
 * the function produces a result of type U or any subtype of U. In other words,
 * the result of applying the function will be of type U or a subtype of U.
 * 
 * Combining all these elements, Function<? super T, ? extends U> declares a
 * function that can take an argument of type T or any supertype of T and
 * produces a result of type U or any subtype of U.
 */