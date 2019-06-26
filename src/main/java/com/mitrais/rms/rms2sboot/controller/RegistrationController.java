package com.mitrais.rms.rms2sboot.controller;

import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mitrais.rms.rms2sboot.dao.RoleRepository;
import com.mitrais.rms.rms2sboot.entity.Role;
import com.mitrais.rms.rms2sboot.entity.User;
import com.mitrais.rms.rms2sboot.service.RoleService;
import com.mitrais.rms.rms2sboot.service.UserService;
import com.mitrais.rms.rms2sboot.user.CrmUser;
import com.mysql.cj.Session;

@Controller
@RequestMapping("/register")
public class RegistrationController {
	
	//@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
    @Autowired
    private UserService userService;
    
    @Autowired
    private RoleRepository roleRepository;
	
    private Logger logger = Logger.getLogger(getClass().getName());
    
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}	
	
	@GetMapping("/showRegistrationForm")
	public String showMyLoginPage(Model theModel) {
		
		theModel.addAttribute("crmUser", new CrmUser());
		
		return "registration-form";
	}

	@PostMapping("/processRegistrationForm")
	public String processRegistrationForm(
				@Valid @ModelAttribute("crmUser") CrmUser theCrmUser, 
				BindingResult theBindingResult, 
				Model theModel) {
		
		String userName = theCrmUser.getUserName();
		logger.info("Processing registration form for: " + userName);
		
		// form validation
		 if (theBindingResult.hasErrors()){
			 return "registration-form";
	        }

		// check the database if user already exists
        User existing = userService.findByUserName(userName);
        if (existing != null){
        	theModel.addAttribute("crmUser", new CrmUser());
			theModel.addAttribute("registrationError", "User name already exists.");

			logger.warning("User name already exists.");
        	return "registration-form";
        }
        // create user account        						
        userService.save(theCrmUser);
        
        logger.info("Successfully created user: " + userName);
        
        return "registration-confirmation";		
	}
	
	
	
	@PostMapping("/processUpdateForm")
	public String processUpdateForm(
				@Valid @ModelAttribute("crmUser") CrmUser theCrmUser, 
				BindingResult theBindingResult, 
				Model theModel, 
				@RequestParam(value="id") String userNameOld, 
				@RequestParam(value = "rols" , required = false) String[] rls,
				Authentication authentication) {
		
		User userOld = userService.findByUserName(userNameOld);
		String userName = theCrmUser.getUserName();
		String passOld = userOld.getPassword();
		String passNew = theCrmUser.getPassword();
		logger.info("Processing update form for: " + userName);
		
		// form validation
		 if (theBindingResult.hasErrors()){
			 User user = userService.findByUserName(userName);
			 theModel.addAttribute("user", user);
			 List<Role> roles = roleRepository.findAll();
			 theModel.addAttribute("roles", roles);
			 return "update-form";
	        }

		 /*
		 if(userNameOld != userName) {
			// check the database if user already exists
		        User existing = userService.findByUserName(userName);
		        if (existing != null){
		        	theModel.addAttribute("crmUser", new CrmUser());
					theModel.addAttribute("registrationError", "User name already exists.");

					logger.warning("User name already exists.");
		        	return "update-form";
		        } else {
		        	userOld.setUserName(userName); 
		        }
		 }
		 // */
		 
		 if(!passOld.equals(passNew)) {
			 userOld.setPassword(passwordEncoder.encode(theCrmUser.getPassword()));
		 }
		 
		 userOld.setFirstName(theCrmUser.getFirstName());
		 userOld.setLastName(theCrmUser.getLastName());
		 userOld.setEmail(theCrmUser.getEmail());
		 
		 HashSet<Role> newRoles = new HashSet<Role>();
		 if(rls != null) {
			    for (int i = 0; i < rls.length; i++) {
			    	newRoles.add(roleRepository.findRoleByName(rls[i]));
			    }
			    userOld.setRoles(newRoles);
			}

        // update user account        						
        userService.update(userOld);
        
        logger.info("Successfully update user: " + userName);
        if(authentication.getName().toString().equals(userName)) {
        	theModel.addAttribute("user", userOld);  
        }
        else {
        	User user  = userService.findByUserName(authentication.getName());
        	theModel.addAttribute("user", user);  
        }
        
        
        return "index";		
	}
	
	@GetMapping("/showIndex")
	public ModelAndView showIndex(HttpSession session) {
		
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("index");
	
		User theUser = (User) session.getAttribute("user");
		
		User user = userService.findByUserName(theUser.getUserName());
		modelView.addObject("user", user);
		
		return modelView;
	}
}
