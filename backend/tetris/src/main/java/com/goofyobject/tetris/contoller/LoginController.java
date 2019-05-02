package com.goofyobject.tetris.contoller;

import com.goofyobject.tetris.database.MySQLConnection;
import com.goofyobject.tetris.database.UserInfor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping()
public class LoginController {
    @Autowired
    private MySQLConnection mySQLConnection;

    @PostMapping("/login")
    public boolean login(@RequestBody UserInfor userInfor) {
        if(userInfor == null || userInfor.getUsername() == null || userInfor.getUsername().trim().equals("")  ) {
                return false;
        }
        return mySQLConnection.login(userInfor);
    }

    @PostMapping("/register")
    public boolean register(@RequestBody UserInfor userInfor) {
        if(userInfor == null || userInfor.getUsername() == null || userInfor.getUsername().trim().equals("")
            || userInfor.getPassword() == null || userInfor.getPassword().trim().equals("")) {
                return false;
        }
        return mySQLConnection.saveUser(userInfor);
    }
}
