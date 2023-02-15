package com.rota.rotakamp.service;

import java.util.List;

import com.rota.rotakamp.model.Role;
import com.rota.rotakamp.model.User;

public interface UserService {
	User saveUser(User user);
	Role saveRole(Role role);
	void addRoleToUser(String username,String roleName);
	User getUser(String username);
	List<User> getUsers();
	List<Role> getRoles();
}
