package com.rota.rotakamp.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rota.rotakamp.Repository.RoleRepository;
import com.rota.rotakamp.Repository.UserRepository;
import com.rota.rotakamp.model.Role;
import com.rota.rotakamp.model.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImp implements UserService,UserDetailsService {

	private final UserRepository userRepo;
	private final RoleRepository roleRepo;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user =userRepo.findByUsername(username);
		if(user == null) {
			log.error("user not found");
			throw new UsernameNotFoundException("user not found");
		}else {
			log.error("user found");
		}
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		});
		return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
	}
	
	@Override
	public User saveUser(User user) {
		log.info("Saving new user {} to the database",user.getUsername());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}

	@Override
	public Role saveRole(Role role) {
		log.info("Saving new role {} to the database",role.getName());
		return roleRepo.save(role);
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
		log.info("Adding role {} to user {}",roleName,username);
		User _user =userRepo.findByUsername(username);
		Role _role = roleRepo.findByName(roleName);
		_user.getRoles().add(_role);
		
	}

	@Override
	public User getUser(String username) {
		log.info("Getting user {}",username);
		return userRepo.findByUsername(username);
	}

	@Override
	public List<User> getUsers() {
		log.info("Getting All Users");
		return userRepo.findAll();
	}

	@Override
	public List<Role> getRoles() {
		log.info("Getting All Roles");
		return roleRepo.findAll();
	}
}
