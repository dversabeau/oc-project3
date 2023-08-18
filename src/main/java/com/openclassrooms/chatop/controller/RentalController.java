package com.openclassrooms.chatop.controller;

import com.openclassrooms.chatop.DTO.RentalDTO;
import com.openclassrooms.chatop.DTO.RentalsDTO;
import com.openclassrooms.chatop.model.Rental;
import com.openclassrooms.chatop.model.UserInfo;
import com.openclassrooms.chatop.service.PictureService;
import com.openclassrooms.chatop.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.openclassrooms.chatop.service.RentalService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@SecurityRequirement(name = "javainuseapi")
@RequestMapping("/api")
public class RentalController {
    @Autowired
    private RentalService rentalService;

    @Autowired
    private UserService userService;

    @Autowired
    private PictureService pictureService;

    @GetMapping("/rentals")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public RentalsDTO getAll() {
        return rentalService.find();
    }

    @GetMapping("/rentals/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public RentalDTO getOne(@PathVariable Long id) {
        return rentalService.getOne(id);
    }

    @PostMapping("/rentals")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public RentalDTO create(@RequestParam("name") String name,
                            @RequestParam("surface") Integer surface,
                            @RequestParam("price") Integer price,
                            @RequestParam("description") String description,
                            @RequestPart("picture") MultipartFile picture, Authentication authentication) throws IOException {
        RentalDTO rentalDTO = new RentalDTO();
        rentalDTO.setName(name);
        rentalDTO.setSurface(surface);
        rentalDTO.setPrice(price);
        rentalDTO.setDescription(description);
        UserInfo userInfo = userService.getCurrentUser(authentication.getName());
        rentalDTO.setOwnerId(userInfo.getId());
        rentalDTO.setPicture(pictureService.uploadImage(picture));
        RentalDTO dto = rentalService.create(rentalDTO);
        return dto;
    }

    @PutMapping("/rentals/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public void update(@RequestBody RentalDTO rentalDTO) {

        rentalService.update(rentalDTO);
    }
}
