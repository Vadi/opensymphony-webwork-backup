/*
 * Copyright (c) 2004 Manning Publications. All Rights Reserved.
 */
package org.hibernate.auction.localization;

import java.util.Map;
import java.util.Locale;
import java.util.HashMap;
import java.util.Collections;

/**
 * The supported Locales for this application
 *
 * @author Jason Carreira <jason@zenfrog.com>
 */
public class Locales {

    private static Map locales;
    public static final Locale ES = new Locale("es","ES");

    static {
        Locales.locales = new HashMap();
        Locales.locales.put("english",Locale.US);
        Locales.locales.put("spanish", ES);
        Locales.locales.put("french", Locale.FRANCE);
        Locales.locales.put("german",Locale.GERMANY);
    }

    public static Map locales() {
        return Collections.unmodifiableMap(locales);
    }

    public Locales() {
    }

    public Map getLocales() {
        return locales();
    }
}
