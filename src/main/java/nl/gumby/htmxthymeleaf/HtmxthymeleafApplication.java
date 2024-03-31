package nl.gumby.htmxthymeleaf;

import nl.gumby.htmxthymeleaf.model.TodoType;
import nl.gumby.htmxthymeleaf.repo.TodoRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.stream.Stream;

@SpringBootApplication
public class HtmxthymeleafApplication {

	public static void main(String[] args) {
		SpringApplication.run(HtmxthymeleafApplication.class, args);
	}

}

@Component
class Initializer {
	private final TodoRepository todoRepository;

	Initializer(TodoRepository todoRepository) {
		this.todoRepository = todoRepository;
	}

	@EventListener({ApplicationReadyEvent.class, TodosResetEvent.class})
	void reset() {
		this.todoRepository.deleteAll();
		Stream.of("Learn spanish", "Make coffee", "Buy cat food", "Wash socks")
				.forEach(t -> this.todoRepository.save(new TodoType(null, t)));
	}
}

