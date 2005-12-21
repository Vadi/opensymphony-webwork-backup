package org.hibernate.auction.web.typeconverters;

import com.opensymphony.xwork.ActionContext;
import ognl.DefaultTypeConverter;
import org.hibernate.auction.model.MonetaryAmount;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;
import java.util.Map;

/**
 * User: plightbo
 * Date: Oct 31, 2004
 * Time: 5:52:56 PM
 */
public class MonetaryAmountConverter extends DefaultTypeConverter {
    public Object convertValue(Map ctx, Object o, Class toClass) {
        if (toClass.equals(String.class)) {
            MonetaryAmount monetaryAmount = (MonetaryAmount) o;
            return monetaryAmount.getCurrency().getSymbol() + monetaryAmount.getValue();
        } else {
            String value = null;
            if (o instanceof String[]) {
                value = ((String[]) o)[0];
            } else {
                value = (String) o;
            }

            Locale locale = (Locale) ctx.get(ActionContext.LOCALE);
            Currency currency = null;
            try {
                currency = Currency.getInstance(locale);
            } catch (Exception e) {
                // this happens sometimes, especially when the country isn't set
                currency = Currency.getInstance("USD");
            }

            // ignore currency if it is there
            String symbol = currency.getSymbol();
            int symbolLoc = value.indexOf(symbol);
            if (symbolLoc != -1) {
                value = value.substring(symbolLoc + symbol.length());
            }

            return new MonetaryAmount(new BigDecimal(value), currency);
        }
    }
}
