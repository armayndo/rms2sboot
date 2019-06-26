package com.mitrais.rms.rms2sboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mitrais.rms.rms2sboot.dao.RoleRepository;
import com.mitrais.rms.rms2sboot.entity.Role;

public class RoleServiceImpl implements RoleService {

	private RoleRepository roleRepository;
	
	@Autowired
	public RoleServiceImpl(RoleRepository theRoleRepository) {
		roleRepository = theRoleRepository;
	}
	
	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	@Override
	public Role findRoleByName(String name) {
		return roleRepository.findRoleByName(name);
	}

}
