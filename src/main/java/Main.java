import crawler.IPriceCheckable;
import crawlerImpl.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private List<IPriceCheckable> priceCheckers;

    public Main() throws IOException {
        initPriceCheckers();
        final String ean = "8594404005096";

        for (IPriceCheckable checker :
                priceCheckers) {
            System.out.println(checker.toString() + ": " +  checker.getPrice(ean));
        }


    }

    private void initPriceCheckers(){
        priceCheckers = new ArrayList<IPriceCheckable>();
        priceCheckers.add(new MakroCrawler());
        priceCheckers.add(new KosikCrawler());
        priceCheckers.add(new GoogleAverageCrawler());
        priceCheckers.add(new GoogleCrawler());
        priceCheckers.add(new KolonialCrawler());
        priceCheckers.add(new TescovinyCrawler());
    }



    public static void main(String[] args) throws IOException {
        new Main();
    }
}
