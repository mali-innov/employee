package com.maliinnov.employee.services.token;

import com.maliinnov.employee.models.Employee;
import com.maliinnov.employee.models.Token;
import com.maliinnov.employee.repositories.TokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository repository;

    @Override
    public void save(Employee employee, String token) {
        Token savedToken = Token.builder()
                .token(token)
                .revoked(false)
                .expired(false)
                .createdAt(LocalDateTime.now())
                .employee(employee)
                .build();
        repository.save(savedToken);
    }

    @Override
    public void revokeAllEmployeeTokens(Employee employee) {
        List<Token> tokens = repository.findAllByUserIdAndExpiredIsFalseOrRevokedIsFalse(employee.getId());
        if (tokens.isEmpty()){
            return;
        }
        tokens.forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
            token.setLogoutAt(LocalDateTime.now());
        });
        repository.saveAll(tokens);
    }
}
