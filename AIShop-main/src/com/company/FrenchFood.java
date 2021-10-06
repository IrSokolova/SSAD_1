package com.company;

public class FrenchFood extends Food {

    public FrenchFood(String type, double price, String kitchen) {
        super(type, price, kitchen);
        this.setKitchen("French");
        this.setTaste("Gracieux");
    }
}