package br.com.example.apirestfulsb.services;

import br.com.example.apirestfulsb.models.User;
import br.com.example.apirestfulsb.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public User findById(Long id) {
    Optional<User> user = userRepository.findById(id);
    return user.orElseThrow(() -> new ObjectNotFoundException("User not found!"));
  }

  @Transactional
  public User create(User user) {
    user.setId(null);
    return userRepository.save(user);
  }

  @Transactional
  public User update(User user) {
    User newUser = findById(user.getId());
    newUser.setPassword(user.getPassword());
    return userRepository.save(newUser);
  }

//  public void delete(Long id) {
//    Optional<User> user = userRepository.findById(id);
////    if (user != null) {
////      userRepository.deleteById(id);
////    } else if (user.getTasks().size() > 0) {
////      throw new DataBindingViolationException("Failed to save entity with associated data");
////    }
//    user.orElseThrow(() -> new DataBindingViolationException("Failed to save entity with associated data"));
//  }

  public void delete(Long id) {
    Optional<User> user = userRepository.findById(id);
    if (user.isPresent()) {
      User userEntity = user.get();
      if (!userEntity.getTasks().isEmpty()) {
        throw new DataBindingViolationException("Failed to delete entity with associated data");
      }
      userRepository.deleteById(id);
    } else {
      throw new ObjectNotFoundException("User not found");
    }
  }

}
