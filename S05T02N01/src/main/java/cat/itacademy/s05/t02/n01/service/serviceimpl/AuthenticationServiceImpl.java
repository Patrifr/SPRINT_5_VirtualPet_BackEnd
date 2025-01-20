package cat.itacademy.s05.t02.n01.service.serviceimpl;

import cat.itacademy.s05.t02.n01.dto.request.SignInRequest;
import cat.itacademy.s05.t02.n01.dto.request.SignUpRequest;
import cat.itacademy.s05.t02.n01.dto.response.JwtAuthenticationResponse;
import cat.itacademy.s05.t02.n01.enums.Role;
import cat.itacademy.s05.t02.n01.model.User;
import cat.itacademy.s05.t02.n01.repository.UserRepository;
import cat.itacademy.s05.t02.n01.service.AuthenticationService;
import cat.itacademy.s05.t02.n01.service.JwtService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse signUp(SignUpRequest signUpRequest) {
        var user = User.builder()
                .userName(signUpRequest.getUserName())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);

        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder()
                .token(jwt)
                .role(user.getRole())
                .userId(user.getId())
                .build();
    }

    @Override
    public JwtAuthenticationResponse signIn(SignInRequest signInRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    signInRequest.getUserName(), signInRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Authentication failed: Invalid username or password");
        }
        var user = userRepository.findByUserName(signInRequest.getUserName())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user name or password."));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder()
                .token(jwt)
                .role(user.getRole())
                .userId(user.getId())
                .build();
    }
}
