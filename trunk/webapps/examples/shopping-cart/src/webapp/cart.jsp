<%@ taglib uri="webwork" prefix="ww" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<p class="boxTitle">Shopping Cart</p>
<div id="cartTable">
<div><span class="cartLabel">Number of Items:</span><span class="cartValue"><ww:property value="numCartItems"/></span></div>
<div><span class="cartLabel">Total:</span><span class="cartValue"><ww:text name="format.money" value0="cartTotal"/></span></div>
</div>