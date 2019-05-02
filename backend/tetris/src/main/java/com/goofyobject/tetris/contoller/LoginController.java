package com.goofyobject.tetris.contoller;

import com.goofyobject.tetris.database.MySQLConnection;
import com.goofyobject.tetris.database.UserInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping()
public class LoginController {
    @Autowired
    private MySQLConnection mySQLConnection;

    @PostMapping("/login")
    public boolean login(@RequestBody UserInfo userInfo) {
        if(userInfo == null || userInfo.getUsername() == null || userInfo.getUsername().trim().equals("")  ) {
                return false;
        }
        return mySQLConnection.login(userInfo);
    }

    @PostMapping("/register")
    public boolean register(@RequestBody UserInfo userInfo) {
        if(userInfo == null || userInfo.getUsername() == null || userInfo.getUsername().trim().equals("")
            || userInfo.getPassword() == null || userInfo.getPassword().trim().equals("")) {
                return false;
        }
        return mySQLConnection.saveUser(userInfo);
    }
}
