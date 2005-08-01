package com.opensymphony.webwork.views.jsp.ui.ajax;

import com.opensymphony.webwork.views.jsp.ui.AbstractUITag;
import com.opensymphony.webwork.components.UIBean;
import com.opensymphony.webwork.components.ajax.RemoteUpdateDiv;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A tag that creates a HTML &gt;DIV /&lt; that obtains it's content via a remote XMLHttpRequest call
 * via the dojo framework.
 * <p/>
 * If a "listenTopics" is supplied, it will listen to that topic and refresh it's content when any message
 * is received.
 *
 * @author		Ian Roughley
 * @version		$Id$
 */
public class RemoteUpdateDivTag extends AbstractUITag {

    protected String href;
    protected String updateFreq;
    protected String delay;
    protected String loadingText;
    protected String errorText;
    protected String showErrorTransportText;
    protected String listenTopics;
    protected String afterLoading;

    public UIBean getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new RemoteUpdateDiv(stack, req, res);
    }

    protected void populateParams() {
        super.populateParams();

        RemoteUpdateDiv div = (RemoteUpdateDiv) bean;
        div.setHref(href);
        div.setUpdateFreq(updateFreq);
        div.setDelay(delay);
        div.setLoadingText(loadingText);
        div.setErrorText(errorText);
        div.setShowErrorTransportText(showErrorTransportText);
        div.setListenTopics(listenTopics);
        div.setAfterLoading(afterLoading);
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void setUpdateFreq(String updateFreq) {
        this.updateFreq = updateFreq;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public void setLoadingText(String loadingText) {
        this.loadingText = loadingText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    public void setShowErrorTransportText(String showErrorTransportText) {
        this.showErrorTransportText = showErrorTransportText;
    }

    public void setListenTopics(String listenTopics) {
        this.listenTopics = listenTopics;
    }

    public void setAfterLoading(String afterLoading) {
        this.afterLoading = afterLoading;
    }
}
