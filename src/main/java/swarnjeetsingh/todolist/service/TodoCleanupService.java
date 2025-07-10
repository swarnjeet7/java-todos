package swarnjeetsingh.todolist.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import swarnjeetsingh.todolist.model.TodoModel;
import swarnjeetsingh.todolist.repository.TodosRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TodoCleanupService {
    private final TodosRepository todosRepository;
    private static final Logger logger = LoggerFactory.getLogger(TodoService.class);

    public TodoCleanupService(TodosRepository todosRepository) {
        this.todosRepository = todosRepository;
    }

    // Method to delete old todos older than one month
    @Scheduled(cron = "0 0 10 * * *") // Configure the schedule (e.g., every hour)
    public void deleteOldTasks() {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minus(1, ChronoUnit.MONTHS);
        List<TodoModel> todoModels = todosRepository.findTodoModelByCreatedAtBeforeAndActiveTrue(oneMonthAgo);
        if(todoModels.isEmpty()) {
            logger.info("No old tasks found");
            return;
        }

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

}
