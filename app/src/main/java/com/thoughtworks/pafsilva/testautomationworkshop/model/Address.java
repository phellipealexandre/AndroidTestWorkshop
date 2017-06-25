package com.thoughtworks.pafsilva.testautomationworkshop.model;

import java.io.Serializable;

class Address implements Serializable {
    private String street;
    private String suite;
    private String city;
    private String zipcode;
    private Geo geo;
}
