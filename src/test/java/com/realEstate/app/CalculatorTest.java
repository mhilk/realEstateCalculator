package com.realEstate.app;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class CalculatorTest {
    @Test
    public void shouldReturnEmptyListWhenGivenEmptyList() throws Throwable {
        Calculator calculator = new Calculator(new SearchParametersBuilder().create());
        List<House> houses = calculator.filterHouses(new ArrayList<House>());
        assertEquals(0, houses.size());

    }


    @Test
    public void shouldAllowFreeHouse() {
        SearchParameters searchParameters = new SearchParametersBuilder().wRentalCost(1000).create();
        Calculator calculator = new Calculator(searchParameters);
        ArrayList<House> inputHouses = new ArrayList<House>();
        House house = new House(0, 0, 0);
        inputHouses.add(house);
        List<House> houses = calculator.filterHouses(inputHouses);
        assertEquals(1, houses.size());
        assertEquals(house, houses.get(0));
    }

    @Test
    public void shouldNotAllowHighTaxHouse() {
        SearchParameters searchParameters = new SearchParametersBuilder().wRentalCost(1000).wTimeHorizon(1).create();
        Calculator calculator = new Calculator(searchParameters);
        ArrayList<House> inputHouses = new ArrayList<House>();
        House house = new House(0, 0, 12001);
        inputHouses.add(house);
        List<House> houses = calculator.filterHouses(inputHouses);
        assertEquals(0, houses.size());


    }

    @Test
    public void shouldNotAllowHouseWithHighHoa() {
        SearchParameters searchParameters = new SearchParametersBuilder().wRentalCost(1000).wTimeHorizon(1).create();
        Calculator calculator = new Calculator(searchParameters);
        ArrayList<House> inputHouses = new ArrayList<House>();
        House house = new House(0, 1001, 0);
        inputHouses.add(house);
        List<House> houses = calculator.filterHouses(inputHouses);
        assertEquals(0, houses.size());

    }

    @Test
    public void shouldNotAllowHighBuyingCostHouse() {
        SearchParameters searchParameters = new SearchParametersBuilder().wRentalCost(1000)
                                                                         .wBuyingFriction(.1)
                                                                         .create();
        Calculator calculator = new Calculator(searchParameters);
        ArrayList<House> inputHouses = new ArrayList<House>();
        House house = new House(120001, 0, 0);
        inputHouses.add(house);
        List<House> houses = calculator.filterHouses(inputHouses);
        assertEquals(0, houses.size());
    }

    @Test
    public void shouldPassHouseThatsCheaperThanRentIncrease() {
        SearchParameters searchParameters = new SearchParametersBuilder().wRentalCost(1000).wTimeHorizon(5).wRentIncreaseRatio(.01).create();
        Calculator calculator = new Calculator(searchParameters);
        ArrayList<House> inputHouses = new ArrayList<House>();
        House house = new House(0, 1001, 0);
        inputHouses.add(house);
        List<House> houses = calculator.filterHouses(inputHouses);
        assertEquals(1, houses.size());
    }

    @Test
    public void shouldNotPassHouseThatsMoreExpensiveThanRentIncrease() {
        SearchParameters searchParameters = new SearchParametersBuilder().wRentalCost(1000).wTimeHorizon(5).wRentIncreaseRatio(.01).create();
        Calculator calculator = new Calculator(searchParameters);
        ArrayList<House> inputHouses = new ArrayList<House>();
        House house = new House(0, 1060, 0);
        inputHouses.add(house);
        List<House> houses = calculator.filterHouses(inputHouses);
        assertEquals(0, houses.size());

    }

    @Test
    public void shouldAllowHouseThatsAppreciated() {
        SearchParameters searchParameters = new SearchParametersBuilder().wRentalCost(1000).wTimeHorizon(5).wHomeAppreciatonRatio(1).wBuyingFriction(1).create();
        Calculator calculator = new Calculator(searchParameters);
        ArrayList<House> inputHouses = new ArrayList<House>();
        House house = new House(60001,0, 0);
        inputHouses.add(house);
        List<House> houses = calculator.filterHouses(inputHouses);
        assertEquals(1, houses.size());
    }

    @Test
    public void shouldNotAllowHouseThatHasNotAppreciatedEnough() {
        SearchParameters searchParameters = new SearchParametersBuilder().wRentalCost(4999.0/12).wTimeHorizon(1).wHomeAppreciatonRatio(.5).wBuyingFriction(1).create();
        Calculator calculator = new Calculator(searchParameters);
        ArrayList<House> inputHouses = new ArrayList<House>();
        House house = new House(10000,0, 0);
        inputHouses.add(house);
        List<House> houses = calculator.filterHouses(inputHouses);
        assertEquals(0, houses.size());

    }

    public static class SearchParametersBuilder {

        private double rentalCost;
        private double timeHorizon;
        private double buyingFriction;
        private double sellingFriction;
        private double mortgageLength;
        private double rentIncreaseRatio;
        private double homeAppreciatonRatio;
        private double alternativeInvestmentGrowth;

        public SearchParametersBuilder(){
            timeHorizon = 1;
        }
        public SearchParametersBuilder wHomeAppreciatonRatio(double homeAppreciatonRatio) {
            this.homeAppreciatonRatio = homeAppreciatonRatio;
            return this;
        }
        public SearchParametersBuilder wRentalCost(double rentalCost) {
            this.rentalCost = rentalCost;
            return this;
        }

        public SearchParametersBuilder wBuyingFriction(double buyingFrictionRatio) {
            this.buyingFriction = buyingFrictionRatio;
            return this;
        }
        public SearchParametersBuilder wSellingFriction(double sellingFrictionRatio){
            this.sellingFriction = sellingFrictionRatio;
            return this;
        }
        public SearchParametersBuilder wTimeHorizon(double timeHorizon){
            this.timeHorizon = timeHorizon;
            return this;
        }
        public SearchParametersBuilder wRentIncreaseRatio(double rentIncreaseRatio){
            this.rentIncreaseRatio = rentIncreaseRatio;
            return this;
        }

        public SearchParameters create() {
            return new SearchParameters(rentalCost,
                                        timeHorizon,
                                        buyingFriction,
                                        sellingFriction,
                                        mortgageLength,
                                        rentIncreaseRatio,
                                        homeAppreciatonRatio,
                                        alternativeInvestmentGrowth);
        }

    }
}
