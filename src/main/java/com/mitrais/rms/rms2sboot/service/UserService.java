package com.mitrais.rms.rms2sboot.service;

import com.mitrais.rms.rms2sboot.entity.User;
import com.mitrais.rms.rms2sboot.user.CrmUser;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService { 

    User findByUserName(String userName);
    
    List<User> findAll();

    void save(CrmUser crmUser);
    
    void update(User user);
    
    void delete(String userName);
}
