package br.com.example.apirestfulsb.repositories;

import br.com.example.apirestfulsb.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
