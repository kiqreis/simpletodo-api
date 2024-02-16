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
@ToString(includeFieldNames = false)
@EqualsAndHashCode(of = "id")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  private String name;

  @NotBlank
  @Size(min = 8, max = 60)
  @JsonProperty(access = Access.WRITE_ONLY)
  private String password;

  @OneToMany(mappedBy = "user")
  @JsonProperty(access = Access.WRITE_ONLY)
  private List<Task> tasks = new ArrayList<>();

}
