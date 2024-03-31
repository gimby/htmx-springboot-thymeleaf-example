# htmx-springboot-thymeleaf-example
An example of how to use HTMX in combination with Spring Boot 3 and Thymeleaf.

The code is based on a Youtube video done by Josh Long. What this example code adds additional examples of is:

* Split the CSS into a separate static style.css file to demonstrate how to package static resource files
* Splitting up the thymeleaf template so a todo line is a separate fragment in a separate fragments file which can be
  included and parameters passed to it
* Reusing said fragment from the Spring Boot controller to, rather than replace the entire list of todo items when
  adding a new item, append the new item to the end of the list. The "Add inline" button triggers this path. This
  demonstrates how to use the HTMX "beforeend" swap type.
* Add rudimentary error handling by being able to display an error message when a duplicate todo item is added (and it is 
  cleared when a new one is added successfully)

This example was setup for personal learning purposes to effectively use HTMX and to demo it to other people. I wanted
it to be a more complete example that goes further than the bare minimum tutorial level.

## Resources that this code is based on

* The Youtube video by Josh Long: https://www.youtube.com/watch?v=M9TL-2Jbr0c
* The blog article "HTMX global error handler" by Wim Deblauwe: https://www.wimdeblauwe.com/blog/2023/12/14/htmx-global-error-handler/

