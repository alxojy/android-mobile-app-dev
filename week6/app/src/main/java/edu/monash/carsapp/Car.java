package edu.monash.carsapp;

public class Car {
    private String maker;
    private String model;
    private int year;
    private String color;
    private int seats;
    private double price;

    public Car(String maker, String model, int year, String color, int seats, double price) {
        this.maker = maker;
        this.model = model;
        this.year = year;
        this.color = color;
        this.seats = seats;
        this.price = price;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return Integer.toString(year);
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSeats() {
        return Integer.toString(seats);
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getPrice() {
        return Double.toString(price);
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
