package br.com.ruandevjs.developmentsblog.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<UserModel, UUID> {
  public UserModel findByUsername(String username);

  public UserModel findByEmail(String username);

}
