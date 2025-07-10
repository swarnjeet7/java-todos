package swarnjeetsingh.todolist.controller;

import org.springframework.web.bind.annotation.*;
import swarnjeetsingh.todolist.model.TodoModel;
import swarnjeetsingh.todolist.service.TodoService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public List<TodoModel> getAllTodos() {
        return todoService.getAllTodos();
    }

    @PostMapping
    public TodoModel addTodo(@RequestBody TodoModel todo) {
        return todoService.addTodo(todo);
    }

    @GetMapping("getById/{id}")
    public TodoModel getTodo(@PathVariable int id) {
        return todoService.getTodoById(id);
    }

    @DeleteMapping("{id}")
    public TodoModel deleteTodo(@PathVariable int id) {
        return todoService.deleteTodoById(id);
    }
}
