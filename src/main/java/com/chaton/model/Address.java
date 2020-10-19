package com.chaton.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String house_number;

    @Column(length = 100)
    private String street_name;

    @Column(length = 100)
    private String town_name;

    @Column(length = 100)
    private String local_gov_area;

    @Column(length = 100)
    private  String state;

    @Column(length = 100)
    private String country;

    public Address(){}

    public Address(@NotNull Long id, String house_number, String street_name,
                   String town_name, String local_gov_area, String state, String country){
        this.id = id;
        this.house_number = house_number;
        this.street_name = street_name;
        this.town_name = town_name;
        this.local_gov_area = local_gov_area;
        this.state = state;
        this.country = country;
    }

}
