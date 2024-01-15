package com.anant.SpringSecurity_2X.service;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.anant.SpringSecurity_2X.entity.User;

//In Spring Security, the UserDetails interface is a core interface that represents the core information about a user. 
//It encapsulates details about a user, including their username, password, authorities (roles), and other attributes.
//The UserDetails interface is used as a standard way to represent user information during the authentication and authorization processes.
// It has various methods like--
//getAuthorities(): Returns a collection of GrantedAuthority objects.
//getUsername(),getPassword(),isAccountNonExpired(),isAccountNonLocked(),isCredentialsNonExpired(),isEnabled().

public class GroupUserDetails implements UserDetails {

	private String userName;
	private String password;
	private boolean isActive;
	private List<GrantedAuthority> authorities;
	
	public GroupUserDetails(User user)
	{
		this.userName=user.getUserName();
		this.password=user.getPassword();
		this.isActive=user.isActive();
		
		//user.getRoles().split(","): This takes the roles string from the user object, presumably containing roles separated by commas, and splits it into an array of strings. For example, if user.getRoles() returns "ROLE_USER,ROLE_ADMIN", then this would result in an array ["ROLE_USER", "ROLE_ADMIN"].

		//Arrays.stream(...): This converts the array of strings into a stream of strings using Arrays.stream(...). Now you have a stream of role names.

		//.map(SimpleGrantedAuthority::new): This maps each role name to a SimpleGrantedAuthority object. SimpleGrantedAuthority is an implementation of the GrantedAuthority interface provided by Spring Security. It represents a granted authority or role.

		//The map function applies the constructor reference SimpleGrantedAuthority::new to each role name, creating a SimpleGrantedAuthority object for each role name in the stream.

		//collect(Collectors.toList()): Finally, this collects the SimpleGrantedAuthority objects into a List. The result is a List<SimpleGrantedAuthority> containing authorities/roles parsed from the original comma-separated string.
		this.authorities=Arrays.stream(user.getRoles().split(",")).map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}

	// <? extends GrantedAuthority>: This is a wildcard (?) with an upper bound
	// (extends GrantedAuthority).
	// It's part of Java's generics feature. This syntax means that the collection
	// can contain objects of any type that is a subtype
	// of (or implements) the GrantedAuthority interface.

	// the getAuthorities() method is often associated with user roles and
	// permissions.
	// The method would return a collection of authorities assigned to a user.

	// Collection: This indicates that the method returns a collection of objects.
	// returns a collection of objects that extend or implement the GrantedAuthority
	// interface.
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
