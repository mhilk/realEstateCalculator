package com.realEstate.app;

public class SearchParameters {
    public final double monthlyCost;
    public final double yearsOfOwnership;
    public final double costOfBuying;
    public final double costOfSelling;
    public final double mortgagePeriod;
    public final double rentIncrease;
    public final double homeAppreciation;
    public final double alternativeInvestmentGrowth;

    public SearchParameters(double monthlyCost,
                            double yearsOfOwnership,
                            double costOfBuying,
                            double costOfSelling,
                            double mortgagePeriod,
                            double rentIncrease,
                            double homeAppreciation,
                            double alternativeInvestmentGrowth) {
        this.monthlyCost = monthlyCost;
        this.yearsOfOwnership = yearsOfOwnership;
        this.costOfBuying = costOfBuying;
        this.costOfSelling = costOfSelling;
        this.mortgagePeriod = mortgagePeriod;
        this.rentIncrease = rentIncrease;
        this.homeAppreciation = homeAppreciation;
        this.alternativeInvestmentGrowth = alternativeInvestmentGrowth;
    }

}
