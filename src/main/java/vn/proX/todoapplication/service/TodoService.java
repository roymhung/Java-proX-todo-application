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

    public Todo GetTodoById(Long id) {
        Optional<Todo> todoOptional = this.todoRepository.findById(id);
        return todoOptional.isPresent() ? todoOptional.get() : null;
    }

    public void handleUpdateTodo(Long id, Todo inputTodo) {
        // Logic to update a todo item
        Optional<Todo> todoOptional = this.todoRepository.findById(id);
        if (todoOptional.isPresent()) {
            Todo currentTodo = todoOptional.get();

            currentTodo.setUsername(inputTodo.getUsername());
            currentTodo.setCompleted(inputTodo.isCompleted());

            this.todoRepository.save(currentTodo);
            System.out.println("Updated todo item: " + currentTodo);
        } else {
            System.out.println("Todo item with ID " + id + " not found");
        }
    }

    public void handleDeleteTodo(Long id) {
        // Logic to delete a todo item

        this.todoRepository.deleteById(id);
    }

}


