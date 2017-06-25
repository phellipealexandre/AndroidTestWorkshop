package com.thoughtworks.pafsilva.testautomationworkshop.model;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String name;
    private String username;
    private String email;
    private Address address;
    private String phone;
    private String website;
    private Company company;

    public String getName() {
        return name;
    }
}
