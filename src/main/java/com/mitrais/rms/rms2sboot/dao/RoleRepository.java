package com.mitrais.rms.rms2sboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mitrais.rms.rms2sboot.entity.Role;
import com.mitrais.rms.rms2sboot.entity.User;

public interface RoleRepository extends JpaRepository<Role, Integer> {

//	@Query("select r from role r where r.name = :name")
//	public Role findRoleByName(@Param("name") String name);
	
	public Role findRoleByName(String name);
	
}
