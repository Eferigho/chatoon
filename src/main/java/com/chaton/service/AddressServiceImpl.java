package com.chaton.service;

import com.chaton.model.user.Address;
import com.chaton.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class AddressServiceImpl  implements AddressService{

    @Autowired
    AddressRepository addressRepository;

    @Override
    public ResponseEntity<Address> createAddress(String houseNumber, String streetName, String city, String state, String country) {
        return null;
    }

    @Override
    public ResponseEntity<Address> updateAddress(String houseNumber, String streetName, String city, String state, String country) {
        return null;
    }
}
