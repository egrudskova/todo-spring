package org.example.springtodo.dto;

import lombok.*;
import org.example.springtodo.model.TodoStatus;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class TodoDto {
    private Long id;
    private String name;
    private String description;
    private LocalDate due = LocalDate.now();
    private String status = String.valueOf(TodoStatus.TODO);
}
