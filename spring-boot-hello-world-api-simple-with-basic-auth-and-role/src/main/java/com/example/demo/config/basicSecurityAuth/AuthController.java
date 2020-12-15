package com.example.demo.config.basicSecurityAuth;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/api/v1/auth")
@RestController
public class AuthController {

	//http://localhost:8080/api/v1/auth/logout (use this to log user out)
	
	//http://localhost:8080/api/v1/auth/me
	@GetMapping(path = "/me", produces = "application/json")
	public Principal me(HttpServletRequest request, Principal principal) {
		return principal; 
	}

	//http://localhost:8080/api/v1/auth/me/roles
	@GetMapping(path = "/me/roles", produces = "application/json")
	public List<String> me2(HttpServletRequest request, Principal principal) {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		System.out.println(securityContext.getAuthentication().getPrincipal());
		return securityContext.getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());//.getName();
	}


	//http://localhost:8080/api/v1/auth/checkUsername?username=admin
	@GetMapping(path = "/checkUsername", produces = "application/json")
	@PreAuthorize("#username == authentication.principal.username")
	public boolean checkUserNameIsSameAsPrincipalUser(HttpServletRequest request, Principal principal, @RequestParam String username) {
		System.out.println("checkUserNameIsSameAsPrincipalUser: " + username);
		return true; 
	}

	
	//http://localhost:8080/api/v1/auth/checkUserRoleIsAdmin
	@GetMapping(path = "/checkUserRoleIsAdmin", produces = "application/json")
	public boolean checkUserRole(HttpServletRequest request, Principal principal) {	
		
		SecurityContext context = SecurityContextHolder.getContext();
        if (context == null)
            return false;

        Authentication authentication = context.getAuthentication();
        if (authentication == null)
            return false;

        for (GrantedAuthority auth : authentication.getAuthorities()) {
        	System.out.println(auth.getAuthority());
            if ("ADMIN".equals(auth.getAuthority()))
                return true;
        }

        return false;
	}

}
