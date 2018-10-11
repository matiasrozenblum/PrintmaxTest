package com.example.matirozen.printmaxtest.Model;

public class Price {
    private String codigo;
    private String precioa;
    private String preciob;
    private String precioc;
    private String preciod;
    private String precioe;

    public Price() {

    }

    public Price(String codigo, String precioa, String preciob, String precioc, String preciod, String precioe) {
        this.codigo = codigo;
        this.precioa = precioa;
        this.preciob = preciob;
        this.precioc = precioc;
        this.preciod = preciod;
        this.precioe = precioe;

    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getprecioa() {
        return precioa;
    }

    public void setprecioa(String precioa) {
        this.precioa = precioa;
    }

    public String getpreciob() {
        return preciob;
    }

    public void setpreciob(String preciob) {
        this.preciob = preciob;
    }

    public String getprecioc() {
        return precioc;
    }

    public void setprecioc(String precioc) {
        this.precioc = precioc;
    }

    public String getpreciod() {
        return preciod;
    }

    public void setpreciod(String preciod) {
        this.preciod = preciod;
    }

    public String getprecioe() {
        return precioe;
    }

    public void setprecioe(String precioe) {
        this.precioe = precioe;
    }

    @Override
    public String toString() {
        return "Price{" +
                "codigo='" + codigo + '\'' +
                ", precioa='" + precioa + '\'' +
                ", preciob='" + preciob + '\'' +
                ", precioc='" + precioc + '\'' +
                ", preciod='" + preciod + '\'' +
                ", precioe='" + precioe + '\'' +
                '}';
    }
}
