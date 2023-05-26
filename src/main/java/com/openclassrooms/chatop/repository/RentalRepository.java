package com.openclassrooms.chatop.repository;

import com.openclassrooms.chatop.DTO.RentalDTO;
import com.openclassrooms.chatop.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

    List<Rental> findAll();

    Rental getOne(Long id);

    Rental save(Rental rental);
}
