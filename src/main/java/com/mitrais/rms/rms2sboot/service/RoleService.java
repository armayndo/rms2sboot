package com.mitrais.rms.rms2sboot.service;

import java.util.List;

import com.mitrais.rms.rms2sboot.entity.Role;

public interface RoleService {

	Role findRoleByName(String name);
	
	List<Role> findAll();
}
