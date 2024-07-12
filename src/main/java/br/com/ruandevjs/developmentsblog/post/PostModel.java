package br.com.ruandevjs.developmentsblog.post;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_posts")
public class PostModel {
  @Id
  @GeneratedValue(generator = "UUID")
  private UUID id;

  private String title;
  private String content;
  private String author;

  private UUID idUser;

  @CreationTimestamp
  private LocalDateTime createdAt;
}
