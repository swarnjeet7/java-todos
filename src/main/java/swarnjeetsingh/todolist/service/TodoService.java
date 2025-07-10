package swarnjeetsingh.todolist.service;

import org.springframework.stereotype.Service;
import swarnjeetsingh.todolist.model.TodoModel;
import swarnjeetsingh.todolist.repository.TodosRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class TodoService {
    private final TodosRepository todosRepository;
    private static final Logger logger = LoggerFactory.getLogger(TodoService.class);

    public TodoService(TodosRepository todosRepository) {
        this.todosRepository = todosRepository;
    }

    public List<TodoModel> getAllTodos() {
        logger.info("Retrieving all todos");
        List<TodoModel> todos = todosRepository.findAll();
        logger.debug("Todos retrieved: {}", todos);
        return todos;
    }

    public TodoModel addTodo(TodoModel todoModel) {
        logger.info("Adding a new todo: {}", todoModel.getTask());
        return todosRepository.save(todoModel);
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
