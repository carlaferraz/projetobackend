package br.pucpr.projetobackend.service;

import br.pucpr.projetobackend.model.User;
import br.pucpr.projetobackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import br.pucpr.projetobackend.model.User;
import br.pucpr.projetobackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public void save(User user){
        repository.save(user);
    }

    public void delete(Integer id){
        repository.deleteById(id);
    }

    public User getId(Integer id){
        Optional<User> user = repository.findById(id);
        return user.get();
    }

    public List<User> getAll(){
        return repository.findAll();
    }

    public User update(User user){
        Optional<User> newUser = repository.findById(user.getId());
        updateUser(newUser, user);
        return repository.save(newUser.get());
    }

    private void updateUser(Optional<User> newUser, User user){
        newUser.get().setNome(user.getNome());
    }
}
