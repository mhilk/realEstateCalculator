package com.realEstate.app;

import org.webbitserver.HttpControl;
import org.webbitserver.HttpHandler;
import org.webbitserver.HttpRequest;
import org.webbitserver.HttpResponse;
import org.webbitserver.handler.StaticFileHandler;
import org.webbitserver.netty.NettyWebServer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        NettyWebServer nettyWebServer = new NettyWebServer(8888);
        nettyWebServer.add(new StaticFileHandler("web"));
        nettyWebServer.add("/calculate/*", new HttpHandler() {
            @Override
            public void handleHttpRequest(HttpRequest httpRequest,
                                          HttpResponse httpResponse,
                                          HttpControl httpControl) throws Exception {
                String inprice = httpRequest.queryParam("price");
                double rent = Double.parseDouble(inprice);
                //WebScraper scraper = new WebScraper();
                //scraper.findLessThan(rent);

                Calculator calc = new Calculator(new SearchParameters(rent, Double.MAX_VALUE, 0, 0, 10, 0, 0, 0));

                httpResponse.end();
            }
        });

        nettyWebServer.start().get();

        CountDownLatch countDownLatch = new CountDownLatch(1);


        countDownLatch.await();

    }
}
