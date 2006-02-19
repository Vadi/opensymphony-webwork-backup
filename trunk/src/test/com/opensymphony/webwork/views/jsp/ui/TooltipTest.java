/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.TestConfigurationProvider;
import com.opensymphony.webwork.views.jsp.AbstractUITagTest;
import com.opensymphony.xwork.config.ConfigurationManager;

/**
 * UI components Tooltip test case.
 * 
 * @author tm_jee
 * @version $Date$ $Id$
 */
public class TooltipTest extends AbstractUITagTest {

	public void testWithoutFormOverriding() throws Exception {
		ConfigurationManager.clearConfigurationProviders();
		ConfigurationManager.addConfigurationProvider(new TestConfigurationProvider());
		
		
		// we test it on textfield component, but since the tooltip are common to 
		// all components, it will be the same for other components as well.
		FormTag formTag = new FormTag();
		formTag.setPageContext(pageContext);
		formTag.setId("myFormId");
		formTag.setAction("testAction");
		formTag.setName("myForm");
		
		
		TextFieldTag tag = new TextFieldTag();
		tag.setPageContext(pageContext);
		tag.setLabel("MyLabel");
		tag.setId("myId");
		
		
		tag.setTooltip("myTooltip");
		tag.setTooltipIcon("/webwork/tooltip/myTooltip.gif");
		tag.setTooltipAboveMousePointer("true");
		tag.setTooltipBgColor("#ffffff");
		tag.setTooltipBgImg("/webwork/tooltip/myBgImg.gif");
		tag.setTooltipBorderWidth("10");
		tag.setTooltipBorderColor("#eeeeee");
		tag.setTooltipDelay("2000");
		tag.setTooltipFixCoordinate("[300, 300]");
		tag.setTooltipFontColor("#dddddd");
		tag.setTooltipFontFace("San-Serif,Verdana");
		tag.setTooltipFontSize("20");
		tag.setTooltipFontWeight("bold");
		tag.setTooltipLeftOfMousePointer("true");
		tag.setTooltipOffsetX("10");
		tag.setTooltipOffsetY("20");
		tag.setTooltipOpacity("90");
		tag.setTooltipPadding("30");
		tag.setTooltipShadowColor("#cccccc");
		tag.setTooltipShadowWidth("40");
		tag.setTooltipStatic("true");
		tag.setTooltipSticky("true");
		tag.setTooltipStayAppearTime("3000");
		tag.setTooltipTextAlign("right");
		tag.setTooltipTitle("MyTitle");
		tag.setTooltipTitleColor("#bbbbbb");
		tag.setTooltipWidth("600");
		
		
		formTag.doStartTag();
		tag.doStartTag();
		tag.doEndTag();
		formTag.doEndTag();
		
		verify(TooltipTest.class.getResource("tooltip-1.txt"));
	}
	
	public void testWithFormOverriding() throws Exception {
		
		ConfigurationManager.clearConfigurationProviders();
		ConfigurationManager.addConfigurationProvider(new TestConfigurationProvider());
		
		FormTag formTag = new FormTag();
		formTag.setPageContext(pageContext);
		formTag.setName("myForm");
		formTag.setId("myFormId");
		formTag.setAction("testAction");
		
		formTag.setTooltipIcon("/webwork/tooltip/formMyTooltip.gif");
		formTag.setTooltipAboveMousePointer("false");
		formTag.setTooltipBgColor("#aaaaaa");
		formTag.setTooltipBgImg("/webwork/tooltip/formMyBgImg.gif");
		formTag.setTooltipBorderWidth("11");
		formTag.setTooltipBorderColor("#bbbbbb");
		formTag.setTooltipDelay("2001");
		formTag.setTooltipFixCoordinate("[301, 301]");
		formTag.setTooltipFontColor("#cccccc");
		formTag.setTooltipFontFace("Verdana,San-Serif");
		formTag.setTooltipFontSize("21");
		formTag.setTooltipFontWeight("normal");
		formTag.setTooltipLeftOfMousePointer("false");
		formTag.setTooltipOffsetX("11");
		formTag.setTooltipOffsetY("21");
		formTag.setTooltipOpacity("91");
		formTag.setTooltipPadding("31");
		formTag.setTooltipShadowColor("#cccccc");
		formTag.setTooltipShadowWidth("41");
		formTag.setTooltipStatic("false");
		formTag.setTooltipSticky("false");
		formTag.setTooltipStayAppearTime("3001");
		formTag.setTooltipTextAlign("left");
		formTag.setTooltipTitle("FormMyTitle");
		formTag.setTooltipTitleColor("#dddddd");
		formTag.setTooltipWidth("601");
		
		
		TextFieldTag tag = new TextFieldTag();
		tag.setPageContext(pageContext);
		tag.setLabel("MyLabel");
		tag.setId("myId");
		
		tag.setTooltip("myTooltip");
		
		formTag.doStartTag();
		tag.doStartTag();
		tag.doEndTag();
		formTag.doEndTag();
		
		verify(TooltipTest.class.getResource("tooltip-2.txt"));
	}
	
	public void testWithFormOverridingSome() throws Exception {
		
		ConfigurationManager.clearConfigurationProviders();
		ConfigurationManager.addConfigurationProvider(new TestConfigurationProvider());
		
		FormTag formTag = new FormTag();
		formTag.setName("myForm");
		formTag.setPageContext(pageContext);
		formTag.setId("myFormId");
		formTag.setAction("testAction");
		
		formTag.setTooltipIcon("/webwork/tooltip/formMyTooltip.gif");
		formTag.setTooltipAboveMousePointer("false");
		formTag.setTooltipBgColor("#aaaaaa");
		formTag.setTooltipBgImg("/webwork/tooltip/formMyBgImg.gif");
		formTag.setTooltipBorderWidth("11");
		formTag.setTooltipBorderColor("#bbbbbb");
		formTag.setTooltipDelay("2001");
		formTag.setTooltipFixCoordinate("[301, 301]");
		formTag.setTooltipFontColor("#cccccc");
		formTag.setTooltipFontFace("Verdana,San-Serif");
		formTag.setTooltipFontSize("21");
		formTag.setTooltipFontWeight("normal");
		formTag.setTooltipLeftOfMousePointer("false");
		formTag.setTooltipOffsetX("11");
		formTag.setTooltipOffsetY("21");
		formTag.setTooltipOpacity("91");
		formTag.setTooltipPadding("31");
		formTag.setTooltipShadowColor("#cccccc");
		formTag.setTooltipShadowWidth("41");
		formTag.setTooltipStatic("false");
		formTag.setTooltipSticky("false");
		formTag.setTooltipStayAppearTime("3001");
		formTag.setTooltipTextAlign("left");
		formTag.setTooltipTitle("FormMyTitle");
		formTag.setTooltipTitleColor("#dddddd");
		formTag.setTooltipWidth("601");
		
		
		TextFieldTag tag = new TextFieldTag();
		tag.setPageContext(pageContext);
		tag.setLabel("MyLabel");
		tag.setId("myId");
		
		tag.setTooltip("myTooltip");
		tag.setTooltipIcon("/webwork/tooltip/myTooltip.gif");
		tag.setTooltipAboveMousePointer("true");
		tag.setTooltipBgColor("#ffffff");
		tag.setTooltipBgImg("/webwork/tooltip/myBgImg.gif");
		
		
		formTag.doStartTag();
		tag.doStartTag();
		tag.doEndTag();
		formTag.doEndTag();
		
		verify(TooltipTest.class.getResource("tooltip-3.txt"));
	}
}
