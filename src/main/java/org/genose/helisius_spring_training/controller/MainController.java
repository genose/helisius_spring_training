package org.genose.helisius_spring_training.controller;



import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    public MainController() {}

   @GetMapping("")
    public String indexMain() {
        return "Hello World!";
    }
    // @GetMapping("/user/{id}")
    /* public String user(@PathVariable final String id) {
        return "Hello World! "+ id;
    }*/

   /* SwaggerUI / OpenAPI
    @Bean

    public GroupedOpenApi usersGroup(@Value("${springdoc.version}") String appVersion) {
        return GroupedOpenApi.builder().group("users")
                .addOperationCustomizer((operation, handlerMethod) -> {
                    operation.addSecurityItem(new SecurityRequirement().addList("basicScheme"));
                    return operation;
                })
                .addOpenApiCustomizer(openApi -> openApi.info(new Info().title("Users API").version(appVersion)))
                .packagesToScan("org.genose.helisius_spring_training")
                .pathsToMatch("/docs/**")
                .build();
    }
    
    */
}
