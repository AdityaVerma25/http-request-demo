package com.engineerindigest.demo_project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringExclude;

import java.io.Serializable;

@Data
@Entity
@Table(name = "profiles")
public class Profile  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String bio;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;

}
