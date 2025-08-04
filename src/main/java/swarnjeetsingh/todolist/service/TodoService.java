package swarnjeetsingh.todolist.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import swarnjeetsingh.todolist.model.TodoModel;
import swarnjeetsingh.todolist.model.TodoModelDTO;
import swarnjeetsingh.todolist.model.response.TodoResponse;
import swarnjeetsingh.todolist.repository.TodosRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j // Use the Lombok @Slf4j annotation
public class TodoService {
    private final TodosRepository todosRepository;

    public TodoService(TodosRepository todosRepository) {
        this.todosRepository = todosRepository;
    }

    public ResponseEntity<TodoResponse> getAllTodos() {
        log.info("Retrieving all todos");
        List<TodoModel> todos = todosRepository.findByActiveTrue();

        log.debug("Todos retrieved: {}", todos);
        return ResponseEntity.ok(TodoResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("success")
                .success(true)
                .data(todos)
                .build());
    }

    public ResponseEntity<TodoResponse> addTodo(TodoModelDTO todo) {
        log.info("Create new todo: {}", todo.task());
        TodoModel todoModel = new TodoModel();
        todoModel.setTask(todo.task());
        todoModel.setDescription(todo.description());
        todoModel.setCompleted(todo.completed());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(TodoResponse.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("success")
                .success(true)
                .data(todosRepository.save(todoModel))
                .build());
    }

    public ResponseEntity<TodoResponse> getTodoById(int id) {
        log.info("Retrieving todo with id: {}", id);
        Optional<TodoModel> todo = Optional.ofNullable(todosRepository.findById(id));

        if (todo.isEmpty()) {
            log.info("Todo not found with id: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    TodoResponse.builder()
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .message("Todo not found")
                            .success(false)
                            .build()
            );
        }

        log.info("Todo found with id: {}", id);
        return ResponseEntity.ok(TodoResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("success")
                .success(true)
                .data(todo.get())
                .build());
    }

    public ResponseEntity<TodoResponse> deleteTodoById(int id) {
        log.info("Deleting todo with id: {}", id);
        Optional<TodoModel> optionalTodo = Optional.ofNullable(todosRepository.findById(id));

        if (optionalTodo.isPresent()) {
            log.info("Todo found with id to delete: {}", id);
            TodoModel todo = optionalTodo.get();
            todo.setActive(false);
            var deActivatedTodo = todosRepository.save(todo);
            log.info("DeActivated todo: {}", deActivatedTodo);
            return ResponseEntity.ok(
                    TodoResponse.builder().success((true)).statusCode(HttpStatus.OK.value()).message("Todo deleted successfully").data(deActivatedTodo).build()
            );
        }

        log.info("Todo not found with id to delete: {}", id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                TodoResponse.builder().statusCode(HttpStatus.NOT_FOUND.value()).message("Todo not found").success(false).build()
        );
    }

    public ResponseEntity<TodoResponse> updateTodo(TodoModel todo) {
        log.info("Updating todo with id: {}", todo.getId());
        Optional<TodoModel> optionalTodo = Optional.ofNullable(todosRepository.findById(todo.getId()));

        if (optionalTodo.isPresent()) {
            log.info("Todo found with id: {}", todo.getId());
            TodoModel updatedTodo = optionalTodo.get();
            updatedTodo.setTask(todo.getTask());
            updatedTodo.setDescription(todo.getDescription());
            updatedTodo.setCompleted(todo.isCompleted());
            updatedTodo.setActive(todo.isActive());
            log.info("Updated todo: {}", updatedTodo);
            return ResponseEntity.ok(
                    TodoResponse.builder().success((true)).statusCode(HttpStatus.OK.value()).message("Todo updated successfully").data(todosRepository.save(updatedTodo)).build()
            );
        }

        log.info("Todo not found with id: {}", todo.getId());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                TodoResponse.builder()
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .message("Todo not found")
                        .success(false)
                        .build());
    }

}
