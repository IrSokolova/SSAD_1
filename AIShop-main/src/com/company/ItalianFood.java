package com.company;

public class ItalianFood extends Food {

    public ItalianFood(String type, double price, String kitchen) {
        super(type, price, kitchen);
        this.setKitchen("Italian");
        this.setTaste("Aggraziato");
    }
}
