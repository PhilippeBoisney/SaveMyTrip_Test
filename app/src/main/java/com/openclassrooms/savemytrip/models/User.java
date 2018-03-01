package com.openclassrooms.savemytrip.models;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Philippe on 28/02/2018.
 */

@Entity
public class User {

    @PrimaryKey
    private long id;

    private String username;
    private String urlPicture;

    @Embedded
    public Address address;

    public User(long id, String username, String urlPicture, Address address) {
        this.id = id;
        this.username = username;
        this.urlPicture = urlPicture;
        this.address = address;
    }

    // --- GETTER ---

    public long getId() { return id; }
    public String getUsername() { return username; }
    public String getUrlPicture() { return urlPicture; }
    public Address getAddress() { return address; }

    // --- SETTER ---

    public void setId(long id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setUrlPicture(String urlPicture) { this.urlPicture = urlPicture; }
    public void setAddress(Address address) { this.address = address; }

}
