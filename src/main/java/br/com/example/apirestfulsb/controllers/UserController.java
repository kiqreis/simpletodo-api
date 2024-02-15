package br.com.example.apirestfulsb.controllers;

import br.com.example.apirestfulsb.models.User;
import br.com.example.apirestfulsb.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/{id}")
  public ResponseEntity<User> findById(@PathVariable(value = "id") Long id) {
    User user = userService.findById(id);
    return ResponseEntity.status(HttpStatus.OK).body(user);
  }

  @PostMapping
  public ResponseEntity<User> create(@Valid @RequestBody User user) {
    return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(user));
  }

  @PutMapping("/{id}")
  public ResponseEntity<User> update(@Valid @RequestBody User user, @PathVariable(value = "id") Long id) {
    user.setId(id);
    return ResponseEntity.status(HttpStatus.OK).body(userService.update(user));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {
    userService.delete(id);
    return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
  }

}
