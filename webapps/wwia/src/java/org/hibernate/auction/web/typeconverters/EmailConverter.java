package org.hibernate.auction.web.typeconverters;

import ognl.DefaultTypeConverter;
import org.hibernate.auction.model.Email;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: plightbo
 * Date: Apr 11, 2005
 */
public class EmailConverter extends DefaultTypeConverter {
    public Object convertValue(Map ctx, Object o, Class toType) {
        if (toType == Email.class) {
            String email = ((String[]) o)[0];
            return Email.parse(email);
        } else if (toType == String.class) {
            Email email = (Email) o;
            return email.toString();
        }

        return null;
    }
}
