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

public class TescovinyCrawler implements IPriceCheckable, ImageCrawable {

    private static final String CRAWL_URL = "https://www.google.cz/search?q=tescoviny+id+";

    public double getPrice(String EAN) throws IOException {
        double price = 0;

        String tescovinyUrl = getCrawlingUrl(EAN);
        if(tescovinyUrl != null){
            Document doc = Jsoup.connect(tescovinyUrl).get();

            Elements eanElement = doc.select("div.detail");

            if(eanElement != null){
                String text = eanElement.text();
                if(text.contains(EAN)){
                    Elements priceElements = doc.select("td span.price-before");

                    if(priceElements != null && priceElements.size() > 0){
                        Element priceElement = priceElements.first();
                        String priceStr = priceElement.text().split(" ")[0];

                        NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
                        Number number = null;
                        try {
                            number = format.parse(priceStr);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        price = number.doubleValue();
                    }
                }
            }

        }

        return price;
    }

    private String getCrawlingUrl(final String EAN) throws IOException {
        String link = null;
        Document doc = Jsoup.connect(CRAWL_URL + EAN).get();
        Elements priceElements = doc.select("h3.r a");
        if(priceElements != null && priceElements.size() > 0){
            Element priceElement = priceElements.first();
            link = priceElement.attr("abs:href");
        }
        return link;
    }

    public Object getImage(String EAN) {
        return null;
    }

    public String toString(){
        return "Tesco";
    }
}
