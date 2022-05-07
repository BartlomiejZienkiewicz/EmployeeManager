package com.manager.EmployeeManager.component;

import com.manager.EmployeeManager.entity.User;
import com.manager.EmployeeManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class InMemoryAuthenticationProvider implements AuthenticationProvider {


    private UserService userService;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public InMemoryAuthenticationProvider(UserService userService,@Lazy PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        Object credentials = authentication.getCredentials();
        Assert.notNull(name, "Name cannot be null");
        Assert.notNull(credentials, "Credentials cannot be null");

        if(!(credentials instanceof String)){
            return null;
        }


        String password = credentials.toString();
        User userDetails = userService.loadUserByUsername(name);
        boolean passwordMatch = passwordEncoder.matches(password, userDetails.getPassword());


        if(!password.equals(userDetails.getPassword()) ){
            if(!passwordMatch){
               return null;
            }

        }

        User user = userService.loadUserByUsername(name);

        boolean isUserEnabled = user.isEnabled();
        if(isUserEnabled == false){
            System.out.println(1);
            return null;
        }


        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(name, password, userDetails.getAuthorities());

        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
