package com.rota.rotakamp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rota.rotakamp.model.Role;

public interface RoleRepository  extends JpaRepository<Role, Long>{
	
	Role findByName(String name);


}
