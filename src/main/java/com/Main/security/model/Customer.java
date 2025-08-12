package com.Main.security.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "customerId")
    private Long id;

    @Column(nullable = false)
    private String name;

    private String email;
    private String pwd;
    private String mobileNumber;
    private String role;
    private LocalDateTime createdAt;
    @PrePersist
    public void created(){
        this.createdAt = LocalDateTime.now();
    }


}
