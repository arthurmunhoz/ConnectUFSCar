package br.ufscar.connect.Models;

import android.app.Application;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Arthur on 06/12/2016.
 */
public class Evaluation extends Application {

    //Declarando atributos
    @SerializedName("infra")
    private float Infra;
    @SerializedName("limpeza")
    private float Limp;
    @SerializedName("acess")
    private float Acess;
    @SerializedName("seg")
    private float Seg;
    @SerializedName("geral")
    private float Geral;
    @SerializedName("placename")
    private String espaco;
    @SerializedName("created_at")
    private Date date;

    //Construtor
    public void Evaluation(float infra, float limp, float acess, float seg, float geral) {

        this.Infra = infra;
        this.Limp = limp;
        this.Acess = acess;
        this.Seg = seg;
        this.Geral = geral;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    //Construtor vazio
    public void Evaluation() {
    }

    public float getInfra() {
        return Infra;
    }

    public void setInfra(float infra) {
        this.Infra = infra;
    }

    public float getLimp() {
        return Limp;
    }

    public void setLimp(float limp) {
        this.Limp = limp;
    }

    public float getGeral() {
        return Geral;
    }

    public void setGeral(float geral) {
        this.Geral = geral;
    }

    public float getAcess() {
        return Acess;
    }

    public void setAcess(float acess) {
        this.Acess = acess;
    }

    public float getSeg() {
        return Seg;
    }

    public void setSeg(float seg) {
        this.Seg = seg;
    }

    public String getEspaco() {
        return espaco;
    }

    public void setEspaco(String espaco) {
        this.espaco = espaco;
    }
}
