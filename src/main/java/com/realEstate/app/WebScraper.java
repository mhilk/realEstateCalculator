package com.realEstate.app;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WebScraper {

    String url = "http://www.realtor.com/realestateandhomes-search/60614/beds-1/baths-1/type-single-family-home,condo-townhome-row-home-co-op,multi-family-home,mfd-mobile-home,other/price-na-425000?pgsz=50&ml=1";

    public void scraper() {
        Document doc = Jsoup.parse(url);
        Elements listings = doc.getElementsByClass("listing");
        for (Element listing : listings) {
            findHouse(listing);


        }

    }

    public House findHouse(Element listing) {
        double price = 0;
        Document listingPage = getListingPage(listing);
        Elements pageLi = listingPage.getElementsByTag("li");
        double hoaFees = findValueByText(listingPage, "Assessment / Association Dues $:");
        double yearlyTaxes = findValueInTable(listingPage, "asdf");

        return new House(price, hoaFees, yearlyTaxes);
    }

    public Document getListingPage(Element listing) {
        Elements link = listing.getElementsByTag("a");
        Element listingTag = link.first();
        String listingLink = listingTag.attr("href");
        Document listingPage = Jsoup.parse(listingLink);
        return listingPage;
    }

    public double findValueByText(Document listingPage, String textContains) {
        Elements pageLi = listingPage.getElementsByTag("li");
        for (Element element : pageLi) {
            String liText = element.text();
            if (liText.contains(textContains)) {
                int startSub = liText.indexOf(": ");
                String hoaFeeString = liText.substring(startSub + 2);
                double hoaFeeDouble = Double.parseDouble(hoaFeeString);
                return hoaFeeDouble;
            }
        }
        return 0;
    }

    public double findValueInTable(Document listingPage, String textContains){
        return 0;
    }
}




