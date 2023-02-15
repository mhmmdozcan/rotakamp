package com.rota.rotakamp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rota.rotakamp.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByUsername(String username);

}
