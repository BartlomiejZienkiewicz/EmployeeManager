package com.manager.EmployeeManager.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import javax.persistence.*;

@Entity
@Table(name="users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "role")
    private String role;

    @Column(name = "name")
    private String name;

    @Column(name ="last_name")
    private String lastName;

    @Column(name="email")
    private String email;

    @Column(name = "telephone_number")
    private Integer telephoneNumber;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled = false;

    @Column(name = "confirmation_token")
    private String confirmationToken;

    public User() {
        this.name = "Not set";
        this.lastName = "Not set";
    }

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.enabled = false;
        this.name = "Not set";
        this.lastName = "Not set";
    }


    public User(String username, String password, String role, String email){
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.enabled = false;
        this.name = "Not set";
        this.lastName = "Not set";
    }

    public User(String password, String username, String name, String lastName, String role, Integer telephoneNumber,
                String email){
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.role = role;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
        this.enabled = false;
        this.name = "Not set";
        this.lastName = "Not set";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public final String getRole() {
        return role;
    }

    public final void setRole(String role) {
        this.role = role;
    }

    public final  String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final String getLastName() {
        return lastName;
    }

    public final void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public final String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public final String getEmail() {
        return this.email;
    }

    public final void setEmail(String email) {
        this.email = email;
    }

    public final Integer getTelephoneNumber() {
        return telephoneNumber;
    }

    public final void setTelephoneNumber(Integer telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public final boolean isEnabled() {
        return enabled;
    }

    public final void enable() {
        this.enabled = true;

    }
    public final void disable(){
        this.enabled = false;
    }

    public final void setUsername(String username) {
        this.username = username;
    }

    public final void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public final String getConfirmationToken() {
        return this.confirmationToken;
    }

    public final void setConfirmationToken(String confirmation_token) {
        this.confirmationToken = confirmation_token;
    }



    @Override
    public final String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", telephoneNumber=" + telephoneNumber +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
