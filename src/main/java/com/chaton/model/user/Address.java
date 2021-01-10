package com.chaton.model.user;

import com.chaton.web.config.ApplicationUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToOne
    private ApplicationUser user;

    @Column(length = 20)
    private String houseNumber;

    @Column(length = 100)
    private String streetName;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private  String state;

    @Column(length = 100)
    private String country;


}
