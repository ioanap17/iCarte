package ro.upb.cs.thesis.icarte.models;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public class Product {
    public String title, author;
    public Drawable productImage;
    public String description;
    public double price;
    public boolean selected;

    public Product(String title, String author, Drawable productImage, String description, double price) {
        this.title = title;
        this.author = author;
        this.productImage = productImage;
        this.description = description;
        this.price = price;
    }
}
