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
    int publPhotoId;
    int userPhotoId;

    public FeedProblemPost(String userName, String userType, String publDate, String address, String type, String description, int publPhotoId, int userPhotoId) {
        this.userName = userName;
        this.userType = userType;
        this.publDate = publDate;
        this.address = address;
        this.type = type;
        this.description = description;
        this.publPhotoId = publPhotoId;
        this.userPhotoId = userPhotoId;
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

    public int getPublPhotoId() {
        return publPhotoId;
    }

    public void setPublPhotoId(int publPhotoId) {
        this.publPhotoId = publPhotoId;
    }

    public int getUserPhotoId() {
        return userPhotoId;
    }

    public void setUserPhotoId(int userPhotoId) {
        this.userPhotoId = userPhotoId;
    }
}
