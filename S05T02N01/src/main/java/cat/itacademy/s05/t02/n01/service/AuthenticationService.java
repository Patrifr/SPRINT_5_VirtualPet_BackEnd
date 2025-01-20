package cat.itacademy.s05.t02.n01.service;

import cat.itacademy.s05.t02.n01.dto.request.SignInRequest;
import cat.itacademy.s05.t02.n01.dto.request.SignUpRequest;
import cat.itacademy.s05.t02.n01.dto.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signUp(SignUpRequest signUpRequest);
    JwtAuthenticationResponse signIn(SignInRequest signInRequest);

}
