package com.opensymphony.webwork.views.velocity;

import com.opensymphony.util.TextUtils;

import java.net.URLEncoder;

/**
 * A simple bean with some useful utility methods for use in Velocity views.
 *
 * It's added as $tools in the Velocity context.
 */
public class ViewToolsBean
{
    public String urlEncode(String s)
    {
        return URLEncoder.encode(s);
    }

    public String htmlEncode(String s)
    {
        return TextUtils.htmlEncode(s);
    }

    public String textToHtml(String s)
    {
        return TextUtils.plainTextToHtml(s);
    }
}
