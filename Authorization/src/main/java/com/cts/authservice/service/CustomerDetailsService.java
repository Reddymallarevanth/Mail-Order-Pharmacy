package com.cts.authservice.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cts.authservice.dao.UserDAO;
import com.cts.authservice.entity.UserData;

@Service
public class CustomerDetailsService implements UserDetailsService {
	@Autowired
	private UserDAO userdao;

	@Override
	public UserDetails loadUserByUsername(String uid) {
		
		try
		{
			UserData custuser = userdao.findById(uid).orElse(null);
			custuser.getUname();
			return new User(custuser.getUserid(), custuser.getUpassword(), new ArrayList<>());
		}
		catch (Exception e) {
			throw new UsernameNotFoundException("UsernameNotFoundException");
		}
			
		
		
	}

}
