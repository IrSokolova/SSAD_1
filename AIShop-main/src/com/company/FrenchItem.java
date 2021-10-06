package com.company;

public class FrenchItem extends Item {

    public FrenchItem(String type, double price, String color, String print, String origin) {
        super(type, price, color, print, origin);
        this.setOrigin("France");
        this.setStyle("Quotidien");
    }
}