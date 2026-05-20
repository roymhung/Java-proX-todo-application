package vn.proX.todoapplication.service;

import java.util.List;
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

    public List<Todo> handleGetTodo() {
        return this.todoRepository.findAll();
    }


    public void handleUpdateTodo() {
        // Logic to update a todo item
        Optional<Todo> todoOptional = this.todoRepository.findById(1L);
        if (todoOptional.isPresent()) {
            Todo currentTodo = todoOptional.get();

            currentTodo.setUsername("roy123");
            currentTodo.setCompleted(true);

            this.todoRepository.save(currentTodo);
            System.out.println("Updated todo item with username 'roy': " + currentTodo);
        } else {
            System.out.println("Todo item with username 'roy' not found");
        }
    }

    public void handleDeleteTodo() {
        // Logic to delete a todo item

        this.todoRepository.deleteById(4L);
    }

}


