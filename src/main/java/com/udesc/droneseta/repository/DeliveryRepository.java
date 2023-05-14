package com.udesc.droneseta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udesc.droneseta.model.Delivery;
import com.udesc.droneseta.model.enumerator.DeliveryStatus;
import java.util.List;
import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Integer>{

    Optional<Delivery> findFirstByOrderByIdDesc();

    List<Delivery> findAllByStatusIn(List<DeliveryStatus> status);

}
