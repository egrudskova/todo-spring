package org.example.springtodo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.example.springtodo.model.TodoStatus;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@Table(name = "todos")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate due;
    @Enumerated(EnumType.STRING)
    private TodoStatus status;
}
