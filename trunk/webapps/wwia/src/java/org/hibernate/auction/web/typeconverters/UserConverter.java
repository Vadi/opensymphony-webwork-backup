/*
 * Copyright (c) 2004 Manning Publications. All Rights Reserved.
 */
package org.hibernate.auction.web.typeconverters;

import org.hibernate.auction.dao.UserDAO;
import org.hibernate.auction.dao.UserDAOAware;
import org.hibernate.auction.model.User;

import java.lang.reflect.Member;
import java.util.Map;

/**
 * UserConverter
 *
 * @author Jason Carreira <jcarreira@eplus.com>
 */
public class UserConverter extends AbstractEntityConverter {
    public Object convertValue(Map map, Object value, Class toClass) {
        return convertValue(map,null,null,null,value,toClass);
    }

    public Object convertValue(Map map, Object target, Member member, String propertyName, Object value, Class toClass) {
        if (toClass.getName().equals(User.class.getName())) {
            if (value instanceof User) {
                return value;
            }
            UserDAO dao = (UserDAO) getComponent(UserDAOAware.class);
            Long id = getLongId(value);
            if (id != null) {
                return  dao.getUserById(id,false);
            }
        } else if (toClass.getName().equals(String.class.getName())) {
            if (value instanceof String) {
                return value;
            }
            if (value instanceof User) {
                return ((User)value).getId().toString();
            }
        }
        return null;
    }

}
