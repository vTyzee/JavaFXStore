package com.example.GrocceryShop.service;

import com.example.GrocceryShop.interfaces.AppUserService;
import com.example.GrocceryShop.model.AppUser;
import com.example.GrocceryShop.repository.AppUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserServiceImpl implements AppUserService {

    private static AppUser currentUser;
    private final AppUserRepository userRepository;

    public AppUserServiceImpl(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public AppUser create(AppUser entity) {
        return userRepository.save(entity);
    }

    @Override
    public Optional<AppUser> getById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<AppUser> getAll() {
        return userRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<AppUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public AppUser getCurrentUser() {
        return currentUser;
    }

    @Override
    public void setCurrentUser(AppUser user) {
        currentUser = user;
    }

    @Override
    public AppUser updateUser(AppUser user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("Нельзя обновить AppUser без ID.");
        }
        return userRepository.save(user);
    }

    // --- Новый метод saveOrUpdate ---
    /**
     * Метод, объединяющий логику создания/обновления.
     * Если у user есть ID, Spring Data JPA сделает UPDATE,
     * иначе — INSERT (create).
     */
    public AppUser saveOrUpdate(AppUser user) {
        return userRepository.save(user);
    }
}
