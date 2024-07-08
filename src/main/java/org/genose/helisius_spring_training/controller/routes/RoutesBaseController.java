package org.genose.helisius_spring_training.controller.routes;

import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import org.genose.helisius_spring_training.utils.ClassStackUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@EqualsAndHashCode
@MappedSuperclass
@RequestMapping("")
@RestController
abstract public class RoutesBaseController {
    protected Logger logger = LoggerFactory.getLogger(RoutesBaseController.class);
    @GetMapping("")
    public String get() {
        return "Hello World ! Base::" + this.getClass().getSimpleName()+" :: "+ClassStackUtils.getEnclosingMethodObject(this);
    }

    @GetMapping("/all")
    public List<Object> getAll() {
        String message  = "Hello World ! Base::" + this.getClass().getSimpleName()+" :: "+ClassStackUtils.getEnclosingMethodObject(this);
        ArrayList<Object> list = new ArrayList<>();
        list.add(message);

        return  list.stream().toList() ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable int id) {
        return ResponseEntity.accepted().body("Hello World ! Base Base::" + this.getClass().getSimpleName()+" :: "+ClassStackUtils.getEnclosingMethodObject(this));
    }

    @PatchMapping("")
    @PutMapping("")
    @PostMapping("")
    public ResponseEntity<Object> saveEntity(ResponseEntity<Object> entity) {
        return ResponseEntity.ok("From Class : Base::" + this.getClass().getSimpleName()+" :: "+ClassStackUtils.getEnclosingMethodObject(this));
    }

    @DeleteMapping("")
    public ResponseEntity<Object> delete(ResponseEntity<Object> entity) {
        return ResponseEntity.ok("From Class : Base::" + this.getClass().getSimpleName()+" :: "+ClassStackUtils.getEnclosingMethodObject(this));
    }

}
