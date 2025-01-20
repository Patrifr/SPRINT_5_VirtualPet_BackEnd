package cat.itacademy.s05.t02.n01.service;

import cat.itacademy.s05.t02.n01.dto.request.CreatePetRequest;
import cat.itacademy.s05.t02.n01.dto.request.PetUpdateRequest;
import cat.itacademy.s05.t02.n01.dto.response.PetResponseDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface PetService {
    PetResponseDto createPet (CreatePetRequest petRequest);
    void deletePet (Integer petId, Authentication authentication);
    PetResponseDto getOnePet (Integer id, Authentication authentication);
    List<PetResponseDto> getUserAllPets(Integer userId);
    List<PetResponseDto> getAllPets ();
    PetResponseDto updatePet(Integer petId, PetUpdateRequest updateRequest, Authentication authentication);

}
