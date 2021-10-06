package com.company;

public class ItalianItem extends Item {

    public ItalianItem(String type, double price, String color, String print, String origin) {
        super(type, price, color, print, origin);
        this.setOrigin("Italy");
        this.setStyle("Ordinario");
    }
}