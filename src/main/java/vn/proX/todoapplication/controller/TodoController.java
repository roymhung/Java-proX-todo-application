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
        // sau khi gọi service, chúng ta có thể in ra thông tin của todo để kiểm tra =>> khi dung
        // private final TodoService todoService thì chúng ta có thể gọi được phương thức
        // handleCreateTodo của service để xử lý logic tạo mới todo
        System.out.println(newTodo);
        // In ra thông tin của todo sau khi đã gọi service khi khong có private final TodoService
        // todoService thì chúng ta sẽ không thể gọi được phương thức handleCreateTodo của service
        // để xử lý logic tạo mới todo
        return "Create a new todo item id = " + newTodo.getId();
    }

}
