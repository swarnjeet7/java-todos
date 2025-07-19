package swarnjeetsingh.todolist.controller;

import jakarta.persistence.PostUpdate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swarnjeetsingh.todolist.model.TodoModel;
import swarnjeetsingh.todolist.service.TodoService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/getAll")
    public List<TodoModel> getAllTodos() {
        return todoService.getAllTodos();
    }

    @PostMapping("/create")
    public TodoModel addTodo(@RequestBody TodoModel todo) {
        return todoService.addTodo(todo);
    }

    @GetMapping("getById/{id}")
    public Optional<TodoModel> getTodo(@PathVariable int id) {
        return todoService.getTodoById(id);
    }

    @DeleteMapping("deleteById/{id}")
    public Optional<TodoModel> deleteTodo(@PathVariable int id) {
        Optional<TodoModel> todo = todoService.deleteTodoById(id);

        if(todo.isPresent()) {
            TodoModel deletedTodo = todo.get();
            return Optional.of(deletedTodo);
        }
        return Optional.empty();
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateTodo(@RequestBody TodoModel updatedTodo) {
        Optional<TodoModel> optionalTodo = todoService.getTodoById(updatedTodo.getId());

        if (optionalTodo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Todo not found");
        }

        TodoModel existingTodo = optionalTodo.get();
        existingTodo.setTask(updatedTodo.getTask());
        existingTodo.setDescription(updatedTodo.getDescription());
        existingTodo.setCompleted(updatedTodo.isCompleted());
        existingTodo.setActive(updatedTodo.isActive());

        todoService.addTodo(existingTodo);

        return ResponseEntity.ok(existingTodo);
    }
}
