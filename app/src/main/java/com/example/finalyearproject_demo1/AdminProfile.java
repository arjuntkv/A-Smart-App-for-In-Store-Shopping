package com.example.finalyearproject_demo1;

public class AdminProfile {
    private String id;
    private String name;
    private String amount;
    private String quantity;
    private String quan;

    public AdminProfile() {
    }

    public AdminProfile(String id,String name, String amount, String quantity, String quan) {
        this.id=id;
        this.name = name;
        this.amount = amount;
        this.quantity = quantity;
        this.quan = quan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getQuan() {
        return quan;
    }

    public void setQuan(String quan) {
        this.quan = quan;
    }
}
