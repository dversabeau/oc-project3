package com.openclassrooms.chatop.DTO;

import com.openclassrooms.chatop.model.Rental;

import java.util.List;

public class RentalsDTO {
    List<RentalDTO> rentals;

    public List<RentalDTO> getRentals() {
        return rentals;
    }

    public void setRentals(List<RentalDTO> rentals) {
        this.rentals = rentals;
    }
}
