package com.san.inv.sony_liv;

import com.san.inv.sony_liv.bo.ApplicationUser;
import com.san.inv.sony_liv.bo.Role;
import com.san.inv.sony_liv.repo.RoleRepository;
import com.san.inv.sony_liv.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class SonyLivCodingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SonyLivCodingApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder){
		return args -> {
			Role admin = addRole("Admin", roleRepository);
			addRole("Viewer", roleRepository);
			addRole("Editor", roleRepository);
			Set<Role> roles = new HashSet<>();
			roles.add(admin);
			ApplicationUser user = new ApplicationUser(1, "admin", passwordEncoder.encode("admin"), roles);
			userRepository.save(user);
		};
	}

	private Role addRole(String strAdmin, RoleRepository roleRepository){
		Role role = new Role(strAdmin.toUpperCase());
		if(!roleRepository.findByAuthority(strAdmin).isPresent()){
			role = roleRepository.saveAndFlush(role);
		}else{
			role = roleRepository.findByAuthority(strAdmin).get();
		}
		return role;
	}

}
