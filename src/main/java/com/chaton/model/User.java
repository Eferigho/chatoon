package com.chaton.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 50)
    private String username;

    @JsonIgnore
    @ToString.Exclude
    @Column(nullable = false, length = 400)
    private String password;

    @Column(nullable = true, length = 100)
    private String first_name;

    @Column(length = 100)
    private  String last_name;

    @Column(nullable = false, length = 200)
    private String email;

    @Column(length = 20)
    private String phone_number;

    @Column(length = 200)
    private  String  user_photo;

    @Column(length = 200)
    private String user_billboard;

    @OneToOne
    private Address address;

    private LocalDate date_join;

    private String role;

    private  Boolean is_active;

    @OneToMany
    private List<Post> posts;

    @OneToMany
    private  List<Comment> comments;

    @OneToMany
    private List<Like> likes;

    public User(@NotNull String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = "default user";
        this.is_active = true;
    }

    public User(@NotNull String username,String email, String password, String role){
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.is_active = true;
    }

    public String getUsername() {
        return username;
    }
}
