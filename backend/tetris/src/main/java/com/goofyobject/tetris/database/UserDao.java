package com.goofyobject.tetris.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao implements UserService {

    @Autowired
    private JdbcTemplate jdbctemplate;

    @Override
    public int saveUser(UserInfo user) {
        return jdbctemplate.update("INSERT INTO user VALUES (?, ?, ?, ?, ?, ?, ?)", user.getUsername(),
                user.getPassword(), user.getRegion(), user.getGender(), user.getAge(), user.getWins(), user.getLoses());
    }

    @Override
    public boolean findUsersByUsername(UserInfo user) {
        String query = "SELECT * FROM user WHERE username=? and password = ?";

        List<UserInfo> res = jdbctemplate.query(query, new Object[]{user.getUsername(),user.getPassword()}, new RowMapper<UserInfo>() {
            public UserInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
                return UserInfo.Builder.newInstance().buildFromResultSet(rs);
            }
        });
        return res.size() > 0;
    }


}