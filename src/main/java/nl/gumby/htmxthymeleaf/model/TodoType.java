package nl.gumby.htmxthymeleaf.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name="todo")
public record TodoType (@Id Integer id, String title){}
