package br.com.example.apirestfulsb.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(includeFieldNames = false)
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Long id;

  @Column(nullable = false, unique = true)
  @NotBlank
  @EqualsAndHashCode.Include
  private String name;

  @Column(nullable = false)
  @JsonProperty(access = Access.WRITE_ONLY)
  @Size(min = 8, max = 60)
  @NotBlank
  private String password;

  @OneToMany(mappedBy = "user")
  @JsonProperty(access = Access.WRITE_ONLY)
  private List<Task> tasks = new ArrayList<>();

}
