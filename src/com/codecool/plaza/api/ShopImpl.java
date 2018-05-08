package com.codecool.plaza.api;

import com.codecool.plaza.exceptions.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopImpl implements Shop {

    private String name;
    private String owner;
    private Map<Long, ShopImplEntry> products;
    private boolean open;

    public ShopImpl(String name, String owner){
        this.name = name;
        this.owner = owner;
        products = new HashMap<>();
        open = false;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public Map<Long, ShopImplEntry> getProducts() {
        return products;
    }

    public boolean isOpen() {
        return open;
    }

    public void open() {
        open = true;
    }

    public void close() {
        open = false;
    }

    public List<Product> findByName(String name) throws ShopIsClosedException {
        List<Product> productList = new ArrayList<>();
        if (isOpen()) {
            for (ShopImplEntry temp : products.values()) {
                if (temp.getProduct().getName().equalsIgnoreCase(name)) {
                    productList.add(temp.getProduct());
                }
            }
        }
        else {
            throw new ShopIsClosedException("Shop is closed.");
        }
        return productList;
    }

    public boolean hasProduct(long barcode) throws ShopIsClosedException {
        if (isOpen()) {
            return products.containsKey(barcode);
        }
        else {
            throw new ShopIsClosedException("Shop is closed.");
        }
    }

    public void addNewProduct(Product product, int quantity, float price) throws ProductAlreadyExistsException, ShopIsClosedException {
        if(isOpen()){
            if(!hasProduct(product.getBarcode())){
                products.put(product.getBarcode(), new ShopImplEntry(product, quantity, price));
            }else{
                throw new ProductAlreadyExistsException("This product already exist.");
            }
        }
        else {
            throw new ShopIsClosedException("Shop is closed.");
        }
    }

    public Product buyProduct(long barcode) throws NoSuchProductException, ShopIsClosedException {
        return null;
    }

    public List<Product> buyProducts(long barcode, int quantity) throws NoSuchProductException, OutOfStockException, ShopIsClosedException {
        return null;
    }

    public String toString() {
        String allProducts = "";

        allProducts += "Clothing Products: ";
        for (Map.Entry<Long, ShopImplEntry> entry : products.entrySet()) {
            if (entry.getValue().getProduct() instanceof ClothingProduct) {
                allProducts += "\n" + entry.getValue().getProduct().toString() + " Quantity: " + entry.getValue().getQuantity() + " Price: $" + entry.getValue().getPrice();
            }
        }

        allProducts += "\nFood Products: ";
        for (Map.Entry<Long, ShopImplEntry> entry : products.entrySet()) {
            if (entry.getValue().getProduct() instanceof FoodProduct) {
                allProducts += "\n" + entry.getValue().getProduct().toString() + " Quantity: " + entry.getValue().getQuantity() + " Price: $" + entry.getValue().getPrice();
            }
        }
        return allProducts;
    }

    class ShopImplEntry {

        private Product product;
        private int quantity;
        private float price;

        ShopImplEntry(Product product, int quantity, float price) {
            this.product = product;
            this.quantity = quantity;
            this.price = price;
        }

        public Product getProduct(){
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public void increaseQuantity(int amount) {
            this.quantity += amount;
        }

        private void decreaseQuantity(int amount) throws NoSuchProductException {
            if (quantity - amount < 0) {
                throw new NoSuchProductException("No more available products.");
            } else {
                this.quantity -= amount;
            }
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }

}