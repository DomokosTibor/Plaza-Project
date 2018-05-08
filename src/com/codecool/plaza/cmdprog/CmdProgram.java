package com.codecool.plaza.cmdprog;

import com.codecool.plaza.exceptions.*;
import com.codecool.plaza.api.*;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CmdProgram {

    private List<Product> cart;
    private Scanner sc = new Scanner(System.in);
    private SimpleDateFormat date = new SimpleDateFormat("MM-DD-YYY");


    CmdProgram() {
        cart = new ArrayList<>();
    }

    public void run() {
        PlazaImpl myPlaza;
        String ownerName;
        label:
        while (true) {
            System.out.println("There are no plaza created yet! Press\n" +
                    "1) to create one.\n" +
                    "2) to exit.");
            String menuChoice = sc.nextLine();
            switch (menuChoice) {
                case "1":
                    System.out.print("Enter the owner of the Plaza: ");
                    ownerName = sc.nextLine();
                    System.out.print("Enter the name of the Plaza: ");
                    String plazaName = sc.nextLine();
                    myPlaza = new PlazaImpl(ownerName, plazaName);
                    runPlazaMenu(myPlaza);
                    break label;
                case "2":
                    System.out.println("Thanks for coming! Goodbye!");
                    System.exit(1);
                default:
                    System.out.println("Invalid option, please try again.");
                    break;
            }
        }
    }

    private void runPlazaMenu(PlazaImpl myPlaza) {
        while (true) {
            int number;
            ShopImpl myShop;
            String tempShop;
            String tempOwner;

            while (true) {
                System.out.println("\nWelcome to the " + myPlaza.getName() + "! Press\n" +
                        "1) to list all shops.\n" +
                        "2) to add a new shop.\n" +
                        "3) to remove an existing shop.\n" +
                        "4) enter a shop by name.\n" +
                        "5) to open the plaza.\n" +
                        "6) to close the plaza.\n" +
                        "7) to check if the plaza is open or not.\n" +
                        "0) leave plaza.");
                String option = sc.nextLine();
                try {
                    number = Integer.parseInt(option);
                    break;
                }
                catch (NumberFormatException e) {
                    System.out.println("Only numbers accepted!");
                }
            }
            try {
                switch (number) {
                    case 1:
                        for (Shop shop : myPlaza.getShops()) {
                            System.out.println(shop.getOwner() + "'s " + shop.getName());
                        }
                        break;
                    case 2:
                        if (myPlaza.isOpen()) {
                            System.out.print("Enter the owner of the shop: ");
                            tempOwner = sc.nextLine();
                            System.out.print("Enter the name of the shop: ");
                            tempShop = sc.nextLine();
                            try {
                                myPlaza.addShop(new ShopImpl(tempShop, tempOwner));
                            }
                            catch (ShopAlreadyExistsException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        else {
                            throw new PlazaIsClosedException("You cannot add a new shop, because the plaza is closed.");
                        }
                        break;
                    case 3:
                        for (Shop shop : myPlaza.getShops()) {
                            System.out.println(shop.getName());
                        }
                        System.out.println("Which shop do you want to remove?");
                        tempShop = sc.nextLine();
                        try {
                            myPlaza.removeShop(myPlaza.findShopByName(tempShop));
                            System.out.println(tempShop + " is removed from the plaza");
                        }
                        catch (NoSuchShopException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 4:
                        for (Shop shop : myPlaza.getShops()) {
                            System.out.println(shop.getName());
                        }
                        System.out.println("Which shop do you want to go in?");
                        tempShop = sc.nextLine();
                        try {
                            myShop = (ShopImpl) myPlaza.findShopByName(tempShop);
                            runShopMenu(myShop);
                            break;
                        }
                        catch (NoSuchShopException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 5:
                        myPlaza.open();
                        System.out.println("You have opened the Plaza\n");
                        break;
                    case 6:
                        myPlaza.close();
                        System.out.println("You have closed the Plaza\n");
                        break;
                    case 7:
                        if (myPlaza.isOpen()) {
                            System.out.println(myPlaza.getName() + " is open");
                        }
                        else {
                            System.out.println(myPlaza.getName() + " is closed");
                        }
                        break;
                    case 0:
                        System.out.println("Goodbye!");
                        System.exit(1);

                }
            } catch (PlazaIsClosedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void runShopMenu(ShopImpl myShop) {
        boolean running = true;
        while (running) {
            System.out.println("Hi! This is the " + myShop.getName() + ", welcome! Press\n" +
                    "1) to list available products.\n" +
                    "2) to find products by name.\n" +
                    "3) to display the shop's owner.\n" +
                    "4) to open the shop.\n" +
                    "5) to close the shop.\n" +
                    "6) to add new product to the shop.\n" +
                    "7) to add existing products to the shop.\n" +
                    "8) to buy a product by barcode.\n" +
                    "0) go back to plaza");
            String option = sc.nextLine();
            try {
                int subNum = Integer.parseInt(option);
                switch (subNum) {
                    case 1:
                        try {
                            System.out.println(myShop.toString());
                        }
                        catch (IndexOutOfBoundsException e) {
                            System.out.println("There's no item.");
                        }
                        break;
                    case 2:
                        System.out.print("Enter the item name: ");
                        String itemName = sc.nextLine();
                        try {
                            System.out.println(myShop.findByName(itemName).get(0).toString());
                        }
                        catch (IndexOutOfBoundsException e) {
                            System.out.println("No such item in this shop");
                        }
                        break;
                    case 3:
                        System.out.println("The owner of this shop is " + myShop.getOwner());
                        break;
                    case 4:
                        myShop.open();
                        System.out.println("You have opened the shop");
                        break;
                    case 5:
                        myShop.close();
                        System.out.println("You have closed the shop");
                        break;
                    case 6:
                        Product product = null;
                        boolean creatingProduct = true;
                        while (creatingProduct) {
                            String[] headerCloth = new String[] {"Enter the barcode: ", "Enter the manufacturer: ",
                                    "Enter the name: ", "Enter the material: ", "Enter the type: "};
                            String[] headerFood = new String[] {"Enter the barcode: ", "Enter the manufacturer: ",
                                    "Enter the name: ", "Enter the calorie: ", "Enter the date: (yyyy-mm-dd)"};
                            System.out.println("What kind of product do you want to create? (Cloth or Food)");
                            String clothOrFood = sc.nextLine().toLowerCase();
                            switch (clothOrFood) {
                                case "cloth": {
                                    String[] attributes = new String[headerCloth.length];
                                    for (int i = 0; i < headerCloth.length; i++) {
                                        System.out.println(headerCloth[i]);
                                        attributes[i] = sc.nextLine().toLowerCase();
                                    }
                                    String tempManufacturer = attributes[1];
                                    String tempName = attributes[2];
                                    String tempMaterial = attributes[3];
                                    String tempType = attributes[4];
                                    try {
                                        long tempBarcode = Long.parseLong(attributes[0]);
                                        product = new ClothingProduct(tempBarcode, tempManufacturer, tempName, tempMaterial, tempType);
                                        creatingProduct = false;
                                    }
                                    catch (NumberFormatException e) {
                                        System.out.println(e.getMessage());
                                    }
                                    break;
                                }
                                case "food": {
                                    String[] attributes = new String[headerFood.length];
                                    for (int i = 0; i < headerFood.length; i++) {
                                        System.out.println(headerFood[i]);
                                        attributes[i] = sc.nextLine().toLowerCase();
                                    }
                                    String tempManufacturer = attributes[1];
                                    String tempName = attributes[2];
                                    try {
                                        long tempBarcode = Long.parseLong(attributes[0]);
                                        int tempCalorie = Integer.parseInt(attributes[3]);
                                        Date tempDate = date.parse(attributes[4]);
                                        product = new FoodProduct(tempBarcode, tempManufacturer, tempName, tempCalorie, tempDate);
                                        creatingProduct = false;
                                    }
                                    catch (ParseException e) {
                                        System.out.println(e.getMessage());
                                    }
                                    break;
                                }
                                default:
                                    System.out.println("Invalid option! Please try again!");
                                    break;
                            }
                        }
                        System.out.print("Enter the quantity: ");
                        String tempQuantityS = sc.nextLine();
                        System.out.print("Enter the price: ");
                        String tempPriceS = sc.nextLine();
                        try {
                            int tempQuantity = Integer.parseInt(tempQuantityS);
                            float tempPrice = Float.parseFloat(tempPriceS);
                            myShop.addNewProduct(product, tempQuantity, tempPrice);
                        }
                        catch (ProductAlreadyExistsException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 0:
                        running = false;
                }
            } catch (ShopIsClosedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}