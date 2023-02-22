package com.udesc.droneseta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udesc.droneseta.model.Address;

public interface AddressRepository extends JpaRepository<Address, Integer>{

}
