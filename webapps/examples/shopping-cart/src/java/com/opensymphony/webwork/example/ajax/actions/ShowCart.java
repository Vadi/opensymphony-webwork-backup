/*
 * Copyright (c) 2005 Your Corporation. All Rights Reserved.
 */
package com.opensymphony.webwork.example.ajax.actions;

import com.opensymphony.xwork.ActionSupport;
import com.opensymphony.webwork.example.ajax.cart.ShoppingCartAware;
import com.opensymphony.webwork.example.ajax.cart.ShoppingCart;

import java.util.Iterator;

/**
 * ShowCart
 *
 * @author Jason Carreira <jcarreira@eplus.com>
 */
public class ShowCart extends ActionSupport implements ShoppingCartAware {
    private ShoppingCart cart;

    public void setShoppingCart(ShoppingCart cart) {
        this.cart = cart;
    }

    public ShoppingCart getCart() {
        return cart;
    }

    public int getNumCartItems() {
        ShoppingCart cart = getCart();
        if ( (cart == null) || (cart.getContents() == null) ) {
            return 0;
        }
        return cart.getContents().size();
    }

    public double getCartTotal() {
        ShoppingCart cart = getCart();
        if ( (cart == null) || (cart.getContents() == null) ) {
            return 0;
        }
        double total = 0.0;
        for (Iterator iterator = cart.getContents().iterator(); iterator.hasNext();) {
            ShoppingCart.CartEntry cartEntry = (ShoppingCart.CartEntry) iterator.next();
            total += ( cartEntry.getQuantity() * cartEntry.getProduct().getPrice() );
        }
        return total;
    }
}
