package org.example.springtodo.repository;

import org.example.springtodo.entity.Todo;
import org.example.springtodo.model.TodoStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findTodosByStatus(TodoStatus status);
}
