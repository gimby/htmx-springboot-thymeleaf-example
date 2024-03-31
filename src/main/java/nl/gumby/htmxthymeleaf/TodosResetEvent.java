package nl.gumby.htmxthymeleaf;

import org.springframework.context.ApplicationEvent;

import java.time.Instant;

public class TodosResetEvent extends ApplicationEvent {
    public TodosResetEvent() {
        super(Instant.now());
    }
}