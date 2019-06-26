package com.mitrais.rms.rms2sboot.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.mitrais.rms.rms2sboot.entity.User;
import com.mitrais.rms.rms2sboot.service.UserService;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserService userService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		System.out.println("\n\nIn customAuthenticationSuccessHandler\n\n");

		String userName = authentication.getName();

		System.out.println("userName=" + userName);
		System.out.println("getAuthorities=" + authentication.getAuthorities().toString());

		User theUser = userService.findByUserName(userName);
		System.out.println(theUser.getUserName() + " " + theUser.getEmail());
		//System.out.println(theUser.toString()); //error failed to lazily initialize a collection of role: com.mitrais.rms.entity.User.roles, could not initialize proxy - no Session 
		// now place in the session
		HttpSession session = request.getSession();
		session.setAttribute("user", theUser);
		
		// forward to home page
		
		response.sendRedirect(request.getContextPath() + "/showIndex");
	}

}
