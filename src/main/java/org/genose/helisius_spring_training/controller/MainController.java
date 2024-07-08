package org.genose.helisius_spring_training.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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

}
