package com.codehustle.chatterpark.entity;

import com.codehustle.chatterpark.enums.Gender;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.type.YesNoConverter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;

@Entity
@Table(name = "users",schema = "chatterspark")
@Data
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id",nullable = false,unique = true)
    private Long userId;

    @Column(name = "user_name",nullable = false,unique = true)
    private String username;

    @Column(name = "user_email",nullable = false,unique = true)
    private String userEmail;

    @Column(name = "user_password",nullable = false)
    private String password;

    @Column(name = "date_of_birth",nullable = false,columnDefinition = "DATE")
    private LocalDate dob;

    @Column(name = "age",nullable = false)
    private Integer age;

    @Column(name = "gender",nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "online_status")
    @Convert(converter = YesNoConverter.class)
    private boolean isOnline;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
