package org.example.auth;

import lombok.RequiredArgsConstructor;
import org.example.User.Role;
import org.example.User.User;
import org.example.User.UserRepository;
import org.example.email.EmailService;
import org.example.email.verificationtoken.VerificationToken;
import org.example.email.verificationtoken.VerificationTokenRepository;
import org.example.security.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @Autowired
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final VerificationTokenRepository verificationTokenRepository;
    private final EmailService emailService;
    public RegistrationResponse register(RegisterRequest request) {
        try {
            Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
            if(userOptional.isPresent()){
                if(userOptional.get().isEnabled()){
                    return new RegistrationResponse(false, "Email already taken.");
                }

                String token = UUID.randomUUID().toString();
                VerificationToken verificationToken = verificationTokenRepository.findByUserId(userOptional.get().getId()).orElse(new VerificationToken());
                verificationToken.setUser(userOptional.get());
                verificationToken.setToken(token);
                verificationToken.setExpiryDate(verificationToken.calculateExpiryDate(VerificationToken.EXPIRATION));
                verificationTokenRepository.save(verificationToken);
                emailService.sendVerificationToken(userOptional.get().getEmail(), token);
                return new RegistrationResponse(true, "success");
            }
            if(userRepository.findByUsername(request.getUsername()).isPresent()){
                return new RegistrationResponse(false, "Username already taken.");
            }
            var user = User.builder()
                    .firstName(request.getName())
                    .lastName(request.getSurname())
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.user)
                    .build();
            userRepository.save(user);
            String token = UUID.randomUUID().toString();
            VerificationToken newVerificationToken = new VerificationToken();
            newVerificationToken.setUser(user);
            newVerificationToken.setToken(token);
            newVerificationToken.setExpiryDate(newVerificationToken.calculateExpiryDate(VerificationToken.EXPIRATION));
            verificationTokenRepository.save(newVerificationToken);
            emailService.sendVerificationToken(user.getEmail(), token);
            return new RegistrationResponse(true, "success");
        } catch (Exception e) {

            return new RegistrationResponse(false, "error");
        }    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow(()->new UsernameNotFoundException("User not found"));
        if(!user.isEnabled()){
            throw new IllegalStateException("Account is not verified");
        }
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();


    }
}
