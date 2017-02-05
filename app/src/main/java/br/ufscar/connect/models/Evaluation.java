package br.ufscar.connect.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Arthur on 06/12/2016.
 */
public class Evaluation  {

    @SerializedName("user_id")
    private String userId;
    @SerializedName("placename")
    private String espaco;
    @SerializedName("created_at")
    private String date;
    @SerializedName("infra")
    private float infra;
    @SerializedName("limpeza")
    private float limp;
    @SerializedName("acess")
    private float acess;
    @SerializedName("seg")
    private float seg;
    @SerializedName("geral")
    private float geral;

    public Evaluation() {
    }

    public Evaluation(String userId, String espaco, String date, float infra, float limp, float acess, float seg, float geral) {
        this.userId = userId;
        this.espaco = espaco;
        this.date = date;
        this.infra = infra;
        this.limp = limp;
        this.acess = acess;
        this.seg = seg;
        this.geral = geral;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEspaco() {
        return espaco;
    }

    public void setEspaco(String espaco) {
        this.espaco = espaco;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getInfra() {
        return infra;
    }

    public void setInfra(float infra) {
        this.infra = infra;
    }

    public float getLimp() {
        return limp;
    }

    public void setLimp(float limp) {
        this.limp = limp;
    }

    public float getAcess() {
        return acess;
    }

    public void setAcess(float acess) {
        this.acess = acess;
    }

    public float getSeg() {
        return seg;
    }

    public void setSeg(float seg) {
        this.seg = seg;
    }

    public float getGeral() {
        return geral;
    }

    public void setGeral(float geral) {
        this.geral = geral;
    }
}
