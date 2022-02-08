package by.epamtc.stanislavmelnikov.view.customtags;

import by.epamtc.stanislavmelnikov.entity.currencyholder.CurrencyHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class CurrencyPrinter extends TagSupport {
    private String priceUSD;
    private static final String usdKey = "USD";
    private static final String blrKey = " BLR";
    private static final String usdSymbol = " $";

    public void setPriceUSD(String priceUSD) {
        this.priceUSD = priceUSD;
    }

    public int doStartTag() {
        JspWriter out = pageContext.getOut();
        HttpSession session = pageContext.getSession();
        String language = (String) session.getAttribute("lang");
        try {
            if (language.equals("en")) {
                out.print(priceUSD + usdSymbol);
            }
            if (language.equals("ru")) {
                double rate = CurrencyHolder.getCurrencyRates().get(usdKey);
                int priceRub = (int) (Integer.parseInt(priceUSD) * rate);
                out.print(priceRub + blrKey);
            }
        } catch (IOException e) {
            Logger logger = LogManager.getLogger(CurrencyPrinter.class);
            logger.error(e.getMessage(), e);
        }
        return SKIP_BODY;
    }
}
