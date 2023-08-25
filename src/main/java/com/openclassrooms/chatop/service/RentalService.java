package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.DTO.MessageDTO;
import com.openclassrooms.chatop.DTO.RentalDTO;
import com.openclassrooms.chatop.DTO.RentalsDTO;
import com.openclassrooms.chatop.model.Message;
import com.openclassrooms.chatop.model.Rental;
import com.openclassrooms.chatop.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    public RentalsDTO find() {
        List<Rental> rentals = rentalRepository.findAll();
        List<RentalDTO> rentalDTOs = new ArrayList<RentalDTO>();
        for (Rental rental : rentals) {
            RentalDTO rentalDTO = convertEntityToDTO(rental);
            rentalDTOs.add(rentalDTO);
        }
        RentalsDTO rentalDTO = new RentalsDTO();
        rentalDTO.setRentals(rentalDTOs);
        return rentalDTO;
    }

    public RentalDTO getOne(Long id) {
        Rental rental = rentalRepository.getOne(id);
        RentalDTO rentalDTO = convertEntityToDTO(rental);
        return rentalDTO;
    }

    public RentalDTO create(RentalDTO rentalDTO) {
        Rental rental = convertDTOToEntity(rentalDTO);
        Rental rental1 = rentalRepository.save(rental);
        RentalDTO dto = convertEntityToDTO(rental1);
        return dto;
    }

    public RentalDTO update(RentalDTO rentalDTO) {
        Rental rental = convertDTOToEntity(rentalDTO);
        Rental rental1 = rentalRepository.save(rental);
        RentalDTO dto = convertEntityToDTO(rental1);
        return dto;
    }

    private RentalDTO convertEntityToDTO(Rental rental) {
        RentalDTO rentalDTO = new RentalDTO();

        rentalDTO.setId(rental.getId());
        rentalDTO.setName(rental.getName());
        rentalDTO.setSurface(rental.getSurface());
        rentalDTO.setPrice(rental.getPrice());
        rentalDTO.setPicture(rental.getPicture());
        rentalDTO.setDescription(rental.getDescription());
        rentalDTO.setOwnerId(rental.getOwnerId());
        rentalDTO.setCreatedAt(rental.getCreatedAt());
        rentalDTO.setUpdatedAt(rental.getUpdatedAt());
        return rentalDTO;
    }

    private Rental convertDTOToEntity(RentalDTO rentalDTO) {
        Rental rental = new Rental();

        rental.setId(rentalDTO.getId());
        rental.setName(rentalDTO.getName());
        rental.setSurface(rentalDTO.getSurface());
        rental.setPrice(rentalDTO.getPrice());
        rental.setPicture(rentalDTO.getPicture());
        rental.setDescription(rentalDTO.getDescription());
        rental.setOwnerId(rentalDTO.getOwnerId());
        rental.setCreatedAt(rentalDTO.getCreatedAt());
        rental.setUpdatedAt(rentalDTO.getUpdatedAt());
        return rental;
    }
}
