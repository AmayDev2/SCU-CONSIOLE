package com.amay.scu.auth;

import com.amay.scu.controller.HeaderController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.Getter;

public class AuthService {

    @Getter
    private String username;
    private final BooleanProperty isAuthenticated = new SimpleBooleanProperty(false);
    private final HeaderController authenticated;

    public AuthService(HeaderController authenticated) {
        this.authenticated = authenticated;
        isAuthenticated.setValue(false);
    }

    public boolean login(String username, String password) {
        if (username.equals("admin") && password.equals("admin")) {
            this.username = username;
            isAuthenticated.setValue(true);
            authenticated.authenticated();
            return true;
        }
        else {
            return false;
        }
    }

    public boolean logout() {
        isAuthenticated.setValue(false);
        authenticated.logout();
        return true;
    }

    public BooleanProperty isAuthenticated() {
        return isAuthenticated;
    }

}
