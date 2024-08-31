package com.amay.scu.auth;

import com.amay.scu.controller.HeaderController;
import lombok.Getter;

public class AuthService {

    @Getter
    private String username;
    private boolean isAuthenticated = false;
    private HeaderController authenticated;

    public AuthService(HeaderController authenticated) {
        this.authenticated = authenticated;
    }

    public boolean login(String username, String password) {
        if (username.equals("admin") && password.equals("admin")) {
            this.username = username;
            isAuthenticated = true;
            authenticated.authenticated();
            return true;
        }
        else {
            return false;
        }
    }

    public boolean logout() {
        isAuthenticated = false;
        authenticated.logout();
        return true;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

}
