package br.com.ruandevjs.developmentsblog.post;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/posts")
public class PostController {
  @Autowired
  private IPostInterface postInterface;

  @PostMapping("/")
  public ResponseEntity<PostModel> create(@RequestBody PostModel postModel, HttpServletRequest request) {
    var idUser = request.getAttribute("idUser");

    postModel.setIdUser((UUID) idUser);

    var task = this.postInterface.save(postModel);
    return ResponseEntity.status(HttpStatus.CREATED).body(task);
  }

  @GetMapping("/")
  public ResponseEntity<List<PostModel>> findAll(HttpServletRequest request) {
    var idUser = request.getAttribute("idUser");

    var posts = this.postInterface.findByIdUser((UUID) idUser);

    return ResponseEntity.status(HttpStatus.CREATED).body(posts);
  }

  @GetMapping("/{id}")
  public ResponseEntity find(@PathVariable UUID id) {
    var task = this.postInterface.findById(id);

    if (task.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
    }

    return ResponseEntity.status(HttpStatus.OK).body(task);
  }

  @PutMapping("/{id}")
  public ResponseEntity find(@RequestBody PostModel postModel, @PathVariable UUID id, HttpServletRequest request) {
    var idUser = request.getAttribute("idUser");

    postModel.setIdUser((UUID) idUser);
    postModel.setId(id);

    var updatedPost = this.postInterface.save(postModel);
    return ResponseEntity.status(HttpStatus.OK).body(updatedPost);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity delete(@PathVariable UUID id, HttpServletRequest request) {

    this.postInterface.deleteById(id);
    return ResponseEntity.status(HttpStatus.OK).body("Deleted");
  }
}
