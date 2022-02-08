package by.epamtc.stanislavmelnikov.entity.currencyholder;

import java.util.HashMap;
import java.util.Map;

public class CurrencyHolder{
    private static Map<String, Double> currencyRates = new HashMap<>();

    public static Map<String, Double> getCurrencyRates() {
        return currencyRates;
    }

    public static void setCurrencyRates(Map<String, Double> currencyRates) {
        CurrencyHolder.currencyRates = currencyRates;
    }
}
