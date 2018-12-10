package com.example.blue.shoppinglistorganizer;

public class Grocery {

    String name;
    String category;
    Float price;

    Grocery(String _name, String _category, Float _price){
        name = _name;
        category = _category;
        price = _price;
    }

    public String GetName(){
        return name;
    }
}
