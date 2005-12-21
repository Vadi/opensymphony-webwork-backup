/*
 * Copyright (c) 2004 Manning Publications. All Rights Reserved.
 */
package org.hibernate.auction.model;

import org.hibernate.auction.localization.LocaleUtil;

import java.io.Serializable;
import java.util.Locale;

/**
 * LocalizedText
 *
 * @author Jason Carreira <jason@zenfrog.com>
 */
public class LocalizedText implements Serializable, Comparable {
    private Long id;
    private String key;
    private Locale locale;
    private String text;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLocaleStr() {
        return (locale != null)? locale.toString() : null;
    }

    public void setLocaleStr(String localeStr) {
        this.locale = LocaleUtil.localeFromString(localeStr);
    }

    public Locale getLocale() {
        return locale;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LocalizedText)) return false;

        final LocalizedText localizedText = (LocalizedText) o;

        if (key != null ? !key.equals(localizedText.key) : localizedText.key != null) return false;
        if (locale != null ? !locale.equals(localizedText.locale) : localizedText.locale != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (key != null ? key.hashCode() : 0);
        result = 29 * result + (locale != null ? locale.hashCode() : 0);
        return result;
    }

    public int compareTo(Object o) {
        final LocalizedText other = ((LocalizedText)o);
        int retVal = key.compareTo(other.key);
        if (retVal == 0) {
            retVal = locale.toString().compareTo(other.locale.toString());
        }
        return retVal;
    }
}
