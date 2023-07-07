package com.maliinnov.employee.repositories;

import com.maliinnov.employee.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    List<Token> findAllByUserIdAndExpiredIsFalseOrRevokedIsFalse(Long id);
    Optional<Token> findByToken(String token);
    Token findByTokenAndRevokedIsFalseOrExpiredIsFalse(String token);
}
