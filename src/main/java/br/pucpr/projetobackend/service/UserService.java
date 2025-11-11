package br.pucpr.projetobackend.service;

import br.pucpr.projetobackend.dto.RegisterDTO;
import br.pucpr.projetobackend.exception.DuplicateResourceException;
import br.pucpr.projetobackend.model.User;
import br.pucpr.projetobackend.repository.UserRepository;
import br.pucpr.projetobackend.security.Role;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    
    private UserRepository repository;
    private PasswordEncoder passwordEncoder;

    public User register(RegisterDTO registerDTO) {
        if (!registerDTO.getSenha().equals(registerDTO.getConfirmarSenha())) {
            throw new IllegalArgumentException("As senhas não coincidem");
        }

        if (repository.existsByEmail(registerDTO.getEmail())) {
            throw new DuplicateResourceException("Email já cadastrado", registerDTO.getEmail());
        }

        User user = new User();
        user.setNome(registerDTO.getNome());
        user.setEmail(registerDTO.getEmail());
        user.setSenha(passwordEncoder.encode(registerDTO.getSenha()));
        user.setRole(Role.USER);

        return repository.save(user);
    }

    public User save(User user) {
        if (user.getSenha() != null && !user.getSenha().startsWith("$2a$")) {
            user.setSenha(passwordEncoder.encode(user.getSenha()));
        }
        return repository.save(user);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public User getId(Integer id) {
        Optional<User> user = repository.findById(id);
        return user.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public User update(User user) {
        Optional<User> newUser = repository.findById(user.getId());
        if (newUser.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado");
        }
        updateUser(newUser, user);
        return repository.save(newUser.get());
    }

    private void updateUser(Optional<User> newUser, User user) {
        newUser.get().setNome(user.getNome());
        if (user.getEmail() != null) {
            newUser.get().setEmail(user.getEmail());
        }
    }
}
