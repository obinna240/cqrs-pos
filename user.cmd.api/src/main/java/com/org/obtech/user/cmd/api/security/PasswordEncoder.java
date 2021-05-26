package com.org.obtech.user.cmd.api.security;

public interface PasswordEncoder {
    String hashPassword(String password);
}
