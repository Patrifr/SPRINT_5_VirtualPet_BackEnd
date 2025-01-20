package cat.itacademy.s05.t02.n01.dto.request;

import cat.itacademy.s05.t02.n01.enums.Accessories;
import cat.itacademy.s05.t02.n01.enums.Locations;
import cat.itacademy.s05.t02.n01.enums.PetInteraction;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetUpdateRequest {
    private PetInteraction petInteraction;
    private Accessories accessory;
    private Locations location;
}
