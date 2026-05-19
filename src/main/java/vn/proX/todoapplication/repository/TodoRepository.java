package vn.proX.todoapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.proX.todoapplication.entity.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

}
