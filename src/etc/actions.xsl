<?xml version="1.0" encoding="UTF-8"?>

<!--
  - This is a draft XSLT for converting actions.xml files to xwork.xml
  - 
  - @author: Matt Ho <matt@indigoegg.com>
  - @version 0.1
  -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<!--
<xsl:output method="text"/>
-->

<!--
  -
  -->
<xsl:template match="/">
<?xml-stylesheet type="text/xsl" href="http://www.indigoegg.com/webwork/webwork.xsl"?>

<xwork>
  <package name="default">
    <result-types>
      <result-type name="dispatcher" class="com.opensymphony.webwork.dispatcher.ServletDispatcherResult"/>
      <result-type name="redirect" class="com.opensymphony.webwork.dispatcher.ServletRedirectResult"/>
      <result-type name="chain" class="com.opensymphony.xwork.ActionChainResult"/>
      <result-type name="velocity" class="com.opensymphony.xwork.VelocityResult"/>
    </result-types>

    <interceptors>
      <interceptor name="timer" class="com.opensymphony.xwork.interceptor.TimerInterceptor"/>
      <interceptor name="logger" class="com.opensymphony.xwork.interceptor.LoggingInterceptor"/>
      <interceptor name="params" class="com.opensymphony.xwork.interceptor.ParametersInterceptor"/> 
      <interceptor-stack name="defaultStack">
        <interceptor-ref name="timer"/>
        <interceptor-ref name="logger"/>
        <interceptor-ref name="params"/>
      </interceptor-stack>
    </interceptors>

    <xsl:for-each select="actions/action">
      <xsl:variable name="class"><xsl:value-of select="@name"/></xsl:variable>
      <xsl:if test="@alias">
        <xsl:apply-templates select=".">
          <xsl:with-param name="name" select="@alias"/>
        </xsl:apply-templates>
      </xsl:if>
      <xsl:apply-templates select=".">
        <xsl:with-param name="name" select="@name"/>
        <xsl:with-param name="isalias">true</xsl:with-param>
      </xsl:apply-templates>
      <xsl:for-each select="command">
        <xsl:apply-templates select=".">
          <xsl:with-param name="class" select="$class"/>
          <xsl:with-param name="isalias">false</xsl:with-param>
        </xsl:apply-templates>
      </xsl:for-each>
    </xsl:for-each>
  </package>
</xwork>
</xsl:template>

<xsl:template match="action">
<xsl:param name="name">default</xsl:param>
<xsl:param name="isalias">default</xsl:param>
    <action>
      <xsl:attribute name="name"><xsl:value-of select="$name"/></xsl:attribute>
      <xsl:attribute name="class"><xsl:value-of select="@name"/></xsl:attribute>
      <xsl:for-each select="view">
        <xsl:apply-templates select="."/>
      </xsl:for-each>
      <xsl:if test="$isalias = 'false'">
        <xsl:for-each select="command">
          <xsl:apply-templates select=".">
            <xsl:with-param name="name" select="$name"/>
          </xsl:apply-templates>
        </xsl:for-each>
      </xsl:if>
      <interceptor-ref name="defaultStack"/>
    </action>
</xsl:template>

<xsl:template match="view">
  <xsl:choose>
    <xsl:when test="contains(., '.jsp')">
      <result type="dispatcher">
        <xsl:attribute name="name"><xsl:value-of select="@name"/></xsl:attribute>
        <param name="location"><xsl:value-of select="."/></param>
      </result>
    </xsl:when>
    <xsl:when test="contains(., '.vm')">
      <result type="velocity">
        <xsl:attribute name="name"><xsl:value-of select="@name"/></xsl:attribute>
        <param name="location"><xsl:value-of select="."/></param>
      </result>
    </xsl:when>
    <xsl:otherwise>
      <result type="chain">
        <xsl:attribute name="name"><xsl:value-of select="@name"/></xsl:attribute>
        <param name="actionName"><xsl:value-of select="."/></param>
      </result>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="command">
<xsl:param name="class">default</xsl:param>
    <action>
      <xsl:if test="not(@alias)">
        <xsl:attribute name="name"><xsl:value-of select="@name"/></xsl:attribute>
      </xsl:if>
      <xsl:if test="@alias">
        <xsl:attribute name="name"><xsl:value-of select="@alias"/></xsl:attribute>
      </xsl:if>
      <xsl:if test="@method">
        <xsl:attribute name="method"><xsl:value-of select="@method"/></xsl:attribute>
      </xsl:if>
      <xsl:attribute name="class"><xsl:value-of select="$class"/></xsl:attribute>
      <xsl:for-each select="view">
        <xsl:apply-templates select="."/>
      </xsl:for-each>
      <xsl:for-each select="command">
        <xsl:apply-templates select="."/>
      </xsl:for-each>
      <interceptor-ref name="defaultStack"/>
    </action>
</xsl:template>


</xsl:stylesheet>

