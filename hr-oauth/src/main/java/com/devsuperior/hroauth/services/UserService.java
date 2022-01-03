package com.devsuperior.hroauth.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.devsuperior.hroauth.entities.User;
import com.devsuperior.hroauth.feignClients.UserFeignClient;

@Service
public class UserService implements UserDetailsService {

	private static Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private  UserFeignClient userFeignClient;
	
	public User findByEmail(String email) {
		User user = userFeignClient.findByEmail(email).getBody();
		
		if (user == null) {
			log.error("Emial not found: " + email);
			throw new IllegalArgumentException("Email not found");
		}
		log.info("Email found: " + email);
		return user;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userFeignClient.findByEmail(username).getBody();

		if (user == null) {
			log.error("Emial not found: " + username);
			throw new UsernameNotFoundException("Email not found");
		}
		log.info("Email found: " + username);
		return user;
	}
	
}
