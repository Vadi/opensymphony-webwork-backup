<?xml version="1.0" encoding="UTF-8"?>

<!--
  - This is a draft XSLT for xwork.xml files 
  - 
  - @author: Matt Ho <matt@indigoegg.com>
  - @version $Id$
  -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="html"/>

<!--
  -
  -->
<xsl:template match="/">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>XWork Package Description</title>
    <link rel="stylesheet" type="text/css" href="http://www.indigoegg.com/webwork/tag-style.css" />
</head>
<body>
<h2>XWork Package Description</h2>
<h3>Quick Link to Packages</h3>
<ul>
  <xsl:for-each select="xwork/package">
    <li><a><xsl:attribute name="href">#<xsl:value-of select="@name"/></xsl:attribute><xsl:value-of select="@name"/></a></li>
  </xsl:for-each>
</ul>

<xsl:for-each select="xwork">
  <xsl:apply-templates/>
</xsl:for-each>

</body>
</html>
</xsl:template>

<xsl:template match="package">
<div class="bannerrow">
  <a><xsl:attribute name="name"><xsl:value-of select="@name"/></xsl:attribute></a>
  <h4 class="title">
    Package: <xsl:value-of select="@name"/>
    <xsl:if test="@extends">
      <xsl:text> </xsl:text>
      <em>(extends <xsl:value-of select="@extends"/>)</em>
    </xsl:if>
  </h4>
  <xsl:if test="@namespace">
    Namespace: <em><xsl:value-of select="@namespace"/></em><br/>
  </xsl:if>
</div>

<div class="content">
  <xsl:if test="description">
    <div class="subheader">Description</div>
    <div class="content">
      <p><xsl:value-of select="description"/></p>
    </div>
  </xsl:if>

  <xsl:if test="result-types">
    <div class="subheader">Result Types</div>
    <div class="content">
      <table cellpadding="4" cellspacing="2" border="0" width="90%">
      <tr>
        <th><div align="left">Name</div></th>
        <th><div align="left">Class</div></th>
      </tr>
      <xsl:for-each select="result-types/result-type">
        <xsl:sort select="@name" data-type="text" order="ascending"/>
        <xsl:variable name="rownum"><xsl:value-of select="position()"/></xsl:variable>
        <xsl:apply-templates select=".">
          <xsl:with-param name="rownum" select="$rownum"/>
        </xsl:apply-templates>
      </xsl:for-each>
      </table>
    </div>
  </xsl:if>

  <xsl:if test="interceptors">
    <div class="subheader">Interceptors</div>
    <div class="content">
      <table cellpadding="4" cellspacing="2" border="0" width="90%">
      <tr>
        <th><div align="left">Name</div></th>
        <th><div align="left">Class or References</div></th>
      </tr>
      <xsl:for-each select="interceptors/*">
        <xsl:sort select="@name" data-type="text" order="ascending"/>
        <xsl:variable name="rownum"><xsl:value-of select="position()"/></xsl:variable>
        <xsl:apply-templates select=".">
          <xsl:with-param name="rownum" select="$rownum"/>
        </xsl:apply-templates>
      </xsl:for-each>
      </table>
    </div>
  </xsl:if>

  <xsl:if test="global-results">
    <div class="subheader">Global Results</div>
    <div class="content">
      <table cellpadding="4" cellspacing="2" border="0" width="90%">
      <tr>
        <th><div align="left">Name</div></th>
        <th><div align="left">Type</div></th>
        <th><div align="left">Param Name</div></th>
        <th><div align="left">Param Value</div></th>
      </tr>
      <xsl:for-each select="global-results/result">
        <xsl:sort select="@name" data-type="text" order="ascending"/>
        <xsl:variable name="rownum"><xsl:value-of select="position()"/></xsl:variable>
        <xsl:apply-templates select=".">
          <xsl:with-param name="rownum" select="$rownum"/>
        </xsl:apply-templates>
      </xsl:for-each>
      </table>
    </div>
  </xsl:if>

  <xsl:if test="action">
    <xsl:variable name="namespace"><xsl:value-of select="@namespace"/></xsl:variable>
    <div class="subheader">URLs in this package</div>
    <ul>
      <xsl:for-each select="action">
        <xsl:sort select="@name" data-type="text" order="ascending"/>
        <li>
          <a><xsl:attribute name="href">#action_<xsl:value-of select="$namespace"/><xsl:value-of select="@name"/></xsl:attribute>
            /<xsl:value-of select="$namespace"/><strong><xsl:value-of select="@name"/></strong>.action
          </a>
        </li>
      </xsl:for-each>
    </ul>
    
    <xsl:for-each select="action">
      <xsl:sort select="@name" data-type="text" order="ascending"/>
      <xsl:apply-templates select=".">
        <xsl:with-param name="namespace" select="$namespace"/>
      </xsl:apply-templates>
    </xsl:for-each>
  </xsl:if>
</div>

<p/>

</xsl:template>

<!--
  -
  -->
<xsl:template match="result-type">
<xsl:param name="rownum">1</xsl:param>
<tr>
  <td>
    <xsl:if test="$rownum mod 2 = 0">
      <xsl:attribute name="class">altrow</xsl:attribute>
    </xsl:if>
    <xsl:value-of select="@name"/>
  </td>
  <td>
    <xsl:if test="$rownum mod 2 = 0">
      <xsl:attribute name="class">altrow</xsl:attribute>
    </xsl:if>
    <xsl:value-of select="@class"/>
  </td>
</tr>
</xsl:template>


<!--
  -
  -->
<xsl:template match="result">
<xsl:param name="rownum">1</xsl:param>
<tr>
  <td valign="top" nowrap="nowrap">
    <xsl:if test="$rownum mod 2 = 0">
      <xsl:attribute name="class">altrow</xsl:attribute>
    </xsl:if>
    <xsl:value-of select="@name"/>
  </td>
  <td valign="top" nowrap="nowrap">
    <xsl:if test="$rownum mod 2 = 0">
      <xsl:attribute name="class">altrow</xsl:attribute>
    </xsl:if>
    <xsl:value-of select="@type"/>
  </td>
  <td valign="top" nowrap="nowrap">
    <xsl:if test="$rownum mod 2 = 0">
      <xsl:attribute name="class">altrow</xsl:attribute>
    </xsl:if>
    <xsl:for-each select="param">
      <xsl:value-of select="@name"/><br/>
    </xsl:for-each>
  </td>
  <td valign="top" nowrap="nowrap">
    <xsl:if test="$rownum mod 2 = 0">
      <xsl:attribute name="class">altrow</xsl:attribute>
    </xsl:if>
    <xsl:for-each select="param">
      <xsl:value-of select="."/><br/>
    </xsl:for-each>
  </td>
</tr>
</xsl:template>


<!--
  -
  -->
<xsl:template match="interceptor">
<xsl:param name="rownum">1</xsl:param>
<tr>
  <td>
    <xsl:if test="$rownum mod 2 = 0">
      <xsl:attribute name="class">altrow</xsl:attribute>
    </xsl:if>

    <a><xsl:attribute name="name">ref_<xsl:value-of select="@name"/></xsl:attribute></a>
    <xsl:if test="$rownum mod 2 = 0">
      <xsl:attribute name="class">altrow</xsl:attribute>
    </xsl:if>
    <xsl:value-of select="@name"/>
  </td>

  <td>
    <xsl:if test="$rownum mod 2 = 0">
      <xsl:attribute name="class">altrow</xsl:attribute>
    </xsl:if>

    <xsl:value-of select="@class"/>
  </td>
</tr>
</xsl:template>


<!--
  -
  -->
<xsl:template match="interceptor-stack">
<xsl:param name="rownum">1</xsl:param>
<tr>
  <td valign="top">
    <xsl:if test="$rownum mod 2 = 0">
      <xsl:attribute name="class">altrow</xsl:attribute>
    </xsl:if>

    <a><xsl:attribute name="name">ref_<xsl:value-of select="@name"/></xsl:attribute></a>
    <xsl:if test="$rownum mod 2 = 0">
      <xsl:attribute name="class">altrow</xsl:attribute>
    </xsl:if>
    <xsl:value-of select="@name"/>
  </td>

  <td valign="top">
    <xsl:if test="$rownum mod 2 = 0">
      <xsl:attribute name="class">altrow</xsl:attribute>
    </xsl:if>

    <xsl:for-each select="interceptor-ref">
        <xsl:value-of select="position()"/>. 
        <a><xsl:attribute name="href">#ref_<xsl:value-of select="@name"/></xsl:attribute><xsl:value-of select="@name"/></a><br/>
    </xsl:for-each>
  </td>
</tr>
</xsl:template>

<xsl:template match="action">
<xsl:param name="namespace">default/</xsl:param>

    <a><xsl:attribute name="name">action_<xsl:value-of select="$namespace"/><xsl:value-of select="@name"/></xsl:attribute></a>
    <div class="subheader">Action: <xsl:value-of select="@name"/></div>
    <div class="content">
        <table cellpadding="4" cellspacing="2" border="0" width="90%">
          <tr>
            <td valign="top" class="rowtitle">Class:</td>
            <td valign="top"><xsl:value-of select="@class"/></td>
          </tr>
          <tr>
            <td valign="top" class="rowtitle">Interceptor Stack:</td>
            <td valign="top" class="altrow">
              <xsl:for-each select="interceptor-ref">
                <xsl:value-of select="position()"/>. 
                <a><xsl:attribute name="href">#ref_<xsl:value-of select="@name"/></xsl:attribute><xsl:value-of select="@name"/></a><br/>
              </xsl:for-each>
            </td>
          </tr>
          <xsl:if test="result">
          <tr>
            <td valign="top" class="rowtitle">Results:</td>
            <td valign="top" class="altrow">
              <table width="100%">
                <tr>
                  <th><div align="left">Name</div></th>
                  <th><div align="left">Type</div></th>
                  <th><div align="left">Param Name</div></th>
                  <th><div align="left">Param Value</div></th>
                </tr>
                <xsl:for-each select="result">
                  <xsl:variable name="rownum"><xsl:value-of select="position()"/></xsl:variable>
                  <xsl:apply-templates select="."/>
                </xsl:for-each>
              </table>
            </td>
          </tr>
          </xsl:if>
        </table>
    </div>
</xsl:template>

</xsl:stylesheet>


