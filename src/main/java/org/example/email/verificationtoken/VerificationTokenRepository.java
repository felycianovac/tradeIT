package org.example.email.verificationtoken;

import org.example.email.verificationtoken.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Integer> {
        Optional<VerificationToken> findByToken(String token);

}
