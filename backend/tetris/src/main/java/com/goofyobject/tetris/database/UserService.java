package com.goofyobject.tetris.database;

public interface UserService {

	int saveUser(UserInfo users); 
	boolean findUsersByUsername(UserInfo user);

}
