package com.mitrais.rms.rms2sboot.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mitrais.rms.rms2sboot.dao.RoleRepository;
import com.mitrais.rms.rms2sboot.entity.Role;
import com.mitrais.rms.rms2sboot.entity.User;
import com.mitrais.rms.rms2sboot.service.RoleService;
//import com.mitrais.rms.rms2sboot.service.UserService;
//import com.mitrais.rms.rms2sboot.user.CrmUser;
import com.mitrais.rms.rms2sboot.service.UserService;
import com.mitrais.rms.rms2sboot.user.CrmUser;

@Controller
public class DemoController {

	// create a mapping for "/hello"
	
	@GetMapping("/hello")
	public String sayHello(Model theModel) {
		
		theModel.addAttribute("theDate", new java.util.Date());
		
		return "helloworld";
	}
	
	@Autowired
    private UserService userService;
	
	@Autowired
	private RoleRepository roleService;
	
	@GetMapping("/")
	public String showHome() {
		return "index";
	}
	
//		@GetMapping("/showIndexx")
//		public String showIndex() {
//			return "index";
//		}
	
	@GetMapping("/showIndex")
	public ModelAndView showIndex(HttpSession session) {
		
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("index");
	
		User theUser = (User) session.getAttribute("user");
		
		User user = userService.findByUserName(theUser.getUserName());
		modelView.addObject("user", user);
		
		return modelView;
	}
	
		// add request mapping for /leaders
		@GetMapping("/leaders")
		public String showLeaders() {
			
			return "leaders";
		}
		
		@GetMapping("/systems")
		public String showAdmins() {
			
			return "systems/systems";
		}
		
		
		@GetMapping("/profile/updateProfile")
		public String updateProfile(@RequestParam(name="id") String userId, Model theModel ) {
					    
			User user = userService.findByUserName(userId);
			List<Role> roles = roleService.findAll();
			CrmUser crmUser = new CrmUser();
			crmUser.setUserName(user.getUserName());
			crmUser.setFirstName(user.getFirstName());
			crmUser.setLastName(user.getLastName());
			crmUser.setPassword(user.getPassword());
			crmUser.setMatchingPassword(user.getPassword());
			crmUser.setEmail(user.getEmail());
			theModel.addAttribute("user", user);
			theModel.addAttribute("crmUser", crmUser);
			theModel.addAttribute("roles", roles);
			
			return "update-form";
		}
		
		@GetMapping("/login1")
		public String showLogin1() {
			
			return "fancy-login";
		}
		
		@GetMapping("/login2")
		public String showLogin2() {
			
			return "users/login";
		}
}








