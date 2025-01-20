package cat.itacademy.s05.t02.n01.controller;

import cat.itacademy.s05.t02.n01.dto.request.CreatePetRequest;
import cat.itacademy.s05.t02.n01.dto.request.PetUpdateRequest;
import cat.itacademy.s05.t02.n01.dto.response.PetInfo;
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
    public ResponseEntity<PetInfo> createPet(@RequestBody CreatePetRequest petRequest){
        PetInfo newPet = petService.createPet(petRequest);
        return new ResponseEntity<>(newPet, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN') or @petService.isOwner(#userId, #petId)")
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deletePet(@RequestParam Integer petId, Authentication authentication){
        petService.deletePet(petId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get")
    public ResponseEntity<PetInfo> getOnePetFromUser(@RequestParam Integer userId, @RequestParam Integer petId){
        PetInfo petResponse = petService.getOnePet(userId, petId);
        return ResponseEntity.ok(petResponse);
    }

    @GetMapping("/getUserPets")
    public ResponseEntity<List<PetInfo>> getAllPetsFromUser(@RequestParam Integer userId){
        List<PetInfo> pets = petService.getUserAllPets(userId);
        return ResponseEntity.ok(pets);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity<List<PetInfo>> getAllPets(){
        List<PetInfo> pets = petService.getAllPets();

        return ResponseEntity.ok(pets);
    }

    @PutMapping("/update")
    public ResponseEntity<PetInfo> updatePet(@RequestParam Integer userId, @RequestParam Integer petId,
        @RequestBody PetUpdateRequest petRequest) {
        PetInfo updatedPetResponse = petService.updatePet(petId, userId, petRequest);
        return ResponseEntity.ok(updatedPetResponse);
    }
}
