package br.pucpr.projetobackend.service;

import br.pucpr.projetobackend.model.Author;
import br.pucpr.projetobackend.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository repository;

    public Author save(Author author){
        return repository.save(author);
    }

    public List<Author> getAll(){
        return repository.findAll();
    }

    public Author getId(Integer id){
        Optional<Author> a = repository.findById(id);
        return a.orElse(null);
    }

    public Author update(Author author){
        Optional<Author> existing = repository.findById(author.getId());
        if (existing.isEmpty()) return null;
        var e = existing.get();
        e.setName(author.getName());
        e.setBio(author.getBio());
        return repository.save(e);
    }

    public void delete(Integer id){
        repository.deleteById(id);
    }
}
