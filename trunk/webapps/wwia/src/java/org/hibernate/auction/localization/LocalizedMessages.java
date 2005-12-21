/*
 * Copyright (c) 2004 Manning Publications. All Rights Reserved.
 */
package org.hibernate.auction.localization;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.interceptor.component.ComponentInterceptor;
import com.opensymphony.xwork.interceptor.component.ComponentManager;
import org.hibernate.auction.dao.LocalizedTextDAO;
import org.hibernate.auction.model.LocalizedText;

import java.util.*;

/**
 * LocalizedMessages
 *
 * @author Jason Carreira <jason@zenfrog.com>
 */
public class LocalizedMessages extends ResourceBundle {
    private LocalizedTextDAO getLocalizedTextDao() {
        ComponentManager cm = (ComponentManager) ActionContext.getContext().get(ComponentInterceptor.COMPONENT_MANAGER);
        return (cm == null) ? null : (LocalizedTextDAO) cm.getComponentInstance(LocalizedTextDAO.class);
    }

    public Enumeration getKeys() {
        Map texts = getTexts();
        if (texts == null) {
            return null;
        }
        return new IteratorEnum(texts.keySet().iterator());
    }

    protected Object handleGetObject(String key) {
        LocalizedTextDAO dao = getLocalizedTextDao();
        if (dao == null)  {
            return null;
        }
        final LocalizedText localizedText = dao.getLocalizedText(getLocaleForTexts(),key);
        return (localizedText == null) ? null : localizedText.getText();
    }

    protected Map getTexts() {
        LocalizedTextDAO dao = getLocalizedTextDao();
        if (dao == null) {
            return null;
        }
        List textList = dao.getTexts(getLocaleForTexts());
        Map texts = new HashMap();
        for (Iterator iterator = textList.iterator(); iterator.hasNext();) {
            LocalizedText text = (LocalizedText) iterator.next();
            texts.put(text.getKey(),text.getText());
        }
        return texts;
    }

    protected Locale getLocaleForTexts() {
        return null;
    }
}
