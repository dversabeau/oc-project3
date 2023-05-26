package com.openclassrooms.chatop.controller;

import com.openclassrooms.chatop.DTO.RentalDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.openclassrooms.chatop.service.RentalService;

import java.util.List;

@RestController
public class RentalController {
    @Autowired
    private RentalService rentalService;

    @GetMapping("/api/rentals")
    public List<RentalDTO> getAll(){
        return rentalService.find();
    }

    @GetMapping("/api/rentals/{id}")
    public RentalDTO getOne(@PathVariable Long id){
        return rentalService.getOne(id);
    }

    @PostMapping("/api/rentals")
    public void create(@RequestBody RentalDTO rentalDTO){
        rentalService.create(rentalDTO);
    }

    @PutMapping("/api/rentals/{id}")
    public void update(@RequestBody RentalDTO rentalDTO){
        rentalService.update(rentalDTO);
    }
}
