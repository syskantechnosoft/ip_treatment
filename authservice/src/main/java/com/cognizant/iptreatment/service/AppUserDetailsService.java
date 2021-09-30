package com.cognizant.iptreatment.service;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cognizant.iptreatment.controller.UserController;
import com.cognizant.iptreatment.exception.UserAlreadyExistsException;
import com.cognizant.iptreatment.model.Role;
import com.cognizant.iptreatment.model.User;
import com.cognizant.iptreatment.repository.RoleRepository;
import com.cognizant.iptreatment.repository.UserRepository;
import com.cognizant.iptreatment.security.AppUser;

@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	AppUser appUser;
	User user;
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	public AppUserDetailsService() {
		super();
	}

	public AppUserDetailsService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	public AppUserDetailsService(UserRepository userRepository, RoleRepository roleRepository) {
		super();
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LOGGER.info("loadUserByUsername START");
		user = userRepository.findByUsername(username);
		if (user == null)
			throw new UsernameNotFoundException("username not found");
		else
			appUser = new AppUser(user);
		LOGGER.info("loadUserByUsername END");
		return appUser;
	}

	public void signUp(User user) throws UserAlreadyExistsException {

		LOGGER.info("signUp START");
		String pass = user.getPassword();
		LOGGER.debug("my password " + pass);
		user.setPassword(passwordEncoder().encode(pass));
		LOGGER.debug("my encrypted password" + user.getPassword());
		String userName = user.getUsername();
		User userNameExists = userRepository.findByUsername(userName);
		if (userNameExists != null) {
			throw new UserAlreadyExistsException();
		} else {
			Role role = roleRepository.findById(1);
			Set<Role> roleList = new HashSet<Role>();
			roleList.add(role);
			user.setRoleList(roleList);
			this.userRepository.save(user);
		}
		LOGGER.info("signUp END");
	}

	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
