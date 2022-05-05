package com.manager.EmployeManager.service;

import com.manager.EmployeManager.entity.User;
import com.manager.EmployeManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<User> getUsers(){
        return userRepository.findAll();
    }
    public final User findUserById(Integer id){
        User user = userRepository.findAll().stream().
                filter(user1 -> user1.getId().equals(id)).findFirst().get();
        return user;
    }
    public List<User> findUsersByRole(String role){
        List<User> list
                = userRepository.findAll().stream().filter(user -> user.getRole().equals(role)).
                collect(Collectors.toList());
        return list;
    }
    public void saveUser(String password, String name, String lastName, String role, Integer telephoneNumber,
                         String email){
        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setRole(role);
        user.setTelephoneNumber(telephoneNumber);
        user.setEmail(email);

        userRepository.save(user);
    }

    public void saveUser(User user){
        userRepository.save(user);
    }


    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Assert.notNull(username, "Username cannot be null");
            Optional<User> user =
                    userRepository.findAll().stream().
                            filter(user1 -> user1.getUsername().equals(username)).findFirst();

            return user.get();
        } catch (UsernameNotFoundException e) {
            return null;

        }
    }
    public User getUserByConfirmationToken(String token){
        Assert.notNull(token,"Token cannot be null");
        List<User> users = userRepository.findAll();
        Optional<User> user =
                users.stream().filter(user1 -> token.equals(user1.getConfirmationToken())).findFirst();
        if(user.isPresent()){
            User user2 = user.get();
            return user2;
        }
        return null;
    }
    public void changeEnabledOnUserByName(String username, boolean enabled){
        loadUserByUsername(username).setEnabled(enabled);
    }
    public void updateUser(User user){
        Integer idOfUser = user.getId();
        userRepository.deleteById(idOfUser);
        userRepository.save(user);
    }
    public final Integer getIdOfUserByHisUsername(String username){
        User user = getUsers().stream().filter(user1 -> user1.getUsername().equals(username)).findFirst().get();
        System.out.println("QQQ "+ user.getId());
        return user.getId();
    }

    public User getUserByUsername(String username) {
        User user = getUsers().stream().filter(user1 -> user1.getUsername().equals(username)).findFirst().get();
        return user;
    }
}
