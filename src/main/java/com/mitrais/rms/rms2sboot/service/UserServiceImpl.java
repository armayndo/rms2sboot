package com.mitrais.rms.rms2sboot.service;

import com.mitrais.rms.rms2sboot.dao.EmployeeRepository;
//import com.mitrais.rms.rms2sboot.dao.RoleDao;
import com.mitrais.rms.rms2sboot.dao.RoleRepository;
//import com.mitrais.rms.rms2sboot.dao.UserDao;
import com.mitrais.rms.rms2sboot.dao.UserRepository;
import com.mitrais.rms.rms2sboot.entity.Role;
import com.mitrais.rms.rms2sboot.entity.User;
import com.mitrais.rms.rms2sboot.user.CrmUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

	// need to inject user dao
//	@Autowired
//	private UserDao userDao;
//
//	@Autowired
//	private RoleDao roleDao;
//	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	private UserRepository userRepository;
	
	private RoleRepository roleRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository theUserRepository, 
			RoleRepository theRoleRepository) {
		userRepository = theUserRepository;
		roleRepository = theRoleRepository;
	}

	@Override
	public User findByUserName(String userName) {
		// check the database if the user already exists
		return userRepository.findByUserName(userName);
	}

	@Override
	public void save(CrmUser crmUser) {
		User user = new User();
		 // assign user details to the user object
		user.setUserName(crmUser.getUserName());
		user.setPassword(passwordEncoder.encode(crmUser.getPassword()));
		//user.setPassword(crmUser.getPassword());
		user.setFirstName(crmUser.getFirstName());
		user.setLastName(crmUser.getLastName());
		user.setEmail(crmUser.getEmail());

		// give user default role of "employee"
		//user.setRoles(Arrays.asList(roleRepository.findRoleByName("ROLE_EMPLOYEE")));
		user.setRoles(new HashSet<>(Arrays.asList(roleRepository.findRoleByName("ROLE_EMPLOYEE"))));

		 // save user in the database
		userRepository.save(user);
	}
	
	@Override
	public void update(User user) {
		
		userRepository.save(user);
		
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(userName);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		System.out.println(user.getRoles());
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public void delete(String userName) {
		User user = userRepository.findByUserName(userName);
		userRepository.delete(user);;
		
	}

	
}
