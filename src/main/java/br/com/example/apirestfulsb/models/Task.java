package br.com.example.apirestfulsb.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "tasks")
@Data
@ToString(includeFieldNames = false)
@EqualsAndHashCode(of = "id")
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  @NotNull
  private User user;

  @NotBlank
  @Column(nullable = false)
  @Size(min = 1, max = 255)
  private String description;

}
