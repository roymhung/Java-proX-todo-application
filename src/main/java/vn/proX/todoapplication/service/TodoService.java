package vn.proX.todoapplication.service;

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
}
