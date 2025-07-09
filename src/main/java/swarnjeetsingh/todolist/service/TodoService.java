package swarnjeetsingh.todolist.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import swarnjeetsingh.todolist.model.TodoModel;
import swarnjeetsingh.todolist.repository.TodosRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TodoService {
    private final TodosRepository todosRepository;
    private static final Logger logger = LoggerFactory.getLogger(TodoService.class);

    public TodoService(TodosRepository todosRepository) {
        this.todosRepository = todosRepository;
    }

    // Method to delete old todos older than one month
    @Scheduled(cron = "0 0 10 * * *") // Configure the schedule (e.g., every hour)
    public void deleteOldTasks() {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minus(1, ChronoUnit.MONTHS);
        List<TodoModel> todoModels = todosRepository.findTodoModelByCreatedAtBefore(oneMonthAgo);
        List<TodoModel> disabledTodos = todoModels.stream().map(todoModel -> {
            todoModel.setActive(false);
            return todoModel;
        }).toList();

        try {
            List<TodoModel> updatedList = todosRepository.saveAll(disabledTodos);
            logger.info("these tasks are disabled: {}", updatedList);
        } catch (Exception e) {
          logger.error("Error deleting old tasks: {}", e.getMessage());
        }
    }

    public List<TodoModel> getAllTodos() {
        logger.info("Retrieving all todos");
        List<TodoModel> todos = todosRepository.findAll();
        logger.debug("Todos retrieved: {}", todos);
        return todos;
    }

    public void addTodo(TodoModel todoModel) {
        logger.info("Adding a new todo: {}", todoModel.getTask());
        todosRepository.save(todoModel);
        logger.info("Todo added successfully");
    }

    public TodoModel getTodoById(int id) {
        logger.info("Retrieving todo with id: {}", id);
        return todosRepository.findById(id);
    }

    public TodoModel deleteTodoById(int id) {
        logger.info("Deleting todo with id: {}", id);
        TodoModel todo = todosRepository.findById(id);
        todo.setActive(false);
        todosRepository.save(todo);
        logger.info("DeActivated todo with id: {}", id);
        return todo;
    }

}
