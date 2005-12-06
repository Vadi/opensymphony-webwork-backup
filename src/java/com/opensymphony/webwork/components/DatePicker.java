package com.opensymphony.webwork.components;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * <!-- START SNIPPET: javadoc -->
 * Renders datepicker element.</p>
 *
 * Default implementation uses tigracalendar, which is quite unflexible especially in terms of i18n.
 * Therefore a alternative implementation was added, utilizing jscalendar. To use this, set the template parameter
 * to "datepicker_js.ftl"</p>
 *
 * <b>Important:</b> Be sure to set the id param is you use the jscalendar implementation.</p>
 *
 * Following a reference for the format parameter (copied from jscalendar documentation:
 * <table border=0><tr><td valign=top ></td></tr>
 * <tr><td valign=top ><tt>%a</tt> </td><td valign=top >abbreviated weekday name </td></tr>
 * <tr><td valign=top ><tt>%A</tt> </td><td valign=top >full weekday name </td></tr>
 * <tr><td valign=top ><tt>%b</tt> </td><td valign=top >abbreviated month name </td></tr>
 * <tr><td valign=top ><tt>%B</tt> </td><td valign=top >full month name </td></tr>
 * <tr><td valign=top ><tt>%C</tt> </td><td valign=top >century number </td></tr>
 * <tr><td valign=top ><tt>%d</tt> </td><td valign=top >the day of the month ( 00 .. 31 ) </td></tr>
 * <tr><td valign=top ><tt>%e</tt> </td><td valign=top >the day of the month ( 0 .. 31 ) </td></tr>
 * <tr><td valign=top ><tt>%H</tt> </td><td valign=top >hour ( 00 .. 23 ) </td></tr>
 * <tr><td valign=top ><tt>%I</tt> </td><td valign=top >hour ( 01 .. 12 ) </td></tr>
 * <tr><td valign=top ><tt>%j</tt> </td><td valign=top >day of the year ( 000 .. 366 ) </td></tr>
 * <tr><td valign=top ><tt>%k</tt> </td><td valign=top >hour ( 0 .. 23 ) </td></tr>
 * <tr><td valign=top ><tt>%l</tt> </td><td valign=top >hour ( 1 .. 12 ) </td></tr>
 * <tr><td valign=top ><tt>%m</tt> </td><td valign=top >month ( 01 .. 12 ) </td></tr>
 * <tr><td valign=top ><tt>%M</tt> </td><td valign=top >minute ( 00 .. 59 ) </td></tr>
 * <tr><td valign=top ><tt>%n</tt> </td><td valign=top >a newline character </td></tr>
 * <tr><td valign=top ><tt>%p</tt> </td><td valign=top >``PM'' or ``AM'' </td></tr>
 * <tr><td valign=top ><tt>%P</tt> </td><td valign=top >``pm'' or ``am'' </td></tr>
 * <tr><td valign=top ><tt>%S</tt> </td><td valign=top >second ( 00 .. 59 ) </td></tr>
 * <tr><td valign=top ><tt>%s</tt> </td><td valign=top >number of seconds since Epoch (since Jan 01 1970 00:00:00 UTC) </td></tr>
 * <tr><td valign=top ><tt>%t</tt> </td><td valign=top >a tab character </td></tr>
 * <tr><td valign=top ><tt>%U, %W, %V</tt> </td><td valign=top >the week number</td></tr>
 * <tr><td valign=top ><tt>%u</tt> </td><td valign=top >the day of the week ( 1 .. 7, 1 = MON )</td></tr>
 * <tr><td valign=top ><tt>%w</tt> </td><td valign=top >the day of the week ( 0 .. 6, 0 = SUN )</td></tr>
 * <tr><td valign=top ><tt>%y</tt> </td><td valign=top >year without the century ( 00 .. 99 )</td></tr>
 * <tr><td valign=top ><tt>%Y</tt> </td><td valign=top >year including the century ( ex. 1979 )</td></tr>
 * <tr><td valign=top ><tt>%%</tt> </td><td valign=top >a literal <tt>%</tt> character
 * </td></tr></table><p>
 *
 * <b>Note:</b> The this element only works within &lt;ww:form&gt; tags, not plain HTML form.
 * <!-- END SNIPPET: javadoc -->
 *
 * <p/> <b>Examples</b>
 *
 * <pre>
 * <!-- START SNIPPET: expl1 -->
 * &lt;ww:datepicker name="order.date" /&gt;
 * &lt;ww:datepicker name="invoice.date" template="datepicker_js.ftl" language="de" format="%d.%m.&Y %H:%M" showstime="true" /&gt;
 * <!-- END SNIPPET: expl1 -->
 * </pre>
 * <p/>
 * <!-- START SNIPPET: expldesc2 -->
 * If you use jscalendar, you might want to use one of the standard stylesheets provided with jscalendar. For example,
 * to activate the calendar-blue style, include the following in your stylesheet definition:
 * <!-- END SNIPPET: expldesc2 -->
 * <pre>
 * <!-- START SNIPPET: expl2 -->
 * &lt;link href="&lt;%= request.getContextPath() %&gt;/webwork/jscalendar/calendar-blue.css" rel="stylesheet" type="text/css" media="all"/&gt;
 * <!-- END SNIPPET: expl2 -->
 * </pre>
 *
 * @author Patrick Lightbody
 * @author Rene Gielen
 * @version $Revision$
 * @since 2.2
 *
 * @ww.tag name="datepicker" tld-body-content="JSP" tld-tag-class="com.opensymphony.webwork.views.jsp.ui.DatePickerTag"
 * description="Render datepicker"
  */
public class DatePicker extends TextField {

    final public static String TEMPLATE = "datepicker";

    protected String language;
    protected String format;
    protected String showstime;
    protected String singleclick;

    public DatePicker(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    public void evaluateParams() {
        super.evaluateParams();

        if (language != null) {
            addParameter("language", findString(language));
        } else {
            final Locale locale = (Locale) getStack().getContext().get(ActionContext.LOCALE);
            if (locale != null) {
                addParameter("language", locale.getLanguage());
            }
        }

        if (format != null) {
            addParameter("format", findString(format));
        }

        if (showstime != null) {
            addParameter("showstime", findString(showstime));
        }

        if (singleclick != null) {
            addParameter("singleclick", findValue(singleclick, Boolean.class));
        }

    }

    /**
     * @ww.tagattribute required="false" type="String" default="The language of the current Locale"
     * description="The language to use for the widget texts and localization presets. <b>Only valid for jscalendar</b>"
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @ww.tagattribute required="false" type="String" default="Dateformat specified by language preset (%Y/%m/%d for en)"
     * description="The format to use for date field. <b>Only valid for jscalendar</b>"
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * @ww.tagattribute required="false" type="String" default="false"
     * description="Whether time selector is to be shown. Valid values are &quot;true&quot;, &quot;false&quot;, &quot;24&quot; and &quot;12&quot;. <b>Only valid for jscalendar</b>"
     */
    public void setShowstime(String showstime) {
        this.showstime = showstime;
    }

    /**
     * @ww.tagattribute required="false" type="Boolean" default="true"
     * description="Whether to use selected value after single or double click. <b>Only valid for jscalendar</b>"
     */
    public void setSingleclick(String singleclick) {
        this.singleclick = singleclick;
    }

}
