package com.mitrais.rms.rms2sboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mitrais.rms.rms2sboot.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

//	@Query("select u from user u where u.username = :userName")
//	public User findByUserName(@Param("userName") String userName);
	
	public User findByUserName(String userName);
}
