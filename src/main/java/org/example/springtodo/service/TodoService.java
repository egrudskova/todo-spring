package org.example.springtodo.service;

import org.example.springtodo.dto.TodoDto;

import java.util.List;

public interface TodoService {
    List<TodoDto> getAllTodos();

    TodoDto saveTodo(TodoDto todo);

    TodoDto getTodoById(long id);

    TodoDto updateTodo(long id, TodoDto todo);

    void deleteTodo(long id);

    List<TodoDto> getTodosByStatus(String status);

    List<TodoDto> getAllTodosSorted(String mode);
}
