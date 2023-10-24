package de.flyndre.flengine.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Engine {

    /**
     * @return Hello World! for testing
     */
    @GetMapping("/hello-world")
    public String helloWorld() {
        return "Hello world!";
    }
}