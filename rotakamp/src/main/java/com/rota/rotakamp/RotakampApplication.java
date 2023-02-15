package com.rota.rotakamp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class RotakampApplication {

    public static void main(String[] args) {
	SpringApplication.run(RotakampApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
    }

//    @Bean
//    CommandLineRunner run(UserService userService) {
//	return args -> {
////			userService.saveRole(new Role(null,"ROLE_USER"));
////			userService.saveRole(new Role(null,"ROLE_MANAGER"));
////			userService.saveRole(new Role(null,"ROLE_ADMIN"));
////			userService.saveRole(new Role(null,"ROLE_SUPER_ADMIN"));
//
////			userService.saveUser(new User(null,"Samet Aydin","samsos","123123",new ArrayList<Role>()));
////			userService.saveUser(new User(null,"Volkan Sonmez","volki","123123",new ArrayList<Role>()));
////			userService.saveUser(new User(null,"Omer Faruk Ozcan","ofaruk","123123",new ArrayList<Role>()));
////			userService.saveUser(new User(null,"Muhammed Ali Ozcan","mhmmd","123123",new ArrayList<Role>()));
////			
////			userService.addRoleToUser("samsos", "ROLE_USER");
////			userService.addRoleToUser("volki", "ROLE_USER");
////			userService.addRoleToUser("volki", "ROLE_MANAGER");
////			userService.addRoleToUser("ofaruk", "ROLE_USER");
////			userService.addRoleToUser("mhmmd", "ROLE_USER");
////			userService.addRoleToUser("mhmmd", "ROLE_ADMIN");
////			userService.addRoleToUser("mhmmd", "ROLE_SUPER_ADMIN");
//	};
//    }
}
