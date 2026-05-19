package vn.proX.todoapplication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.proX.todoapplication.entity.Todo;
import vn.proX.todoapplication.service.TodoService;

@RestController
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/create-todo")
    public String create() {

        Todo myTodo = new Todo("John Doe", false);
        Todo newTodo = this.todoService.handleCreateTodo(myTodo);

        return "Create a new todo item id = " + newTodo.getId();
    }

    @GetMapping("/todos")
    public String getTodo() {

        this.todoService.handleGetTodo();

        return "Get all todo items";
    }

    @GetMapping("/update-todo")
    public String updateTodo() {

        this.todoService.handleUpdateTodo();

        return "Update todo item";
    }

    @GetMapping("/delete-todo")
    public String deleteTodo() {

        this.todoService.handleDeleteTodo();

        return "Delete todo item";
    }

}
