package com.realEstate.app;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import sun.awt.image.ImageWatched;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class WebScraperTest {

    static final String url = "http://www.realtor.com/realestateandhomes-search/60614/beds-1/baths-1/type-single-family-home,condo-townhome-row-home-co-op,multi-family-home,mfd-mobile-home,other/price-na-425000?pgsz=50&ml=1";

    @Test
    public void shouldFindAnElement() throws IOException {
        File input = new File("redfinSource/redfinSourcePage.html");
        Document doc = Jsoup.parse(input, "UTF-8");

        Element content = doc.getElementById("dojox_grid__View_0");
        Elements links = content.getElementsByClass("dojoxGridRow");
        assertEquals(1, links.size());
    }

    @Test
    public void should() {
        Document doc= Jsoup.parse(url);
        Elements listings = doc.getElementsByClass("listing");
        for (Element listing : listings) {
            findHoaFees(listing);


        }

    }
    public double findHoaFees(Element listing) {
        Elements link = listing.getElementsByTag("a");
        assertEquals(1, link.size());
        Element listingTag = link.first();
        String listingLink = listingTag.attr("href");
        Document listingPage = Jsoup.parse(listingLink);
        Elements pageLi = listingPage.getElementsByTag("li");
        for (Element element : pageLi) {
            String liText = element.text();
            if(liText.contains("Assessment / Association Dues $:")){
                int startSub = liText.indexOf(": ");
                String hoaFeeString = liText.substring(startSub + 2);
                double hoaFeeDouble = Double.parseDouble(hoaFeeString);
                return hoaFeeDouble;


            }

        }
        return 0;
    }

    @Test
    public void shouldFindAfterSpace() {
        String text = "this is a test: qwerty";
        int yeah = text.indexOf(": ");
        String substring = text.substring(yeah+2);
        System.out.println(substring);


    }
}
