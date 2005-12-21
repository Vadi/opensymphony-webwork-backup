/*
 * Copyright (c) 2005 Manning Publications. All Rights Reserved.
 */
package org.hibernate.auction.web.typeconverters;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.interceptor.component.ComponentInterceptor;
import com.opensymphony.xwork.interceptor.component.ComponentManager;
import ognl.DefaultTypeConverter;

/**
 * AbstractEntityConverter
 *
 * @author Jason Carreira <jcarreira@eplus.com>
 */
public class AbstractEntityConverter extends DefaultTypeConverter {
    protected Long getLongId(Object value) {
        Long id = null;
        if (value instanceof Number) {
            Number number = (Number) value;
            id = new Long(number.longValue());
        } else if (value instanceof String) {
            id = Long.valueOf((String)value);
        } else if (value instanceof String[]) {
            id = Long.valueOf(((String[])value)[0]);
        }
        return id;
    }

    protected Object getComponent(final Class enablerType) {
        ComponentManager cm = (ComponentManager) ActionContext.getContext().get(ComponentInterceptor.COMPONENT_MANAGER);
         return cm.getComponent(enablerType);
    }
}
