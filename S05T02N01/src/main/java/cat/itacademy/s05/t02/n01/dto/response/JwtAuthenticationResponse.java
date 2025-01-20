package cat.itacademy.s05.t02.n01.dto.response;

import cat.itacademy.s05.t02.n01.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponse {
    private String token;
    private Role role;
    private Integer userId;
}
