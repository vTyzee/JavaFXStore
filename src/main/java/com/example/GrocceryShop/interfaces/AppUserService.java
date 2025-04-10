package com.example.GrocceryShop.interfaces;

import com.example.GrocceryShop.model.AppUser;
import com.example.GrocceryShop.service.AppService;

import java.util.Optional;

public interface AppUserService extends AppService<AppUser> {

    enum ROLES {
        ADMINISTRATOR,
        MANAGER,
        USER
    }
    Optional<AppUser> findByUsername(String username);
    AppUser getCurrentUser();
    void setCurrentUser(AppUser user);
    AppUser updateUser(AppUser user);
}

