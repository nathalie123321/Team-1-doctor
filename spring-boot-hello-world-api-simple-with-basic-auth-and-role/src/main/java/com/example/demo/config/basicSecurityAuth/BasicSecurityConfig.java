package com.example.demo.config.basicSecurityAuth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)  
@EnableWebSecurity
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter {

	
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.inMemoryAuthentication()
		.withUser("admin").password("{noop}" + "password").authorities(new SimpleGrantedAuthority("ADMIN"))
		.and()
		.withUser("user").password("{noop}" + "password").authorities(new SimpleGrantedAuthority("USER"))
		
		//ADD NEW USERS HERE ...
		
		;
	}
	
	

	protected void configure(HttpSecurity http) throws Exception {
		
		http
		.csrf().disable()
		.authorizeRequests()
		//.antMatchers("/me**").permitAll() 
		//.antMatchers("/api/v1/unrestricted/**").permitAll()
		//.antMatchers("/api/v1/**").authenticated()
		//.antMatchers("/api/v1/admin/**").access("hasAnyAuthority('ADMIN') or hasAnyRole('ADMIN')") //HARDCODED FILTER BUT CAN BE SPECIFIED AS AN ANNOTATION ON THE ENDPOINT DIRECTLY WITH @SECURE @PREAUTHORIZIED ETC... (ENSURE TO COMMENT THIS OUT IF GOING WITTH THE ANNOTATIONS INSTEAD )
		//.antMatchers("/api/v1/user/**").access("hasAnyAuthority('ADMIN') or hasAnyRole('ADMIN')") //HARDCODED FILTER BUT CAN BE SPECIFIED AS AN ANNOTATION ON THE ENDPOINT DIRECTLY WITH @SECURE @PREAUTHORIZIED ETC... (ENSURE TO COMMENT THIS OUT IF GOING WITTH THE ANNOTATIONS INSTEAD )
		.anyRequest().authenticated() 
		.and()

		.logout() 
		.logoutUrl("/api/v1/auth/logout")
		.permitAll()
		.and()
		.httpBasic(); 
	}



}




