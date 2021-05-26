package com.org.obtech.user.core.models;

import org.springframework.security.core.GrantedAuthority;

//Roles must comply to the granted athority role that are part of
// spring bot starter security we must implement Spring boot GrantedAuthority method
public enum Role implements GrantedAuthority {

    //let's implement roles for read and write privileges
    READ_PRIVILEGE, WRITE_PRIVILEGE;

    @Override
    public String getAuthority() {
        return name();
    }
}
