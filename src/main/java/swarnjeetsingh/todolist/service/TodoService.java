package swarnjeetsingh.todolist.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import swarnjeetsingh.todolist.model.TodoModel;
import swarnjeetsingh.todolist.model.TodoModelDTO;
import swarnjeetsingh.todolist.model.response.TodoResponse;
import swarnjeetsingh.todolist.repository.TodosRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    private static final Logger logger = LoggerFactory.getLogger(TodoService.class);
    private final TodosRepository todosRepository;

    public TodoService(TodosRepository todosRepository) {
        this.todosRepository = todosRepository;
    }

    public ResponseEntity<TodoResponse> getAllTodos() {
        logger.info("Retrieving all todos");
        List<TodoModel> todos = todosRepository.findByActiveTrue();
        logger.debug("Todos retrieved: {}", todos);
        return ResponseEntity.ok(TodoResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("success")
                .success(true)
                .data(todos)
                .build());
    }

    public ResponseEntity<TodoResponse> addTodo(TodoModelDTO todo) {
        logger.info("got a request to add a new todo: {}", todo.task());
        TodoModel todoModel = new TodoModel();
        todoModel.setTask(todo.task());
        todoModel.setDescription(todo.description());
        todoModel.setCompleted(todo.completed());

        return ResponseEntity.ok(TodoResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("success")
                .success(true)
                .data(todosRepository.save(todoModel))
                .build());
    }

    public ResponseEntity<TodoResponse> getTodoById(int id) {
        logger.info("Retrieving todo with id: {}", id);
        Optional<TodoModel> todo = Optional.ofNullable(todosRepository.findById(id));

        if (todo.isEmpty()) {
            logger.info("Todo not found with id: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    TodoResponse.builder()
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .message("Todo not found")
                            .success(false)
                            .build()
            );
        }
        logger.info("Todo found with id: {}", id);
        return ResponseEntity.ok(TodoResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("success")
                .success(true)
                .data(todo.get())
                .build());
    }

    public ResponseEntity<TodoResponse> deleteTodoById(int id) {
        logger.info("Deleting todo with id: {}", id);
        Optional<TodoModel> optionalTodo = Optional.ofNullable(todosRepository.findById(id));

        if (optionalTodo.isPresent()) {
            TodoModel todo = optionalTodo.get();
            todo.setActive(false);
            todosRepository.save(todo);
            logger.info("DeActivated todo with id: {}", id);
            return ResponseEntity.ok(
                    TodoResponse.builder().success((true)).statusCode(HttpStatus.OK.value()).message("Todo deleted successfully").data(optionalTodo.get()).build()
            );
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                TodoResponse.builder().statusCode(HttpStatus.BAD_REQUEST.value()).message("Todo not found").success(false).build()
        );
    }

    public ResponseEntity<TodoResponse> updateTodo(TodoModel todo) {
        Optional<TodoModel> optionalTodo = Optional.ofNullable(todosRepository.findById(todo.getId()));

        if (optionalTodo.isPresent()) {
            TodoModel updatedTodo = optionalTodo.get();
            updatedTodo.setTask(todo.getTask());
            updatedTodo.setDescription(todo.getDescription());
            updatedTodo.setCompleted(todo.isCompleted());
            updatedTodo.setActive(todo.isActive());
            return ResponseEntity.ok(
                    TodoResponse.builder().success((true)).statusCode(HttpStatus.OK.value()).message("Todo updated successfully").data(todosRepository.save(updatedTodo)).build()
            );
        }


        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                TodoResponse.builder()
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .message("Todo not found")
                        .success(false)
                        .build());
    }

}
