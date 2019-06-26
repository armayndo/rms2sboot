package com.mitrais.rms.rms2sboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mitrais.rms.rms2sboot.entity.User;
//import com.mitrais.rms.rms2sboot.service.UserService;
//import com.mitrais.rms.rms2sboot.user.CrmUser;
import com.mitrais.rms.rms2sboot.service.UserService;

@Controller
@RequestMapping("/systems")
public class SystemsController {
	
	@Autowired
    private UserService userService;
		
		@GetMapping("/system")
		public String showAdmins() {
			
			return "systems/systems";
		}
		
		
		@GetMapping("/userList")
		public String showUserList(Model theModel) {
			
			List<User> users = userService.findAll();
			theModel.addAttribute("users", users);
			
			return "systems/list";
		}
		
		@GetMapping("/userList2")
		public ModelAndView showUserList2() {
			
			ModelAndView modelView = new ModelAndView();
			modelView.setViewName("systems/list");
			
			List<User> users = userService.findAll();
			modelView.addObject("users", users);
			
			return modelView;
		}
		
		@GetMapping("/delete")
		public String deleteUser(@RequestParam(name="id") String userId ) {
					    
			userService.delete(userId);
			
			return "redirect:/systems/userList2";
			//return "userList"; //ga bisa di pake
		}
		

}
