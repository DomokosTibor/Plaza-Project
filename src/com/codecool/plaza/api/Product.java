package com.codecool.plaza.api;

public abstract class Product {

    protected long barcode;
    protected String manufacturer;
    protected String name;

    protected Product(long barcode, String manufacturer, String name) {
        this.barcode = barcode;
        this.manufacturer = manufacturer;
        this.name = name;
    }

    public long getBarcode() {
        return barcode;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return "Barcode:" + barcode + "\n" +
                "Manufacturer:" + manufacturer + "\n" +
                "Name:" + name;
    }

}