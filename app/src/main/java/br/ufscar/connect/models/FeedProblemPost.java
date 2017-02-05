package br.ufscar.connect.models;

/**
 * Created by bruno on 04/02/17.
 */

public class FeedProblemPost {
    String userName;
    String userType;
    String publDate;
    String address;
    String type;
    String description;
    String photoUrl;
    String userUrl;

    public FeedProblemPost(String userName, String userType, String publDate, String address, String type, String description, String photoUrl, String userUrl) {
        this.userName = userName;
        this.userType = userType;
        this.publDate = publDate;
        this.address = address;
        this.type = type;
        this.description = description;
        this.photoUrl = photoUrl;
        this.userUrl = userUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPublDate() {
        return publDate;
    }

    public void setPublDate(String publDate) {
        this.publDate = publDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }
}
