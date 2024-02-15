package br.com.example.apirestfulsb.repositories;

import br.com.example.apirestfulsb.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

  List<Task> findByUser_Id(Long id);

}
