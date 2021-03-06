package com.goofyobject.tetris.database;

import java.sql.ResultSet;

import org.json.JSONObject;

public class UserInfo {
    private String username;
    private String password;
    private int age;
    private String gender;
    private String region;
    private int wins;
    private int loses;

    public UserInfo(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.age = builder.age;
        this.gender = builder.gender;
        this.region = builder.region;
        this.wins = builder.wins;
        this.loses = builder.loses;
    }

    public static class Builder {

        /// instance fields
        private String username;
        private String password;
        private int age;
        private String gender;
        private String region;
        private int wins;
        private int loses;

        public static Builder newInstance() {
            return new Builder();
        }

        private Builder() {
        }

        // Setter methods
        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setAge(int age) {
            this.age = age;
            return this;
        }

        public Builder setGender(String gender) {
            this.gender = gender;
            return this;
        }

        public Builder setRegion(String region) {
            this.region = region;
            return this;
        }

        public Builder setWins(int wins) {
            this.wins = wins;
            return this;
        }

        public Builder setLoses(int loses) {
            this.loses = loses;
            return this;
        }

        // build method to deal with outer class
        // to return outer instance
        public UserInfo build() {
            return new UserInfo(this);
        }

        public UserInfo buildFromJSONObject(JSONObject userInfoObject) {
            UserInfo.Builder builder = UserInfo.Builder.newInstance();
            try{
                if(userInfoObject.has("username")) {
                    builder.setUsername(userInfoObject.getString("username"));
                }
                if(userInfoObject.has("password")) {
                    builder.setPassword(userInfoObject.getString("password"));
                }
                if(userInfoObject.has("gender")) {
                    builder.setGender(userInfoObject.getString("gender"));
                }
                if(userInfoObject.has("region")) {
                    builder.setRegion(userInfoObject.getString("region"));
                }
                if(userInfoObject.has("age")) {
                    builder.setAge(userInfoObject.getInt("age"));
                }
                if(userInfoObject.has("wins")) {
                    builder.setAge(userInfoObject.getInt("wins"));
                }
                if(userInfoObject.has("loses")) {
                    builder.setAge(userInfoObject.getInt("loses"));
                }
                return builder.build();
            }catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        public UserInfo buildFromResultSet(ResultSet rs) {
            UserInfo.Builder builder = UserInfo.Builder.newInstance();
            try{
                if(rs.getString("username") != null) {
                    builder.setUsername(rs.getString("username"));
                }
                if(rs.getString("password") != null) {
                    builder.setUsername(rs.getString("password"));
                }
                if(rs.getString("gender") != null) {
                    builder.setUsername(rs.getString("gender"));
                }
                if(rs.getString("region") != null) {
                    builder.setUsername(rs.getString("region"));
                }
                if(rs.getString("age") != null) {
                    builder.setUsername(rs.getString("age"));
                }
                if(rs.getString("wins") != null) {
                    builder.setUsername(rs.getString("wins"));
                }
                if(rs.getString("loses") != null) {
                    builder.setUsername(rs.getString("loses"));
                }
                return builder.build();
            }catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }



    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLoses() {
        return loses;
    }

    public void setLoses(int loses) {
        this.loses = loses;
    }
}
