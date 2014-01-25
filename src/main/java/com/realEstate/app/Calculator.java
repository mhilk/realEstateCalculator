package com.realEstate.app;


import java.util.ArrayList;
import java.util.List;

public class Calculator {


    private SearchParameters searchParameters;

    public Calculator(SearchParameters searchParameters) {

        this.searchParameters = searchParameters;
    }

    public List<House> filterHouses(List<House> houses) {
        ArrayList<House> passingHouses = new ArrayList<House>();
        for (House house : houses) {
            double buyingFriction=searchParameters.costOfBuying*house.price;
            double sellPrice = yearlyAppreciation(searchParameters.yearsOfOwnership, searchParameters.homeAppreciation, house.price);
            double sellingFriction=searchParameters.costOfSelling*sellPrice;
            double totalHoa = monthlyToTotal(searchParameters.yearsOfOwnership, house.hoaFees, 0);
            double totalTaxes = yearlyToTotal(searchParameters.yearsOfOwnership, house.yearlyTaxes);
            double costOfMortgage = mortgageCost(house.price);

            double costOfOwnership = totalHoa + totalTaxes + costOfMortgage + buyingFriction + sellingFriction - sellPrice;
            double rentPrice = monthlyToTotal(searchParameters.yearsOfOwnership, searchParameters.monthlyCost, searchParameters.rentIncrease);
            if (costOfOwnership <= rentPrice) {
                passingHouses.add(house);
            }
        }
        return passingHouses;
    }

    private double mortgageCost(double salePrice) {
        return salePrice;

    }

    private double yearlyToTotal(double yearsOfOwnership,double yearlyCost) {
        return yearsOfOwnership*yearlyCost;


    }

    private double monthlyToTotal(double yearsOfOwnership, double monthlyCost, double increaseYearly){
        double totalCost= 0;
        double costPerYear = monthlyCost*12;
        for(int i = 0; i<yearsOfOwnership; i+=1){

            totalCost += yearlyAppreciation(yearsOfOwnership, increaseYearly, costPerYear);

        }
        return totalCost;
    }
    private double yearlyAppreciation(double yearsOfownership, double appreciationRatio, double principal) {
        double appreciationTotal = principal*Math.pow(1+appreciationRatio, yearsOfownership);
        return appreciationTotal;
    }

    private double financeCost(double lumpSum, double mortgagePeriod) {
        return lumpSum / (mortgagePeriod * 12);
    }

    private double yearlyToMonthly(double yearlyCost) {
        return yearlyCost / 12;
    }
}
