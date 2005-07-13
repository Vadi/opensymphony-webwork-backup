<%@ taglib uri="webwork" prefix="ww" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ww:if test="category != null">
    <h3><ww:if test="category.parent != null"><ww:property value="category.parent.name"/> &gt; </ww:if><ww:property value="category.name"/></h3>

    <ww:set name="categoryProducts" value="%{catalog.findProductsByCategory(category)}"/>
    <ww:if test="(#categoryProducts != null) && (#categoryProducts.size > 0)">
        <div>
        <ww:iterator value="#categoryProducts">
            <ww:form id="qty_%{id}" namespace="/catalog/remote" action="updateQuantity" theme="ajax">
            <ww:hidden name="productId" value="%{id}"/>
            <ww:hidden name="formId" value="qty_%{id}"/>
            <div class="product">
                <div class="productDetails">
                <div class="productHeader">
                    <div class="productName"><ww:property value="name"/></div>
                    <div class="productPrice"><ww:text name="format.money" value0="price"/></div>
                </div>
                <div class="productDescription"><ww:property value="description"/></div>
                </div>
                <p class="productQuantity">
                    Quantity:&nbsp;<ww:textfield name="quantity" value="%{cart.getQuantityForProduct(top)}" theme="simple" size="2"/>
                    <ww:submit value="Update" theme="ajax">
                       <ww:param name="notifyTopics">cartUpdated</ww:param>
                   </ww:submit>
                </p>
            </div>
            </ww:form>
        </ww:iterator>
        </div>
    </ww:if>
    <ww:else>
        <b>There are no products in this category</b>
    </ww:else>
</ww:if>
<ww:else>
        <h2>WebWork Ajax Catalog</h2>

        <p>Please choose a category to start shopping.</p>
</ww:else>