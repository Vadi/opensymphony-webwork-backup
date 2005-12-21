/*
 * Copyright (c) 2004 Manning Publications. All Rights Reserved.
 */
package org.hibernate.auction.localization;

import java.util.Locale;

/**
 * LocaleUtil
 *
 * @author Jason Carreira <jason@zenfrog.com>
 */
public class LocaleUtil {
    public static Locale localeFromString(String localeStr) {
        if ((localeStr == null) || (localeStr.trim().length() == 0) || (localeStr.equals("_"))) {
            return null;
        }
        int index = localeStr.indexOf('_');
        if (index < 0) {
            return new Locale(localeStr);
        }
        String language = localeStr.substring(0,index);
        if (index == localeStr.length()) {
            return new Locale(language);
        }
        localeStr = localeStr.substring(index +1);
        index = localeStr.indexOf('_');
        if (index < 0) {
            return new Locale(language,localeStr);
        }
        String country = localeStr.substring(0,index);
        if (index == localeStr.length()) {
            return new Locale(language,country);
        }
        localeStr = localeStr.substring(index +1);
        return new Locale(language,country,localeStr);
    }
}
