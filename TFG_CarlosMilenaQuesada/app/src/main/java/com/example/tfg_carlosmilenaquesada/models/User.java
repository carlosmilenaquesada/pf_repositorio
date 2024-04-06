package com.example.tfg_carlosmilenaquesada.models;

import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String password;
    private String privileges;

    public User() {
    }

    public User(String id, String password, String privileges) {
        this.id = id;
        this.password = password;
        this.privileges = privileges;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPrivileges() {
        return privileges;
    }

    public void setPrivileges(String privileges) {
        this.privileges = privileges;
    }
}
