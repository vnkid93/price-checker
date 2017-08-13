package crawlerImpl;

import crawler.IPriceCheckable;
import crawler.ImageCrawable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class GoogleAverageCrawler implements IPriceCheckable, ImageCrawable {
    private static final String CRAWL_URL = "https://www.google.cz/search?tbm=shop&q=";

    public double getPrice(String EAN) throws IOException {
        double price = 0;
        Document doc = Jsoup.connect(CRAWL_URL + EAN).get();
        Elements priceElements = doc.select("span.price");
        if(priceElements != null && priceElements.size() > 0){
            int count = priceElements.size();
            for (int i = 0; i < count; i++) {
                Element priceElement = priceElements.get(i);
                String priceStr = priceElement.text().split(" ")[0];

                NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
                Number number = null;
                try {
                    number = format.parse(priceStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                price += number.doubleValue();
            }
            price = price/count;
        }
        return ((int)(price*100))/100.;
    }

    public Object getImage(String EAN) {
        return null;
    }

    public String toString(){
        return "Google average";
    }
}
