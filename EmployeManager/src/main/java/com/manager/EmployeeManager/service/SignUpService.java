package com.manager.EmployeeManager.service;

import com.manager.EmployeeManager.component.RandomStringFactory;
import com.manager.EmployeeManager.component.mailer.SignUpMailer;
import com.manager.EmployeeManager.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class SignUpService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    private static final int TOKEN_LENGTH = 15;

    private SignUpMailer signUpMailer;

    private String tokenOfUser;

    @Autowired
    public SignUpService(UserService userDetails, PasswordEncoder passwordEncoder, SignUpMailer signUpMailer) {
        this.userService = userDetails;
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
        userService.saveUser(user);
        signUpMailer.sendConfirmationLink(user.getEmail(),token);
        tokenOfUser = token;
        return user;
    }

    public User signUpAdmin(User user){
        Assert.isNull(user.getId(), "Can't sign up given user, it already has set id. User: " + user.toString());
        String usernameOfSigningUser = user.getUsername();

        String p = passwordEncoder.encode(user.getPassword());
        user.setPassword(p);

        String token = RandomStringFactory.getRandomString(TOKEN_LENGTH);

        user.setConfirmationToken(token);

        user.setEnabled(true);
        userService.saveUser(user);
        tokenOfUser = token;
        return user;
    }
    public User signUpExampleUser(User user){
        Assert.isNull(user.getId(), "Can't sign up given user, it already has set id. User: " + user.toString());
        String usernameOfSigningUser = user.getUsername();

        String p = passwordEncoder.encode(user.getPassword());
        user.setPassword(p);

        String token = RandomStringFactory.getRandomString(TOKEN_LENGTH);

        user.setConfirmationToken(token);

        user.setEnabled(true);
        userService.saveUser(user);
        tokenOfUser = token;
        return user;
    }

    public String getTokenOfUser() {
        return tokenOfUser;
    }
}
