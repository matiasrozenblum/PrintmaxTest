package com.example.matirozen.printmaxtest.model;

public class User {
    private String phone;
    private String address;
    private String name;
    private String birth;

    public User(String phone, String address, String name, String birth) {
        this.phone = phone;
        this.address = address;
        this.name = name;
        this.birth = birth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    @Override
    public String toString() {
        return "User{" +
                "phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", birth='" + birth + '\'' +
                '}';
    }
}
