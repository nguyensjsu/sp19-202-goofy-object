package controller;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/login")
public class LoginController {

    @PostMapping
    public boolean login(@RequestParam String username) {
        // start socket
        return true;
    }
}
