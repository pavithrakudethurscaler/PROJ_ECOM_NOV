package com.ecom.mainapp.repos;

import com.ecom.mainapp.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepo extends JpaRepository<Token, Long> {
    Optional<Token> findByValueAndDeletedAndExpiryAtGreaterThan(String token, boolean isDeleted, long expiryTime);
}
