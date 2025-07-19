package swarnjeetsingh.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swarnjeetsingh.todolist.model.TodoModel;

import java.time.LocalDateTime;
import java.util.List;

public interface TodosRepository extends JpaRepository<TodoModel, Long>  {

    public List<TodoModel> findByActiveTrue();

    public TodoModel findById(long id);

    public List<TodoModel> findTodoModelByCreatedAtBeforeAndActiveTrue(LocalDateTime date);

}
