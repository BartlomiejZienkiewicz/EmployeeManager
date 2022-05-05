package com.test.service;

import com.test.component.RandomStringFactory;
import com.test.component.mailer.SignUpMailer;
import com.test.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class SignUpService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private InMemoryUserDetailsService userDetailsService;

    private static final int TOKEN_LENGTH = 15;

    private SignUpMailer signUpMailer;

    private String tokenOfUser;

    @Autowired
    public SignUpService(InMemoryUserDetailsService userDetails, PasswordEncoder passwordEncoder, SignUpMailer signUpMailer) {
        this.userDetailsService = userDetails;
        this.passwordEncoder = passwordEncoder;
        this.signUpMailer = signUpMailer;
    }

    public User signUpUser(User user){
        Assert.isNull(user.getId(), "Can't sign up given user, it already has set id. User: " + user.toString());
        String usernameOfSigningUser = user.getUsername();

       String p = passwordEncoder.encode(user.getPassword());
        user.setPassword(p);

        String token = RandomStringFactory.getRandomString(TOKEN_LENGTH);



        user.setConfirmationToken(token);

        user.setEnabled(false);
        userDetailsService.addUser(user);
        signUpMailer.sendConfirmationLink(user.getEmailAddress(),token);
        tokenOfUser = token;
        return user;
    }

    public String getTokenOfUser() {
        return tokenOfUser;
    }
}
