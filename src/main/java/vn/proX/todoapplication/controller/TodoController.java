package vn.proX.todoapplication.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import vn.proX.todoapplication.entity.Todo;
import vn.proX.todoapplication.service.TodoService;

@RestController
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
        Todo todoData = this.todoService.GetTodoById(id);
        if (todoData != null) {
            return ResponseEntity.ok().body(todoData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/create-todo")
    public String create() {

        Todo myTodo = new Todo("John Doe", false);
        Todo newTodo = this.todoService.handleCreateTodo(myTodo);

        return "Create a new todo item id = " + newTodo.getId();
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Todo>> getTodo() {

        List<Todo> listTodo = this.todoService.handleGetTodo();

        return ResponseEntity.ok().body(listTodo);
    }

    @PostMapping("/todos")
    public ResponseEntity<Todo> createTodo(@RequestBody Todo inputTodo) {

        Todo newTodo = this.todoService.handleCreateTodo(inputTodo);

        return ResponseEntity.status(HttpStatus.CREATED).body(newTodo);
    }


    @PutMapping("/todos/{id}")
    public ResponseEntity<String> updateTodo(@PathVariable Long id, @RequestBody Todo inputTodo) {

        this.todoService.handleUpdateTodo(id, inputTodo);

        return ResponseEntity.ok().body("Update todo item successfully: " + inputTodo.toString());
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable Long id) {

        this.todoService.handleDeleteTodo(id);

        return ResponseEntity.ok().body("Delete todo item successfully");
    }

}
