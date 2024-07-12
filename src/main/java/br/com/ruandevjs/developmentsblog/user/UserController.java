package br.com.ruandevjs.developmentsblog.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/users")
public class UserController {
  @Autowired
  private IUserRepository userRepository;

  @PostMapping("/")
  public ResponseEntity create(@RequestBody UserModel userModel) {
    var userName = this.userRepository.findByUsername(userModel.getUsername());
    var userEmail = this.userRepository.findByEmail(userModel.getEmail());

    if (userName != null || userEmail != null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username / E-mail already exists!");
    }

    var passwordHashred = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());

    userModel.setPassword(passwordHashred);

    var userCreated = this.userRepository.save(userModel);
    return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
  }

  @GetMapping("/")
  public ResponseEntity<List<UserModel>> findAll() {
    var users = this.userRepository.findAll();
    return ResponseEntity.status(HttpStatus.CREATED).body(users);
  }

  @GetMapping("/{id}")
  public ResponseEntity find(@PathVariable UUID id) {
    var user = this.userRepository.findById(id);

    if (user.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    return ResponseEntity.status(HttpStatus.OK).body(user);
  }
}
