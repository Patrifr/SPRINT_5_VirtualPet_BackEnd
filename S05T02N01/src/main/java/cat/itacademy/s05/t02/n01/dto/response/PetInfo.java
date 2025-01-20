package cat.itacademy.s05.t02.n01.dto.response;

import cat.itacademy.s05.t02.n01.enums.Accessories;
import cat.itacademy.s05.t02.n01.enums.Locations;
import cat.itacademy.s05.t02.n01.enums.PetColor;
import cat.itacademy.s05.t02.n01.enums.PetType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetInfo {
    private Integer petId;
    private Integer userId;
    private String userName;
    private String petName;
    private PetType petType;
    private PetColor petColor;
    private int happiness;
    private int hunger;
    private boolean asleep;
    private Locations location;
    private Accessories accessory;
    private LocalDateTime lastInteraction;
}

