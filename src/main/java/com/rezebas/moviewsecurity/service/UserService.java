package com.rezebas.moviewsecurity.service;

import com.rezebas.moviewsecurity.repositories.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findByEmail(String email);
}

