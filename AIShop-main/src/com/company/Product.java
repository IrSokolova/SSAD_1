package com.company;

import java.util.HashSet;
import java.util.Set;

public class Product {
    protected static int idIterator = 0;

    private int id;
    private String type;
    private double price;
    private Set<String> keywords = new HashSet<>();

    /**
     * @param userKeyword : the keyword
     * @return : does the keyword belong to this product?
     */
    public boolean isApplicable(String userKeyword) {
        return this.keywords.contains(userKeyword);
    }

    public Product(String type, double price) {
        this.id = idIterator;
        idIterator++;

        this.type = type;
        this.price = price;

        this.keywords.add(type);
        this.keywords.add(String.valueOf(id));
    }

    public void printProduct(){
        if (this instanceof Item) {
            System.out.println("id: " + this.getId() + "; type: " + this.getType() +
                    "; price: " + this.getPrice() + "; color: " + ((Item)this).getColor() +
                    "; print: " + ((Item)this).getPrint() + "; origin: " + ((Item)this).getOrigin() +
                    "; style: " + ((Item)this).getStyle());
        } else {
            System.out.println("id: " + this.getId() + "; type: " + this.getType() +
                    "; price: " + this.getPrice() + "; kitchen: " + ((Food)this).getKitchen() +
                    "; taste: " + ((Food)this).getTaste());
        }
    }

    public Set<String> getKeywords() {
        return this.keywords;
    }

    public double getPrice() {
        return this.price;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }
}
