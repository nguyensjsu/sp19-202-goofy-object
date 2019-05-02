package com.goofyobject.tetris;

import com.goofyobject.tetris.database.UserInfo;
import com.goofyobject.tetris.domain.Code;
import com.goofyobject.tetris.domain.Status;
import com.goofyobject.tetris.domain.User;
import org.json.JSONObject;

import java.util.HashMap;

public class Test {
    public static void main(String[] ar) {
        UserInfo.Builder builder = UserInfo.Builder.newInstance();
        JSONObject obj = new JSONObject();
        try {
            builder.setAge(obj.getInt("username"));
            System.out.println(builder);
        }catch (Exception e) {

        }

    }
}
