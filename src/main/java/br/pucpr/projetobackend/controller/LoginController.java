package br.pucpr.projetobackend.controller;

import br.pucpr.projetobackend.security.AuthRequest;
import br.pucpr.projetobackend.security.AuthResponse;
import br.pucpr.projetobackend.security.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/login")
@AllArgsConstructor
public class LoginController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody AuthRequest request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

}
