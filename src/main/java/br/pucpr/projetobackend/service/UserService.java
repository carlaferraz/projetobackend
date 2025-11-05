package br.pucpr.projetobackend.service;

import br.pucpr.projetobackend.model.User;
import br.pucpr.projetobackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}
