package swarnjeetsingh.todolist.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Todo List API")
                        .version("1.0")
                        .description("Rest API's for managing a Todo list. Tech stack: Spring Boot, Java 17, H2 DB")
                        .contact(new Contact()
                                .name("Swarnjeet Singh")
                                .email("swarnjeet7@gmail.com")
                                .url("https://github.com/swarnjeet7")));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("todos")
                .packagesToScan("swarnjeetsingh.todolist")
                .build();
    }
}