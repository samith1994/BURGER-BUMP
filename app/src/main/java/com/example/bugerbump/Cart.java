package com.example.bugerbump;

public class Cart
{
    public String quantity, foodName, foodPrice;

    public Cart()
    {

    }

    public Cart(String quantity, String foodName, String foodPrice) {
        this.quantity = quantity;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }
}
