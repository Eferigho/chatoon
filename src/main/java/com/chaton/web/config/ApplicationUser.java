package com.chaton.web.config;

import com.chaton.model.user.Gender;
import com.chaton.web.config.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;


@Entity
@Data
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationUser  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private  String username;

    @Column(nullable = false, length = 200, unique = true)
    private   String email;

    @Column(nullable = false, length = 400)
    private  String password;

    @Column(length = 100)
    private String firstName;

    @Column(length = 100)
    private  String lastName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(length = 20)
    private String phoneNumber;

    @Column(length = 200)
    private  String  profileImage;

    private String userBillboard;

    private Date dateOfBirth;


    @CreationTimestamp
    private Timestamp dateJoin;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLES",
            joinColumns = {
                    @JoinColumn(name = "USER_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "ROLE_ID") })
    private Set<ApplicationRole> roles;


}
