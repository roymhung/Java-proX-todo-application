package vn.proX.todoapplication.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.proX.todoapplication.entity.Todo;
import vn.proX.todoapplication.repository.TodoRepository;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Todo handleCreateTodo(Todo todo) {
        // Logic to create a new todo item
        System.out.println("Handling creation of todo: " + todo);
        System.out.println("Handling creation of todo-toString: " + todo.toString());

        // Lưu todo vào database thông qua repository
        Todo CreatedTodo = this.todoRepository.save(todo);
        return CreatedTodo;
    }

    public void handleGetTodo() {
        // Logic to get all todo items
        // List<Todo> todos = this.todoRepository.findAll();
        // todos.forEach(todo -> System.out.println("Todo item: " + todo));

        // Optional<Todo> todoOptional = this.todoRepository.findById(3L);
        // if (todoOptional.isPresent()) {
        // System.out.println("Found todo item with id 3: " + todoOptional.get());
        // System.out.println("Found todo item with id 3 toString: " +
        // todoOptional.get().toString());
        // } else {
        // System.out.println("Todo item with id 3 not found");
        // }

        Optional<Todo> todoByUsername = this.todoRepository.findByUsername("roy");
        if (todoByUsername.isPresent()) {
            System.out.println("Found todo item with username 'roy': " + todoByUsername.get());
            System.out.println("Found todo item with username 'roy' toString: "
                    + todoByUsername.get().toString());
        } else {
            System.out.println("Todo item with username 'roy' not found");
        }
    }
}
