package com.opensymphony.webwork.dispatcher.mapper;

/**
 * User: plightbo
 * Date: Jun 20, 2005
 * Time: 6:56:45 PM
 */
public class ActionMapperFactory {
    public static ActionMapper getMapper() {
        return new DefaultActionMapper();
    }
}
