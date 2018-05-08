package com.codecool.plaza.api;

import com.codecool.plaza.exceptions.*;
import java.util.ArrayList;
import java.util.List;

public class PlazaImpl implements Plaza {

    private List<Shop> shops;
    private String owner;
    private String name;
    private boolean open;

    public PlazaImpl(String owner, String name) {
        shops = new ArrayList<>();
        this.owner = owner;
        this.name = name;
        open = false;
    }

    public String getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public List<Shop> getShops() throws PlazaIsClosedException {
        if (isOpen()) {
            return shops;
        } else {
            throw new PlazaIsClosedException("Plaza is closed.");
        }
    }

    public void addShop(Shop shop) throws ShopAlreadyExistsException, PlazaIsClosedException {
        for (Shop tempShop : shops) {
            if (tempShop.getName().equalsIgnoreCase(shop.getName())) {
                throw new ShopAlreadyExistsException("Shop already exists.");
            }
        }
        shops.add(shop);
    }

    public void removeShop(Shop shop) throws NoSuchShopException, PlazaIsClosedException {
        if (isOpen()){
            if(shops.contains(shop)) {
                shops.remove(shop);
            } else {
                throw new NoSuchShopException("No such shop in plaza.");
            }
        } else {
            throw new PlazaIsClosedException("Plaza is closed.");
        }
    }

    public Shop findShopByName(String name) throws NoSuchShopException, PlazaIsClosedException {
        if (isOpen()) {
            for (Shop temp : shops) {
                if (name.equalsIgnoreCase(temp.getName())) {
                    return temp;
                }
            }
            throw new NoSuchShopException("No such shop in plaza.");
        }
        throw new PlazaIsClosedException("Plaza is closed.");
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

}