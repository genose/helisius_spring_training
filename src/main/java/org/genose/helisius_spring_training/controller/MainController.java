package org.genose.helisius_spring_training.controller;


import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    public MainController() {
    }

   /* essai SwaggerUI / OpenAPI
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
