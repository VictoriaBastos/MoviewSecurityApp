package com.rezebas.moviewsecurity.service.impl;

import com.rezebas.moviewsecurity.repositories.User;
import com.rezebas.moviewsecurity.repositories.UserRepository;
import com.rezebas.moviewsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }
}
