package com.example.finalyearproject_demo1;

public class orderProfile {
    String name;
    String mobileno;
    String order;
    String permission;
    String email;


    public orderProfile() {
    }

    public orderProfile(String email,String name, String mobileno, String order, String permission) {
        this.name = name;
        this.mobileno = mobileno;
        this.order = order;
        this.permission = permission;
        this.email=email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
