//package br.pucpr.projetobackend;
//
//import br.pucpr.projetobackend.model.User;
//import br.pucpr.projetobackend.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class UserService {
//    @Autowired
//    private UserRepository repository;
//
//    public void create(User obj){
//        repository.save(obj);
//    }
//
//    public void delete(Integer id){
//        repository.deleteById(id);
//    }
//
//    public User getId(Integer id){
//        Optional<User> obj = repository.findById(id);
//        return obj.get();
//    }
//
//    public List<User> getAll(){
//        return repository.findAll();
//    }
//
//    public User update(User obj){
//        Optional<User> newObj = repository.findById(obj.getId());
//        updateUser(newObj, obj);
//        return repository.save(newObj.get());
//    }
//
//    private void updateUser(Optional<User> newObj, User obj){
//        newObj.get().setNome(obj.getNome());
//    }
//}
