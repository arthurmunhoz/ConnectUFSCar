package br.ufscar.connect.Models;

import android.app.Application;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Arthur on 06/12/2016.
 */
public class Report extends Application {

    //Declarando atributos
    @SerializedName("image_url")
    private String problemPhoto;
    @SerializedName("address")
    private String problemAddress;
    @SerializedName("description")
    private String problemDescription;
    @SerializedName("category")
    private String problemCategory;
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("created_at")
    private String date;


    //Metodos
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Report(String problemPhoto, String problemAddress, String problemDescription,
                  String problemCategory, String date, String user_id) {
        this.problemPhoto = problemPhoto;
        this.problemAddress = problemAddress;
        this.problemDescription = problemDescription;
        this.problemCategory = problemCategory;
        this.date = date;
        this.user_id = user_id;
    }

    public Report() {
    }

    public String getProblemPhoto() {
        return problemPhoto;
    }

    public void setProblemPhoto(String problemPhoto) {
        this.problemPhoto = problemPhoto;
    }

    public String getProblemAddress() {
        return problemAddress;
    }

    public void setProblemAddress(String problemAddress) {
        this.problemAddress = problemAddress;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public String getProblemCategory() {
        return problemCategory;
    }

    public void setProblemCategory(String problemCategory) {
        this.problemCategory = problemCategory;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
