package com.engineerindigest.demo_project.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String token;
    private Instant expiryDate;
    private boolean revoked;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
