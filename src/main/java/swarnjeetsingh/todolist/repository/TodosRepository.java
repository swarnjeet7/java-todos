package swarnjeetsingh.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swarnjeetsingh.todolist.model.TodoModel;

public interface TodosRepository extends JpaRepository<TodoModel, Long>  {

    public TodoModel findById(long id);

}
