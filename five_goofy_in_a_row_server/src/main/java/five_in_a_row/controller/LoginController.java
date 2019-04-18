package five_in_a_row.controller;

import five_in_a_row.entity.User;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping()
public class LoginController {

    @GetMapping("/login")
    public boolean login(@RequestBody User user) {
        // check login
        return true;
    }

    @PostMapping("/register")
    public boolean register(@RequestBody User user) {
        // check login
        return true;
    }
}
