package cat.itacademy.s05.t02.n01.controller;

import cat.itacademy.s05.t02.n01.dto.request.CreatePetRequest;
import cat.itacademy.s05.t02.n01.dto.request.PetUpdateRequest;
import cat.itacademy.s05.t02.n01.dto.response.PetResponse;
import cat.itacademy.s05.t02.n01.dto.response.PetResponseDto;
import cat.itacademy.s05.t02.n01.service.serviceimpl.PetServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;


import java.util.List;

@RestController
@RequestMapping("/api/pet")
@RequiredArgsConstructor
public class PetController {
    private final PetServiceImpl petService;

    @PostMapping("/new")
    public ResponseEntity<PetResponseDto> createPet(@RequestBody CreatePetRequest petRequest){
        PetResponseDto newPet = petService.createPet(petRequest);
        return new ResponseEntity<>(newPet, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deletePet(@RequestParam Integer petId, Authentication authentication){
        petService.deletePet(petId, authentication);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get")
    public ResponseEntity<PetResponseDto> getOnePetFromUser(@RequestParam Integer petId, Authentication authentication){
        PetResponseDto petResponse = petService.getOnePet(petId, authentication);
        return ResponseEntity.ok(petResponse);
    }

    @GetMapping("/getUserPets")
    public ResponseEntity<List<PetResponseDto>> getAllPetsFromUser(@RequestParam Integer userId){
        List<PetResponseDto> pets = petService.getUserAllPets(userId);
        return ResponseEntity.ok(pets);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity<List<PetResponseDto>> getAllPets(){
        List<PetResponseDto> pets = petService.getAllPets();

        return ResponseEntity.ok(pets);
    }

    @PutMapping("/update")
    public ResponseEntity<PetResponseDto> updatePet
    (@RequestParam Integer petId, @RequestBody PetUpdateRequest updateRequest, Authentication authentication) {
        PetResponseDto updatedPetResponse = petService.updatePet(petId, updateRequest, authentication);
        return ResponseEntity.ok(updatedPetResponse);
    }
}
