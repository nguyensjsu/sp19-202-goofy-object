package five_in_a_row.controller;

import five_in_a_row.entity.User;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping()
public class LoginController {

    @PostMapping("/login")
    public boolean login(@RequestBody User user) {
        if(user != null ) {
            if(user.getUsername() != null ) {
                return true;
            }
        }
        return false;
    }

    @PostMapping("/register")
    public boolean register(@RequestBody User user) {
        if(user != null ) {
            if(user.getUsername() != null) {
                return true;
            }
        }
        return false;
    }
}
