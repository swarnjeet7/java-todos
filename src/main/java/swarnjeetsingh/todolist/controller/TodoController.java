package swarnjeetsingh.todolist.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import swarnjeetsingh.todolist.model.TodoModel;
import swarnjeetsingh.todolist.model.TodoModelDTO;
import swarnjeetsingh.todolist.model.response.TodoResponse;
import swarnjeetsingh.todolist.service.TodoService;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;
    private static final Logger logger = LoggerFactory.getLogger(TodoService.class);

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<TodoResponse> getAllTodos() {
        return todoService.getAllTodos();
    }

    @PostMapping("/create")
    public ResponseEntity<TodoResponse> addTodo(@Validated @RequestBody TodoModelDTO todo) {
        return todoService.addTodo(todo);
    }

    @GetMapping("getById/{id}")
    public ResponseEntity<TodoResponse> getTodo(@PathVariable int id) {
        return todoService.getTodoById(id);
    }

    @DeleteMapping("deleteById/{id}")
    public ResponseEntity<TodoResponse> deleteTodo(@PathVariable int id) {
        return todoService.deleteTodoById(id);
    }

    @PutMapping("/update")
    public ResponseEntity<TodoResponse> updateTodo(@RequestBody TodoModel updatedTodo) {
        return todoService.updateTodo(updatedTodo);
    }
}
