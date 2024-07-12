package br.com.ruandevjs.developmentsblog.post;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IPostInterface extends JpaRepository<PostModel, UUID> {
  List<PostModel> findByIdUser(UUID idUser);
}
