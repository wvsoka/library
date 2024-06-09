package com.lista2.service;

import com.lista2.LoginForm;
import com.lista2.LoginResponse;
import com.lista2.User;
import com.lista2.repositories.IUserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Service class for managing user login.
 */
@Service
public class LoginService {

    private IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.token.key}")
    private String key;

    @Autowired
    public LoginService(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Authenticates a user and generates a JWT token.
     *
     * @param loginForm The login form containing user credentials.
     * @return JWT token if authentication is successful, null otherwise.
     */
    public LoginResponse userLogin(LoginForm loginForm) {
        // tutaj pobrac dane uzytkownika z bazy danych
        User user = userRepository.findByLogin(loginForm.getLogin());

        //encode - haszowanie gdy zmienia uzytkownik haslo, matches - porownuje haslo wpisane przez uzytkownika do bazy danych

        if (user != null && passwordEncoder.matches(loginForm.getPassword(), user.getPassword())) {
            //stworzymy nowy token jak sie hasla zgadzaja
            long timeMillis = System.currentTimeMillis();
            String token = Jwts.builder()
                    .issuedAt(new Date(timeMillis))
                    .expiration(new Date(timeMillis + 5 * 60 * 1000))
                    .claim("id", user.getId())
                    .claim("role", user.getRole())
                    .signWith(SignatureAlgorithm.HS256, key)
                    .compact();

            return new LoginResponse(token, user.getRole());
        } else {
            return null; //lepsze rozwiazanie - rzucic wyjatek
        }
    }

    public IUserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }
}
