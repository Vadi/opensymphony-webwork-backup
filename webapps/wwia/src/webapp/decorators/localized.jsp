<%--<%@ page contentType="text/html; charset=UTF-8"%>--%>
<%@ taglib uri="sitemesh-decorator" prefix="decorator" %>
<%@ taglib uri="webwork" prefix="ww" %>
<html>
  <head>
    <title><decorator:title/></title>
    <META HTTP-EQUIV="content-type" CONTENT="text/html; charset=UTF-8">
    <link rel="StyleSheet" href="/shared/styles/main.css"/>
    <link rel="StyleSheet" href="/dtree.css" type="text/css" />
    <script type="text/javascript" src="/dtree.js"></script>
    <decorator:head/>
  </head>

  <body>
    <table class="maintable" cellpadding="10" cellspacing="10"  >
      <tr>
        <td colspan="2">
          <img src="<ww:url value="/shared/images/logo.png"/> "/>
        </td>
      </tr>
      <tr>
        <td valign="top">
          <div id="nav_items">
            * <a href="<ww:url value="updateProfile!default.action"/>">
              <ww:property value="#session['user'].firstname"/>
              <ww:property value="#session['user'].lastname"/>
              </a><br/>
            <p/>
            <ww:if test="!#request['noNav']">
             <%@ page contentType="text/html; charset=UTF-8"%>
             <ww:action name="categoryTree" executeResult="true"/>
             </ww:if>
            <p/>
            <ww:form theme="simple" action="search">
                <ww:textfield theme="simple" name="query" size="10"/>
                <input type="Submit" value="<ww:text name="text.search"/>"/>
            </ww:form>



          </div>
        </td>
        <td valign="top">
          <decorator:body/>
        </td>
      </tr>
    </table>
    <div align="center">
        <ww:bean id="locales" name="org.hibernate.auction.localization.Locales"/>
        <form action="<ww:url includeParams="get" encode="true"/>" method="POST">
        <table width="100%" bgcolor="#8888BB" >
            <tr><td>
            <ww:radio name="request_locale" list="#locales.locales" listKey="value" listValue="key" theme="simple" value="(#session['webwork_locale'] == null) ? locale : #session['webwork_locale']"/>
            </td>
            <td align="right">
            <input type="Submit" value="<ww:text name="text.setLanguage"/>"/>
            </td>
            </tr>
        </table>
        </form>
    </div>
  </body>
</html>