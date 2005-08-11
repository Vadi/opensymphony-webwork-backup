package com.opensymphony.webwork.views.jsp.ui.ajax;

import com.opensymphony.webwork.views.jsp.ui.AbstractUITag;
import com.opensymphony.webwork.components.UIBean;
import com.opensymphony.webwork.components.ajax.RemoteLink;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A tag that creates a HTML &gt;a href='' /&lt; that when clicked calls a URL remote XMLHttpRequest call
 * via the dojo framework.  The result from the URL is executed as JavaScript.
 * <p/>
 * If a "listenTopics" is supplied, it will publish a 'click' message to that topic when the result is
 * returned.  If utilizing the topic/event elements, then this tag needs to be contained within
 * a &gt;ww:topicScope /&lt; tag.
 *
 * @see TopicScopeTag
 *
 * @author		Ian Roughley
 * @version		$Id$
 */
public class HrefTag extends AbstractUITag {


    protected String href;
    protected String errorText;
    protected String showErrorTransportText;
    protected String notifyTopics;
    protected String afterLoading;

    public UIBean getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new RemoteLink(stack, req, res);
    }

    protected void populateParams() {
        super.populateParams();

        RemoteLink link = (RemoteLink) bean;
        link.setHref(href);
        link.setErrorText(errorText);
        link.setShowErrorTransportText(showErrorTransportText);
        link.setNotifyTopics(notifyTopics);
        link.setAfterLoading(afterLoading);
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    public void setShowErrorTransportText(String showErrorTransportText) {
        this.showErrorTransportText = showErrorTransportText;
    }

    public void setNotifyTopics(String notifyTopics) {
        this.notifyTopics = notifyTopics;
    }

     public void setAfterLoading(String afterLoading) {
         this.afterLoading = afterLoading;
     }
}
