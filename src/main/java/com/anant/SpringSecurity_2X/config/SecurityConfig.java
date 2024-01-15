package com.anant.SpringSecurity_2X.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.anant.SpringSecurity_2X.service.GroupUserDetailsService;

//WebSecurityConfigurerAdapter is a convenient base class that allows developers to customize the security configuration of their web application.
//It provides a way to configure security settings such as authentication and authorization,

//WebSecurityConfigurerAdapter contains several methods that you can override to customize the security configuration
//Some commonly overridden methods include:
//configure(HttpSecurity http): Allows configuring the security settings related to HTTP requests, such as authentication, authorization, and CSRF protection.
//configure(AuthenticationManagerBuilder auth): Allows configuring the authentication manager, specifying how users are authenticated.

//@EnableWebSecurity annotation is used to enable and configure Spring Security in your application.
//It is often added to a configuration class that extends WebSecurityConfigurerAdapter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
    private GroupUserDetailsService groupUserDetailsService;
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(groupUserDetailsService);
    }
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		
		//The http.csrf().disable(); configuration is used in Spring Security to disable Cross-Site Request Forgery (CSRF) protection for a web application. CSRF is a security feature that protects against unauthorized execution of actions on behalf of an authenticated user.
		http.csrf().disable();
		
		//http.authorizeRequests():

//http is an instance of HttpSecurity that you configure in your Spring Security configuration class, typically by extending WebSecurityConfigurerAdapter.
//authorizeRequests() is a method that allows you to specify rules for restricting access based on different conditions.
//.antMatchers("/user/join"):

//antMatchers is a method used to specify URL patterns to which the following configuration should apply.
//"/user/join" is the URL pattern for which the access rule is defined. In this case, it's the path "/user/join".
//.permitAll():

//permitAll() is a method that indicates that any user, regardless of authentication status, is allowed access to the specified URL pattern.
//In other words, access to the "/user/join" URL is permitted for everyone, including anonymous (unauthenticated) users.
//	    http.authorizeRequests().antMatchers("/user/join").permitAll().and().authorizeRequests()
//        .antMatchers("/user/**").authenticated().and().httpBasic();
	    
	    http.authorizeRequests().antMatchers("user/join").permitAll().and()
	    .authorizeRequests().antMatchers("/user/**","/post/**").authenticated().and().httpBasic();
	
		
		
	}
	
	//BCryptPasswordEncoder is a class provided by Spring Security for encoding passwords using the bcrypt hashing algorithm.
	//Bcrypt is a widely used and secure password hashing algorithm that incorporates a salt and a work factor to protect against rainbow table attacks and brute-force attacks.
	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	

}
