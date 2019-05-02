package com.goofyobject.tetris.contoller;

import com.goofyobject.tetris.database.MySQLConnection;
import com.goofyobject.tetris.database.UserInfo;

import com.goofyobject.tetris.domain.User;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;

@RestController
@CrossOrigin("*")
@RequestMapping()
public class LoginController {
    @Autowired
    private MySQLConnection mySQLConnection;

    @PostMapping("/login")
    public boolean login(HttpServletRequest request) {

        UserInfo userInfo = UserInfo.Builder.newInstance().buildFromJSONObject(getRequestBody(request));
        if(userInfo == null || userInfo.getUsername() == null || userInfo.getUsername().trim().equals("")  ) {
                return false;
        }
        return mySQLConnection.login(userInfo);
    }

    @PostMapping("/register")
    public boolean register(HttpServletRequest request) {
        UserInfo userInfo = UserInfo.Builder.newInstance().buildFromJSONObject(getRequestBody(request));
        if(userInfo == null || userInfo.getUsername() == null || userInfo.getUsername().trim().equals("")
            || userInfo.getPassword() == null || userInfo.getPassword().trim().equals("")) {
                return false;
        }
        return mySQLConnection.saveUser(userInfo);
    }


    private JSONObject getRequestBody(HttpServletRequest request) {
            StringBuffer jb = new StringBuffer();
            String line;
            try {
                BufferedReader reader = request.getReader();
                while ((line = reader.readLine()) != null) {
                    jb.append(line);
                }
                reader.close();
                return new JSONObject(jb.toString());
            }catch (Exception e) {
                e.printStackTrace();
            }
            return null;
    }

}
