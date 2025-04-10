package com.example.GrocceryShop.tool;

import com.example.GrocceryShop.model.AppUser;
import com.example.GrocceryShop.service.AppUserServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initAdmin(AppUserServiceImpl appUserServiceImpl) {
        return args -> {
            if (appUserServiceImpl.findByUsername("admin").isEmpty()) {
                AppUser admin = new AppUser();
                admin.setFirstname("Admin");
                admin.setLastname("Admin");
                admin.setUsername("admin");
                admin.setPassword("12345");
                admin.getRoles().add(AppUserServiceImpl.ROLES.ADMINISTRATOR.toString());
                appUserServiceImpl.create(admin);
                System.out.println("Администратор Entered Ezzz.");
            }
        };
    }
}
