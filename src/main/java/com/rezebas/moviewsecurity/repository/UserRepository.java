package com.rezebas.moviewsecurity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<com.rezebas.moviewsecurity.repositories.User,Long> {
    com.rezebas.moviewsecurity.repositories.User findByEmail(String email);
}
