package crawler;

import java.io.IOException;

public interface IPriceCheckable {

    double getPrice(final String EAN) throws IOException;
}
