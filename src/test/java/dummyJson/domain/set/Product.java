package dummyJson.domain.set;

public class Product {
    public String title;
    public String description;
    public Integer price;
    public Number discountPercentage;
    public Float rating;
    public Integer stock;
    public String brand;
    public String category;
    public String thumbnail;

    public Product(String title, String description, Integer price, Number discountPercentage, Float rating, Integer stock, String brand, String category, String thumbnail) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.discountPercentage = discountPercentage;
        this.rating = rating;
        this.stock = stock;
        this.brand = brand;
        this.category = category;
        this.thumbnail = thumbnail;
    }

}
