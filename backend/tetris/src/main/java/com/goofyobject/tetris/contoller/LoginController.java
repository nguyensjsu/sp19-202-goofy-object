package com.goofyobject.tetris.contoller;

import com.goofyobject.tetris.database.MySQLConnection;
import com.goofyobject.tetris.database.UserInfo;

import com.goofyobject.tetris.domain.User;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping()
public class LoginController {
    @Autowired
    private MySQLConnection mySQLConnection;

    @PostMapping("/login")
    public boolean login(@RequestBody JSONObject userInfoObject) {
        UserInfo userInfo = UserInfo.Builder.newInstance().buildFromJSONObject(userInfoObject);
        if(userInfo == null || userInfo.getUsername() == null || userInfo.getUsername().trim().equals("")  ) {
                return false;
        }
        return mySQLConnection.login(userInfo);
    }

    @PostMapping("/register")
    public boolean register(@RequestBody JSONObject userInfoObject) {
        UserInfo userInfo = UserInfo.Builder.newInstance().buildFromJSONObject(userInfoObject);
        if(userInfo == null || userInfo.getUsername() == null || userInfo.getUsername().trim().equals("")
            || userInfo.getPassword() == null || userInfo.getPassword().trim().equals("")) {
                return false;
        }
        return mySQLConnection.saveUser(userInfo);
    }
}
