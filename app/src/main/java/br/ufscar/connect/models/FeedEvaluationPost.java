package br.ufscar.connect.models;

/**
 * Created by bruno on 04/02/17.
 */

public class FeedEvaluationPost {
    int userPhotoId;
    String userName;
    String userType;
    String date;
    String placeName;
    float ratingInfra;
    float ratingAcess;
    float ratingLimpeza;
    float ratingSeguranca;
    float ratingGeral;

    public FeedEvaluationPost(int userPhotoId, String userName, String userType, String date, String placeName, float ratingInfra, float ratingAcess, float ratingLimpeza, float ratingSeguranca, float ratingGeral) {
        this.userPhotoId = userPhotoId;
        this.userName = userName;
        this.userType = userType;
        this.date = date;
        this.placeName = placeName;
        this.ratingInfra = ratingInfra;
        this.ratingAcess = ratingAcess;
        this.ratingLimpeza = ratingLimpeza;
        this.ratingSeguranca = ratingSeguranca;
        this.ratingGeral = ratingGeral;
    }

    public int getUserPhotoId() {
        return userPhotoId;
    }

    public void setUserPhotoId(int userPhotoId) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public float getRatingInfra() {
        return ratingInfra;
    }

    public void setRatingInfra(float ratingInfra) {
        this.ratingInfra = ratingInfra;
    }

    public float getRatingAcess() {
        return ratingAcess;
    }

    public void setRatingAcess(float ratingAcess) {
        this.ratingAcess = ratingAcess;
    }

    public float getRatingLimpeza() {
        return ratingLimpeza;
    }

    public void setRatingLimpeza(float ratingLimpeza) {
        this.ratingLimpeza = ratingLimpeza;
    }

    public float getRatingSeguranca() {
        return ratingSeguranca;
    }

    public void setRatingSeguranca(float ratingSeguranca) {
        this.ratingSeguranca = ratingSeguranca;
    }

    public float getRatingGeral() {
        return ratingGeral;
    }

    public void setRatingGeral(float ratingGeral) {
        this.ratingGeral = ratingGeral;
    }
}
