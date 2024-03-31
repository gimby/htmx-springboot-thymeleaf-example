package nl.gumby.htmxthymeleaf.repo;

import nl.gumby.htmxthymeleaf.model.TodoType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TodoRepository extends CrudRepository<TodoType, Integer> {

    Integer countByTitle(String title);
}
