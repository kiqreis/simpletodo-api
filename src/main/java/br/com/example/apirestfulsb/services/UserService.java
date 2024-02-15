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
    return user.orElseThrow(() -> new RuntimeException("User not found!"));
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

  public void delete(Long id) {
    User user = findById(id);
    if (user != null) {
      userRepository.deleteById(id);
    }
  }

}
