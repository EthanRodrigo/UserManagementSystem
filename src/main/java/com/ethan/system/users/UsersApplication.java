package com.ethan.system.users;

import com.ethan.system.users.model.Role;
import com.ethan.system.users.model.User;
import com.ethan.system.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@SpringBootApplication
public class UsersApplication{
	public static void main(String[] args) {
		SpringApplication.run(UsersApplication.class, args);
	}
}
