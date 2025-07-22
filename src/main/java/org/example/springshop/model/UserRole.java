package org.example.springshop.model;

import lombok.Getter;

@Getter
public enum UserRole {

    // 0    1      2
    USER(0L , "user"),
    OWNER(1L , "owner"),
    ADMIN(2L , "admin");


    private Long id ;

    private String roleName ;

    UserRole(Long id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }
}
