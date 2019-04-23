package com.goofyobject.tetris.contoller;

import com.goofyobject.tetris.domain.User;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping()
public class LoginController {

    @PostMapping("/login")
    public boolean login(@RequestBody User user) {
        if(user == null || user.getUsername() == null || user.getUsername().trim().equals("")  ) {
                return false;
        }
        return true;
    }

    @PostMapping("/register")
    public boolean register(@RequestBody User user) {
        if(user == null || user.getUsername() == null || user.getUsername().trim().equals("")  ) {
                return false;
        }
        return true;
    }
}
