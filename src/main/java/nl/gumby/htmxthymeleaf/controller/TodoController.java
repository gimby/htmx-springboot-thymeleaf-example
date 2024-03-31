package nl.gumby.htmxthymeleaf.controller;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxReswap;
import nl.gumby.htmxthymeleaf.TodosResetEvent;
import nl.gumby.htmxthymeleaf.model.TodoType;
import nl.gumby.htmxthymeleaf.repo.TodoRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.stream.Stream;


class DuplicateEntryException extends RuntimeException {

    DuplicateEntryException(String msg) {
        super(msg);
    }
}

@RequestMapping(value="/todos")
@Controller
public class TodoController {
    private final TodoRepository todoRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public TodoController(TodoRepository todoRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.todoRepository = todoRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @ExceptionHandler(DuplicateEntryException.class)
    public HtmxResponse handleError(Exception ex) {
        return HtmxResponse.builder()
                .reswap(HtmxReswap.none())
                .view(new ModelAndView("todos :: error", Map.of("message", ex.getMessage())))
                .build();
    }

    @GetMapping
    public String todos(Model model) {
        model.addAttribute("todos", todoRepository.findAll());
        return "todos";
    }

    @PostMapping(value="/reset")
    public String reset(Model model) {
        applicationEventPublisher.publishEvent(new TodosResetEvent());
        model.addAttribute("todos", todoRepository.findAll());
        return "todos :: todos-list";
    }

    @PostMapping
    public HtmxResponse add(@RequestParam("new-todo") String title, Model model) {

        if(todoExists(title)) {
            throw new DuplicateEntryException("Entry already exists");
        }

        todoRepository.save(new TodoType(null, title));
        model.addAttribute("todos", todoRepository.findAll());
        return buildResponse("todos :: todos-list");
    }

    @PostMapping(value="inline")
    public HtmxResponse addInline(@RequestParam("new-todo") String title, Model model) {

        if(todoExists(title)) {
           throw new DuplicateEntryException("Entry already exists");
        }

        TodoType newTodo = todoRepository.save(new TodoType(null, title));
        model.addAttribute("todo", newTodo);
        return buildResponse("fragments :: todo-line");
    }

    @ResponseBody
    @DeleteMapping(value="/{id}", produces= MediaType.TEXT_HTML_VALUE)
    public String delete(@PathVariable Integer id) {
        todoRepository.deleteById(id);
        return "";
    }

    private boolean todoExists(String todo) {
        return todoRepository.countByTitle(todo) > 0;
    }

    private HtmxResponse buildResponse(String... views) {

        HtmxResponse.Builder builder =  HtmxResponse.builder()
                .view("todos :: todos-form")
                .view("todos :: error");

        Stream.of(views).forEach(builder::view);
        return builder.build();
    }
}
