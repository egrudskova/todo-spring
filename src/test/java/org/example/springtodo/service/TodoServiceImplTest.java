package org.example.springtodo.service;

import org.example.springtodo.dto.TodoDto;
import org.example.springtodo.entity.Todo;
import org.example.springtodo.exception.TodoNotFoundException;
import org.example.springtodo.model.TodoStatus;
import org.example.springtodo.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceImplTest {

    @Mock
    private TodoRepository repository;

    @InjectMocks
    private TodoServiceImpl service;

    private Todo todo1;
    private TodoDto todoDto1;

    @BeforeEach
    void setUp() {
        todo1 = new Todo();
        todo1.setId(1L);
        todo1.setName("Test Todo");
        todo1.setDescription("Test Description");
        todo1.setDue(LocalDate.now());
        todo1.setStatus(TodoStatus.TODO);

        todoDto1 = new TodoDto();
        todoDto1.setId(1L);
        todoDto1.setName("Test Todo");
        todoDto1.setDescription("Test Description");
        todoDto1.setDue(LocalDate.now());
        todoDto1.setStatus("TODO");

    }

    @Test
    void getAllTodos() {
        when(repository.findAll()).thenReturn(Arrays.asList(todo1));

        List<TodoDto> todos = service.getAllTodos();

        assertEquals(1, todos.size());
        assertEquals(todoDto1, todos.getFirst());
        verify(repository, times(1)).findAll();
    }

    @Test
    void saveTodo() {
        when(repository.save(any(Todo.class))).thenReturn(todo1);

        TodoDto savedTodo = service.saveTodo(todoDto1);

        assertEquals(todoDto1, savedTodo);
        verify(repository, times(1)).save(any(Todo.class));
    }

    @Test
    void getTodoByExistingId() {
        when(repository.findById(1L)).thenReturn(Optional.of(todo1));

        TodoDto todoDto = service.getTodoById(1L);

        assertEquals(todoDto1, todoDto);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void getTodoByNonExistingId() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TodoNotFoundException.class, () -> service.getTodoById(1L));
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void updateTodoExistingId() {
        when(repository.findById(1L)).thenReturn(Optional.of(todo1));
        when(repository.save(any(Todo.class))).thenReturn(todo1);

        TodoDto updatedTodoDto = service.updateTodo(1L, todoDto1);

        assertEquals(todoDto1, updatedTodoDto);
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(Todo.class));
    }

    @Test
    void updateTodoNonExistingId() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TodoNotFoundException.class, () -> service.updateTodo(1L, todoDto1));
        verify(repository, times(1)).findById(1L);
        verify(repository, never()).save(any(Todo.class));
    }

    @Test
    void deleteTodoExistingId() {
        when(repository.findById(1L)).thenReturn(Optional.of(todo1));
        doNothing().when(repository).deleteById(1L);

        service.deleteTodo(1L);

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void deleteTodoNonExistingId() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TodoNotFoundException.class, () -> service.deleteTodo(1L));
        verify(repository, times(1)).findById(1L);
        verify(repository, never()).deleteById(1L);
    }

    @Test
    void getTodosByStatus() {
        when(repository.findTodosByStatus(TodoStatus.TODO)).thenReturn(Arrays.asList(todo1));
        when(repository.findTodosByStatus(TodoStatus.DONE)).thenReturn(Arrays.asList());
        when(repository.findTodosByStatus(TodoStatus.PROGRESS)).thenReturn(Arrays.asList());

        List<TodoDto> todosTodoStatus = service.getTodosByStatus("TODO");
        List<TodoDto> todosDoneStatus = service.getTodosByStatus("DONE");
        List<TodoDto> todosProgressStatus = service.getTodosByStatus("PROGRESS");

        assertEquals(1, todosTodoStatus.size());
        assertEquals(todoDto1, todosTodoStatus.getFirst());
        assertEquals(0, todosDoneStatus.size());
        assertEquals(0, todosProgressStatus.size());
        verify(repository, times(1)).findTodosByStatus(TodoStatus.TODO);
        verify(repository, times(1)).findTodosByStatus(TodoStatus.DONE);
        verify(repository, times(1)).findTodosByStatus(TodoStatus.PROGRESS);
    }

    @Test
    void getAllTodosSortedByDue() {
        Todo todo2 = new Todo();
        todo2.setId(2L);
        todo2.setName("Test Todo 2");
        todo2.setDescription("Test Description 2");
        todo2.setDue(LocalDate.now().plusDays(1));
        todo2.setStatus(TodoStatus.TODO);

        when(repository.findAll()).thenReturn(Arrays.asList(todo2, todo1));

        List<TodoDto> todos = service.getAllTodosSorted("DUE");

        assertEquals(2, todos.size());
        assertEquals(todoDto1, todos.getFirst());
        verify(repository, times(1)).findAll();
    }

    @Test
    void getAllTodosSortedByStatus() {
        Todo todo2 = new Todo();
        todo2.setId(2L);
        todo2.setName("Test Todo 2");
        todo2.setDescription("Test Description 2");
        todo2.setDue(LocalDate.now().plusDays(1));
        todo2.setStatus(TodoStatus.DONE);

        when(repository.findAll()).thenReturn(Arrays.asList(todo2, todo1));

        List<TodoDto> todos = service.getAllTodosSorted("STATUS");

        assertEquals(2, todos.size());
        assertEquals(todoDto1, todos.getFirst());
        verify(repository, times(1)).findAll();
    }
}