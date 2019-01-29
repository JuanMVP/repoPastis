package com.st.pillboxapp.responses;

import com.st.pillboxapp.models.User;

public class AuthAndRegisterResponse {

    private String token;
    private User user;

    public AuthAndRegisterResponse(){}

    public AuthAndRegisterResponse(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "AuthAndRegisterResponse{" +
                "token='" + token + '\'' +
                ", user=" + user +
                '}';
    }
}
