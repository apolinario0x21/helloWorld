package apolinario0x21.helloworld.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/helloworld")
public class HelloWorldController {

    @GetMapping
    public String helloWorld() {
        return "Hello World!";
    }


}
