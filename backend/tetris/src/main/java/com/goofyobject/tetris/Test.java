package com.goofyobject.tetris;

import com.goofyobject.tetris.domain.Code;
import com.goofyobject.tetris.domain.Status;
import com.goofyobject.tetris.domain.User;

import java.util.HashMap;

public class Test {
    public static void main(String[] ar) {
        User user = new User("wgc");
        Status s = new Status(Code.OK);
        HashMap<String,Object> res = new HashMap<>();
        s.setDecorator(user);
        s.getObj(res);
        System.out.println(res.toString());

    }
}
