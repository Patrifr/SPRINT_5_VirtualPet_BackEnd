package cat.itacademy.s05.t02.n01.dto.request;

import cat.itacademy.s05.t02.n01.enums.PetColor;
import cat.itacademy.s05.t02.n01.enums.PetType;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePetRequest {
    private Integer userId;
    @NonNull private String petName;
    private PetType petType;
    private PetColor petColor;


}
