package com.codecool.plaza.api;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FoodProduct extends Product {

    private int calories;
    private Date bestBefore;

    public FoodProduct(long barcode, String manufacturer, String name, int calories, Date bestBefore) {
        super(barcode, manufacturer, name);
        this.calories = calories;
        this.bestBefore = bestBefore;
    }

    private boolean isStillConsumable() {
        Date date = new Date();
        String simpleBestBefore = new SimpleDateFormat("YYYY-MM-DD").format(bestBefore);
        String simpleCurrDate = new SimpleDateFormat("YYYY-MM-DD").format(date);
        int compareDates = simpleBestBefore.compareTo(simpleCurrDate);
        return compareDates <= 0;
    }

    public int getCalories(){
        return calories;
    }

    public Date getBestBefore() {
        return bestBefore;
    }

    public String toString() {
        return "Barcode:" + barcode + "\n" +
                "Manufacturer:" + manufacturer + "\n" +
                "Name:" + name + "\n" +
                "Calories:" + calories + "\n" +
                "Still eatable: " +isStillConsumable();
    }

}