package br.pucpr.projetobackend.security;

import br.pucpr.projetobackend.model.User;
import br.pucpr.projetobackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse authenticate(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email ou senha inválidos"));

        if (!passwordEncoder.matches(request.getSenha(), user.getSenha())) {
            throw new RuntimeException("Email ou senha inválidos");
        }

        UserAuthentication userAuthentication = new UserAuthentication();
        userAuthentication.setEmail(user.getEmail());
        userAuthentication.setRole(user.getRole());

        String jwtToken = jwtService.generateToken(userAuthentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(jwtToken);
        authResponse.setEmail(user.getEmail());
        authResponse.setExpires(new Date(System.currentTimeMillis() + 1000 * 60 * 24));

        return authResponse;
    }
}
