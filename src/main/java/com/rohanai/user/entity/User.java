package com.rohanai.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USER", schema = "user_service")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
    
    @Column(name = "FIRST_NAME")
    private String firstName;
    
    @Column(name = "LAST_NAME")
    private String lastName;
    
    @Column(name = "FULL_NAME")
    private String fullName;
    
    @Column(name = "EMAIL")
    private String email;
    
    @Column(name = "MOBILE")
    private String mobile;
    
    @Column(name = "PASSWORD")
    private String password;
    
    @Column(name = "ROLE")
    private String role;
}
