package com.example.matirozen.printmaxtest.Model;

public class Banner {
    private String id;
    private String name;
    private String link;

    public Banner() {
    }

    public String getID() {
        return id;
    }

    public void setID(String ID) {
        this.id = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                '}';
    }

    public Banner(String id, String name, String link) {
        this.id = id;
        this.name = name;
        this.link = link;
    }

    public String getLink() {

        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
