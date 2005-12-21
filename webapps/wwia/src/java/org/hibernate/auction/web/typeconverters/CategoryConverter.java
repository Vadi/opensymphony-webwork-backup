/*
 * Copyright (c) 2005 Manning Publications. All Rights Reserved.
 */
package org.hibernate.auction.web.typeconverters;

import org.hibernate.auction.dao.CategoryDAO;
import org.hibernate.auction.dao.CategoryDAOAware;
import org.hibernate.auction.model.Category;

import java.lang.reflect.Member;
import java.util.Map;

/**
 * UserConverter
 *
 * @author Jason Carreira <jcarreira@eplus.com>
 */
public class CategoryConverter extends AbstractEntityConverter {
    public Object convertValue(Map map, Object value, Class toClass) {
        return convertValue(map,null,null,null,value,toClass);
    }

    public Object convertValue(Map map, Object target, Member member, String propertyName, Object value, Class toClass) {
        if (toClass.getName().equals(Category.class.getName())) {
            if (value instanceof Category) {
                return value;
            }
            CategoryDAO dao = (CategoryDAO) getComponent(CategoryDAOAware.class);
            Long id = getLongId(value);
            if (id != null) {
                return  dao.getCategoryById(id,false);
            }
        } else if (toClass.getName().equals(String.class.getName())) {
            if (value instanceof String) {
                return value;
            }
            if (value instanceof Category) {
                return ((Category)value).getId().toString();
            }
        }
        return null;
    }

}
