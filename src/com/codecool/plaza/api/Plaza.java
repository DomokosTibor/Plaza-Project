package com.codecool.plaza.api;

import com.codecool.plaza.exceptions.*;
import java.util.List;

public interface Plaza {

    List<Shop> getShops() throws PlazaIsClosedException;
    void addShop(Shop shop) throws ShopAlreadyExistsException, PlazaIsClosedException;
    void removeShop(Shop shop) throws NoSuchShopException, PlazaIsClosedException;
    Shop findShopByName(String name) throws NoSuchShopException, PlazaIsClosedException;
    boolean isOpen();
    void open();
    void close();
    String toString();

}