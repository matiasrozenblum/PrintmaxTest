package com.example.matirozen.printmaxtest.Model;

public class Price {
    private String codigo;
    private String precioA;
    private String precioB;
    private String precioC;
    private String precioD;
    private String precioE;

    public Price() {

    }

    public Price(String codigo, String precioA, String precioB, String precioC, String precioD, String precioE) {
        this.codigo = codigo;
        this.precioA = precioA;
        this.precioB = precioB;
        this.precioC = precioC;
        this.precioD = precioD;
        this.precioE = precioE;

    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getPrecioA() {
        return precioA;
    }

    public void setPrecioA(String precioA) {
        this.precioA = precioA;
    }

    public String getPrecioB() {
        return precioB;
    }

    public void setPrecioB(String precioB) {
        this.precioC = precioB;
    }

    public String getPrecioC() {
        return precioC;
    }

    public void setPrecioC(String precioC) {
        this.precioC = precioC;
    }

    public String getPrecioD() {
        return precioD;
    }

    public void setPrecioD(String precioD) {
        this.precioD = precioD;
    }

    public String getPrecioE() {
        return precioE;
    }

    public void setPrecioE(String precioE) {
        this.precioE = precioE;
    }

    @Override
    public String toString() {
        return "Price{" +
                "codigo='" + codigo + '\'' +
                ", precioA='" + precioA + '\'' +
                ", precioB='" + precioB + '\'' +
                ", precioC='" + precioC + '\'' +
                ", precioD='" + precioD + '\'' +
                ", precioE='" + precioE + '\'' +
                '}';
    }
}
