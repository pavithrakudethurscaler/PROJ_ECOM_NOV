package com.ecom.mainapp.services;

import com.ecom.mainapp.models.Token;
import com.ecom.mainapp.models.User;
import com.ecom.mainapp.repos.TokenRepo;
import com.ecom.mainapp.repos.UserRepo;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.RandomStringUtils;
import java.util.Optional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService {

    private UserRepo userRepo;
    private BCryptPasswordEncoder passwordEncoder;
    private TokenRepo tokenRepo;

    public UserService(UserRepo userRepo, BCryptPasswordEncoder passwordEncoder,
                       TokenRepo tokenRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepo = tokenRepo;
    }

    public User signUp(String name, String email, String password) {
        //Add Validation

        User user = new User();
        user.setEmailAddress(email);
        user.setUsername(name);
        user.setHashedPassword(passwordEncoder.encode(password));

        return userRepo.save(user);

    }

    public Token login(String email, String password) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        if(optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User with Email " +  email + " not found");
        }

        User user = optionalUser.get();

        if(!passwordEncoder.matches(password, user.getHashedPassword())) {
            throw new UsernameNotFoundException("User Email and password are not matching");
        }

        Token token = generateToken(user);
        return tokenRepo.save(token);
    }
    private Token generateToken(User user) {
        Token token = new Token();
        token.setValue(RandomStringUtils.randomAlphanumeric(10));
        token.setExpiryAt(System.currentTimeMillis() + 3600000);
        token.setUser(user);

        return token;
    }

    public User validateToken(String token) {
        /*
        A token is valid if
        1. Token Exist in DB
        2. Token has not expired
        3. Token has not marked as deleted
         */
        Optional<Token> tokenResult = tokenRepo
                .findByValueAndDeletedAndExpiryAtGreaterThan(token, false, System.currentTimeMillis());
        if(tokenResult.isEmpty()) {
            return null;
        }

        return tokenResult.get().getUser();
    }
}
