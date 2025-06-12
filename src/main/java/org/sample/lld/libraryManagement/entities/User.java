package org.sample.lld.libraryManagement.entities;


public class User {
    private String id;
    private String name;
    private UserType userType;

    enum UserType {
        ADMIN, USER;
    }
}
