package com.projectfinalapi.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.projectfinalapi.dao.UserDao;
import com.projectfinalapi.function.ApiResponse;
import com.projectfinalapi.function.DateTime;
import com.projectfinalapi.model.DAOUser;
import com.projectfinalapi.model.UserDto;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	private DateTime  dateTime;  
	
    @Autowired
    private ApiResponse apiResponse;
    
    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DAOUser user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),new ArrayList<>());
    }

    public String save(UserDto user){
        DAOUser newUser = new DAOUser();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setRole(user.getRole());  //เขียนเพิ่ม
        userDao.save(newUser);
        return apiResponse.users(dateTime.timestamp(), 201, user.getUsername(), user.getRole());
    }
    
}
