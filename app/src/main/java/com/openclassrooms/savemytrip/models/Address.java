package com.openclassrooms.savemytrip.models;

/**
 * Created by Philippe on 28/02/2018.
 */

public class Address {

    private String street;
    private String state;
    private String city;
    private int postCode;

    public Address(String street, String state, String city, int postCode) {
        this.street = street;
        this.state = state;
        this.city = city;
        this.postCode = postCode;
    }

    // --- GETTER ---

    public String getStreet() { return street; }
    public String getState() { return state; }
    public String getCity() { return city; }
    public int getPostCode() { return postCode; }

    // --- SETTER ---

    public void setStreet(String street) { this.street = street; }
    public void setState(String state) { this.state = state; }
    public void setCity(String city) { this.city = city; }
    public void setPostCode(int postCode) { this.postCode = postCode; }

}
