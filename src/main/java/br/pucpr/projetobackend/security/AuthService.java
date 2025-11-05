package br.pucpr.projetobackend.security;

import br.pucpr.projetobackend.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class AuthService {

    //private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse authenticate(AuthRequest request) {
        var user = new User();
        user.setEmail(request.getEmail());
        user.setSenha(request.getPassword());

        //TODO
        //validar se a senha é correta

        UserAuthentication userAuthentication = new UserAuthentication();
        userAuthentication.setEmail(request.getEmail());

        String jwtToken = jwtService.generateToken(userAuthentication); //cria o token

        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(jwtToken);
        authResponse.setEmail(user.getEmail());
        authResponse.setExpires(new Date(System.currentTimeMillis() + 1000 * 60 * 24));

        return authResponse;
    }


}
