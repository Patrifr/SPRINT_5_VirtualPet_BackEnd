package cat.itacademy.s05.t02.n01.service;

import cat.itacademy.s05.t02.n01.dto.request.CreatePetRequest;
import cat.itacademy.s05.t02.n01.dto.request.PetUpdateRequest;
import cat.itacademy.s05.t02.n01.dto.response.PetResponse;
import cat.itacademy.s05.t02.n01.dto.response.PetInfo;
import cat.itacademy.s05.t02.n01.model.Pet;

import java.util.List;

public interface PetService {
    PetInfo createPet (CreatePetRequest petRequest);
    void deletePet (Integer petId);
    PetInfo getOnePet (Integer userId, Integer id);
    List<PetInfo> getUserAllPets(Integer userId);
    List<PetInfo> getAllPets ();
    PetInfo updatePet(Integer petId, Integer userId, PetUpdateRequest updateRequest);

}
