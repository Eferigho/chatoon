package com.chaton.service;

import com.chaton.model.user.Address;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface AddressService {
    ResponseEntity<Address> createAddress(String houseNumber, String streetName, String city,
                                      String state, String country);

    ResponseEntity<Address> updateAddress(String houseNumber, String streetName, String city,
                                          String state, String country);
}
