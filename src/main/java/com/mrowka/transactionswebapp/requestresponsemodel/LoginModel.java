package com.mrowka.transactionswebapp.requestresponsemodel;

public class LoginModel {
    private String login;
    private String password;

    public LoginModel(String login, String password) {
        this.login = login;
        this.password = password;
    }
    public LoginModel(){

    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginModel{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
