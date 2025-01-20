package cat.itacademy.s05.t02.n01.repository;

import cat.itacademy.s05.t02.n01.model.Pet;
import cat.itacademy.s05.t02.n01.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Integer> {
    Optional<Pet> findByIdAndUser_Id(Integer petId, Integer userId);
    List<Pet> findByUser(User user);

}
