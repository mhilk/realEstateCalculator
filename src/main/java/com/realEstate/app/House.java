package com.realEstate.app;


public class House {
    public final double price;
    public final double hoaFees;
    public final double yearlyTaxes;

    public House(double price, double hoaFees, double yearlyTaxes) {
        this.price = price;
        this.hoaFees = hoaFees;
        this.yearlyTaxes = yearlyTaxes;
    }
}
