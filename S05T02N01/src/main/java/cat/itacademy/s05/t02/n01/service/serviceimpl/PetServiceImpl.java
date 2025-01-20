package cat.itacademy.s05.t02.n01.service.serviceimpl;

import cat.itacademy.s05.t02.n01.dto.request.CreatePetRequest;
import cat.itacademy.s05.t02.n01.dto.request.PetUpdateRequest;
import cat.itacademy.s05.t02.n01.dto.response.PetResponse;
import cat.itacademy.s05.t02.n01.dto.response.PetInfo;
import cat.itacademy.s05.t02.n01.enums.Accessories;
import cat.itacademy.s05.t02.n01.enums.Locations;
import cat.itacademy.s05.t02.n01.exception.custom.InvalidPetInteraction;
import cat.itacademy.s05.t02.n01.exception.custom.PetNotFoundException;
import cat.itacademy.s05.t02.n01.exception.custom.UserNotFoundException;
import cat.itacademy.s05.t02.n01.model.Pet;
import cat.itacademy.s05.t02.n01.model.User;
import cat.itacademy.s05.t02.n01.repository.PetRepository;
import cat.itacademy.s05.t02.n01.repository.UserRepository;
import cat.itacademy.s05.t02.n01.service.PetService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {
    private static final int INITIAL_HAPPINESS = 50;
    private static final int INITIAL_HUNGER = 50;
    private static final int UPDATE_RATE = 3600000;

    private final UserRepository userRepository;
    private final PetRepository petRepository;

    @Scheduled(fixedRate = UPDATE_RATE)
    private void scheduledPetsBehaviourUpdate() {
        updateAllPetsStatus(this::statusUpdate);
    }

    @Scheduled(fixedRate = 3600000)
    public void checkPetsSleepStatus() {
        List<Pet> pets = petRepository.findAll();
        LocalDateTime now = LocalDateTime.now();
        pets.forEach(pet -> {
            if (Duration.between(pet.getLastInteraction(), now).toHours() > 24) {
                pet.setAsleep(true);
                petRepository.save(pet);
            }
        });
    }

    private void updateAllPetsStatus(Consumer<Pet> updatePetStatus) {
        List<Pet> pets = petRepository.findAll();
        pets.forEach(updatePetStatus);
        petRepository.saveAll(pets);
    }

    private void statusUpdate(Pet pet) {
        pet.hourlyStatusChanges();
        petRepository.save(pet);
    }

    @PostConstruct
    public void initPetStatusOnStartup() {
        List<Pet> pets = petRepository.findAll();
        pets.forEach(this::statusUpdate);
    }


    @Override
    public PetInfo createPet(CreatePetRequest petRequest) {
        User user = userRepository.findById(petRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not Found."));

        Pet newPet = new Pet();
        newPet.setPetName(petRequest.getPetName());
        newPet.setPetType(petRequest.getPetType());
        newPet.setPetColor(petRequest.getPetColor());
        newPet.setHappiness(INITIAL_HAPPINESS);
        newPet.setHunger(INITIAL_HUNGER);
        newPet.setAsleep(false);
        newPet.setUser(user);
        newPet.setLocation(Locations.FOREST);
        newPet.setAccessory(Accessories.NONE);
        newPet.setCreatedAt(LocalDateTime.now());
        newPet.setLastInteraction(LocalDateTime.now());


        petRepository.save(newPet);

        return mapPetInfo(newPet);
    }

    @Override
    public void deletePet(Integer petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new PetNotFoundException("Pet with id " + petId + " not found."));

        petRepository.delete(pet);
    }

    @Override
    public PetInfo getOnePet(Integer userId, Integer petId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + ", not found."));
        Pet pet = petRepository.findByIdAndUser_Id(petId, userId)
                .orElseThrow(() -> new PetNotFoundException("User with id " + userId + ", does not have" +
                        "any pet with id " + petId));
        return mapPetInfo(pet);
    }

    @Override
    public List<PetInfo> getUserAllPets(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + ", not found."));

        List<Pet> pets = petRepository.findByUser(user);

        if(pets.isEmpty()){
            throw new PetNotFoundException("No pets found for user with id " + userId);
        }

        return pets.stream().map(this::mapPetInfo).toList();
    }

    @Override
    public List<PetInfo> getAllPets() {
        List<Pet> allPets = petRepository.findAll();

        if(allPets.isEmpty()){
            throw new PetNotFoundException("No pets found");
        }

        return allPets.stream().map(this::mapPetInfo).toList();
    }

    @Override
    public PetInfo updatePet(Integer petId, Integer userId, PetUpdateRequest updateRequest) {
        Pet pet = petRepository.findByIdAndUser_Id(petId, userId)
                .orElseThrow(() -> new PetNotFoundException("User with id " + userId +
                        ", does not have any pet with id " + petId));

        if (pet.isAsleep() && updateRequest.getPetInteraction() != null) {
            pet.setAsleep(false);
        }

        if (updateRequest.getLocation() != null) {
            pet.setLocation(updateRequest.getLocation());
            pet.setLastInteraction(LocalDateTime.now());
        }
        if (updateRequest.getAccessory() != null) {
            pet.setAccessory(updateRequest.getAccessory());
            pet.setLastInteraction(LocalDateTime.now());
        }

        if (updateRequest.getPetInteraction() != null) {
            switch (updateRequest.getPetInteraction()) {
                case PET:
                    pet.setHappiness(pet.getHappiness() + 20);
                    pet.setLastInteraction(LocalDateTime.now());
                    break;

                case FEED:
                    pet.setHappiness(pet.getHappiness() + 10);
                    pet.setHunger(pet.getHunger() - 20);
                    pet.setLastInteraction(LocalDateTime.now());
                    break;

                default:
                    throw new InvalidPetInteraction("Invalid interaction type");
            }
        }

        petRepository.save(pet);
        return mapPetInfo(pet);
    }

    //TODO --> map petDTO
    private PetInfo mapPetInfo(Pet newPet) {
        PetInfo petInfo = new PetInfo();
        petInfo.setPetId(newPet.getId());
        petInfo.setPetName(newPet.getPetName());
        petInfo.setPetType(newPet.getPetType());
        petInfo.setPetColor(newPet.getPetColor());
        petInfo.setHappiness(newPet.getHappiness());
        petInfo.setHunger(newPet.getHunger());
        petInfo.setAsleep(newPet.isAsleep());
        petInfo.setLocation(newPet.getLocation());
        petInfo.setAccessory(newPet.getAccessory());
        petInfo.setLastInteraction(newPet.getLastInteraction());
        petInfo.setUserName(newPet.getUser().getUsername());
        petInfo.setUserId(newPet.getUser().getId());
        return petInfo;
    }

    public boolean isOwner(Integer userId, Integer petId) {
        Pet pet = petRepository.findById(petId).orElseThrow(() -> new PetNotFoundException("Pet not found"));
        return pet.getUser().getId().equals(userId);
    }
}
