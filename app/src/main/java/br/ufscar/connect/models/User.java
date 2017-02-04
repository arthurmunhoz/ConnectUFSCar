package br.ufscar.connect.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Arthur on 18/11/2016.
 */
public class User {

    @SerializedName("usertype")
    private String user_type;
    private String username;
    private String name;
    @SerializedName("lastname")
    private String last_name;
    private String email;
    private String password;
    @SerializedName("image_url")
    private String user_photo;
    @SerializedName("_id")
    private String id;


    public User() {
    }

    public User(String user_type, String username, String name, String last_name, String email, String password, String user_photo, String id) {
        this.user_type = user_type;
        this.username = username;
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.user_photo = user_photo;
        this.id = id;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User(String user_type, String name, String last_name, String email, String password, String user_id) {

        this.user_type = user_type;
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.id = user_id;
    }

    public String getUser_photo() {
        return user_photo;
    }

    public void setUser_photo(String user_photo) {
        this.user_photo = user_photo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_id() {
        return id;
    }

    public void setUser_id(String user_id) {
        this.id = user_id;
    }
}

