/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.mockobjects.servlet.MockBodyContent;
import com.mockobjects.servlet.MockJspWriter;
import com.opensymphony.webwork.TestConfigurationProvider;
import com.opensymphony.webwork.views.jsp.AbstractUITagTest;
import com.opensymphony.webwork.views.jsp.ParamTag;
import com.opensymphony.webwork.views.jsp.WebWorkMockBodyContent;
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
		tag.setTooltipConfig(
				"#{" +
				"'tooltipIcon':'/webwork/tooltip/myTooltip.gif', " +
				"'tooltipAboveMousePointer':'true', " +
				"'tooltipBgColor':'#ffffff', " +
				"'tooltipBgImg':'/webwork/tooltip/myBgImg.gif', " +
				"'tooltipBorderWidth':'10', " +
				"'tooltipBorderColor':'#eeeeee', " +
				"'tooltipDelay':'2000', " +
				"'tooltipFixCoordinateX':'300', " +
				"'tooltipFixCoordinateY':'300', " +
				"'tooltipFontColor':'#dddddd', " +
				"'tooltipFontFace':'San-Serif,Verdana', " +
				"'tooltipFontSize':'20', "+
				"'tooltipFontWeight':'bold', " +
				"'tooltipLeftOfMousePointer':'true', " +
				"'tooltipOffsetX':'10', " +
				"'tooltipOffsetY':'20', " +
				"'tooltipOpacity':'90', " +
				"'tooltipPadding':'30', " +
				"'tooltipShadowColor':'#cccccc', " +
				"'tooltipShadowWidth':'40', " +
				"'tooltipStatic':'true', " +
				"'tooltipSticky':'true', " +
				"'tooltipStayAppearTime':'3000', " +
				"'tooltipTextAlign':'right', " +
				"'tooltipTitle':'MyTitle', " +
				"'tooltipTitleColor':'#bbbbbb', " +
				"'tooltipWidth':'600' " +
				"}"
		);
		
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
		
		formTag.setTooltipConfig(
				"#{ " +
				"'tooltipIcon':'/webwork/tooltip/formMyTooltip.gif', " +
				"'tooltipAboveMousePointer':'false', " +
				"'tooltipBgColor':'#aaaaaa', " +
				"'tooltipBgImg':'/webwork/tooltip/formMyBgImg.gif', " +
				"'tooltipBorderWidth':'11', " +
				"'tooltipBorderColor':'#bbbbbb', " +
				"'tooltipDelay':'2001', " +
				"'tooltipFixCoordinateX':'301', " +
				"'tooltipFixCoordinateY':'301', " +
				"'tooltipFontColor':'#cccccc', " +
				"'tooltipFontFace':'Verdana,San-Serif', " +
				"'tooltipFontSize':'21', " +
				"'tooltipFontWeight':'normal', " +
				"'tooltipLeftOfMousePointer':'false', " +
				"'tooltipOffsetX':'11', " +
				"'tooltipOffsetY':'21', " +
				"'tooltipOpacity':'91', " +
				"'tooltipPadding':'31', " +
				"'tooltipShadowColor':'#cccccc', " +
				"'tooltipShadowWidth':'41', " +
				"'tooltipStatic':'false', " +
				"'tooltipSticky':'false', " +
				"'tooltipStayAppearTime':'3001', " +
				"'tooltipTextAlign':'left', " +
				"'tooltipTitle':'FormMyTitle', " +
				"'tooltipTitleColor':'#dddddd', " +
				"'tooltipWidth':'601' " +
				"}"
		);
		
		
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
	
	public void testWithPartialFormOverriding() throws Exception {
		
		ConfigurationManager.clearConfigurationProviders();
		ConfigurationManager.addConfigurationProvider(new TestConfigurationProvider());
		
		FormTag formTag = new FormTag();
		formTag.setName("myForm");
		formTag.setPageContext(pageContext);
		formTag.setId("myFormId");
		formTag.setAction("testAction");
		
		formTag.setTooltipConfig(
				"#{ " +
				"'tooltipIcon':'/webwork/tooltip/formMyTooltip.gif', " +
				"'tooltipAboveMousePointer':'false', " +
				"'tooltipBgColor':'#aaaaaa', " +
				"'tooltipBgImg':'/webwork/tooltip/formMyBgImg.gif', " +
				"'tooltipBorderWidth':'11', " +
				"'tooltipBorderColor':'#bbbbbb', " +
				"'tooltipDelay':'2001', " +
				"'tooltipFixCoordinateX':'301', " +
				"'tooltipFixCoordinateY':'301', " +
				"'tooltipFontColor':'#cccccc', " +
				"'tooltipFontFace':'Verdana,San-Serif', " +
				"'tooltipFontSize':'21', " +
				"'tooltipFontWeight':'normal', " +
				"'tooltipLeftOfMousePointer':'false', " +
				"'tooltipOffsetX':'11', " +
				"'tooltipOffsetY':'21', " +
				"'tooltipOpacity':'91', " +
				"'tooltipPadding':'31', " +
				"'tooltipShadowColor':'#cccccc', " +
				"'tooltipShadowWidth':'41', " +
				"'tooltipStatic':'false', " +
				"'tooltipSticky':'false', " +
				"'tooltipStayAppearTime':'3001', " +
				"'tooltipTextAlign':'left', " +
				"'tooltipTitle':'FormMyTitle', " +
				"'tooltipTitleColor':'#dddddd', " +
				"'tooltipWidth':'601' " +
				"}"
		);
		
		
		TextFieldTag tag = new TextFieldTag();
		tag.setPageContext(pageContext);
		tag.setLabel("MyLabel");
		tag.setId("myId");
		
		tag.setTooltip("myTooltip");
		tag.setTooltipConfig(
				"#{ " +
				"'tooltipIcon':'/webwork/tooltip/myTooltip.gif', " +
				"'tooltipAboveMousePointer':'true', " +
				"'tooltipBgColor':'#ffffff', " +
				"'tooltipBgImg':'/webwork/tooltip/myBgImg.gif' " +
				"}"
		);
		
		formTag.doStartTag();
		tag.doStartTag();
		tag.doEndTag();
		formTag.doEndTag();
		
		verify(TooltipTest.class.getResource("tooltip-3.txt"));
	}
	
	
	public void testUsingParamValueToSetConfigurations() throws Exception {
		ConfigurationManager.clearConfigurationProviders();
		ConfigurationManager.addConfigurationProvider(new TestConfigurationProvider());
		
		FormTag formTag = new FormTag();
		formTag.setName("myForm");
		formTag.setPageContext(pageContext);
		formTag.setId("myFormId");
		formTag.setAction("testAction");
		
		
		ParamTag formParamTag = new ParamTag();
		formParamTag.setPageContext(pageContext);
		formParamTag.setName("tooltipConfig");
		formParamTag.setValue(
				"#{ " +
				"'tooltipIcon':'/webwork/tooltip/formMyTooltip.gif', " +
				"'tooltipAboveMousePointer':'false', " +
				"'tooltipBgColor':'#aaaaaa', " +
				"'tooltipBgImg':'/webwork/tooltip/formMyBgImg.gif', " +
				"'tooltipBorderWidth':'11', " +
				"'tooltipBorderColor':'#bbbbbb', " +
				"'tooltipDelay':'2001', " +
				"'tooltipFixCoordinateX':'301', " +
				"'tooltipFixCoordinateY':'301', " +
				"'tooltipFontColor':'#cccccc', " +
				"'tooltipFontFace':'Verdana,San-Serif', " +
				"'tooltipFontSize':'21', " +
				"'tooltipFontWeight':'normal', " +
				"'tooltipLeftOfMousePointer':'false', " +
				"'tooltipOffsetX':'11', " +
				"'tooltipOffsetY':'21', " +
				"'tooltipOpacity':'91', " +
				"'tooltipPadding':'31', " +
				"'tooltipShadowColor':'#cccccc', " +
				"'tooltipShadowWidth':'41', " +
				"'tooltipStatic':'false', " +
				"'tooltipSticky':'false', " +
				"'tooltipStayAppearTime':'3001', " +
				"'tooltipTextAlign':'left', " +
				"'tooltipTitle':'FormMyTitle', " +
				"'tooltipTitleColor':'#dddddd', " +
				"'tooltipWidth':'601' " +
				"}"
		);
		
		
		TextFieldTag tag = new TextFieldTag();
		tag.setPageContext(pageContext);
		tag.setLabel("MyLabel");
		tag.setId("myId");
		tag.setTooltip("myTooltip");
		
		ParamTag textFieldParamTag = new ParamTag();
		textFieldParamTag.setPageContext(pageContext);
		textFieldParamTag.setName("tooltipConfig");
		textFieldParamTag.setValue(
				"#{ " +
				"'tooltipIcon':'/webwork/tooltip/myTooltip.gif', " +
				"'tooltipAboveMousePointer':'true', " +
				"'tooltipBgColor':'#ffffff', " +
				"'tooltipBgImg':'/webwork/tooltip/myBgImg.gif' " +
				"}"
		);
		
		formTag.doStartTag();
		formParamTag.doStartTag();
		formParamTag.doEndTag();
		tag.doStartTag();
		textFieldParamTag.doStartTag();
		textFieldParamTag.doEndTag();
		tag.doEndTag();
		formTag.doEndTag();
		
		verify(TooltipTest.class.getResource("tooltip-3.txt"));
	}
	
	
	public void testUsingParamBodyValueToSetConfigurations() throws Exception {
		ConfigurationManager.clearConfigurationProviders();
		ConfigurationManager.addConfigurationProvider(new TestConfigurationProvider());
		
		FormTag formTag = new FormTag();
		formTag.setName("myForm");
		formTag.setPageContext(pageContext);
		formTag.setId("myFormId");
		formTag.setAction("testAction");
		
		
		ParamTag formParamTag = new ParamTag();
		formParamTag.setPageContext(pageContext);
		formParamTag.setName("tooltipConfig");
		WebWorkMockBodyContent bodyContent = new WebWorkMockBodyContent(new MockJspWriter());
		bodyContent.setString(
				"tooltipIcon = /webwork/tooltip/formMyTooltip.gif | " +
				"tooltipAboveMousePointer = false | " +
				"tooltipBgColor = #aaaaaa| " +
				"tooltipBgImg = /webwork/tooltip/formMyBgImg.gif | " +
				"tooltipBorderWidth = 11 | " +
				"tooltipBorderColor = #bbbbbb| " +
				"tooltipDelay = 2001 | " +
				"tooltipFixCoordinateX = 301| " +
				"tooltipFixCoordinateY = 301| " +
				"tooltipFontColor = #cccccc | " +
				"tooltipFontFace = Verdana,San-Serif | " +
				"tooltipFontSize = 21 | " +
				"tooltipFontWeight = normal | " +
				"tooltipLeftOfMousePointer = false | " +
				"tooltipOffsetX = 11 | " +
				"tooltipOffsetY = 21| " +
				"tooltipOpacity = 91| " +
				"tooltipPadding = 31| " +
				"tooltipShadowColor = #cccccc| " +
				"tooltipShadowWidth = 41| " +
				"tooltipStatic = false | " +
				"tooltipSticky = false| " +
				"tooltipStayAppearTime = 3001| " +
				"tooltipTextAlign = left| " +
				"tooltipTitle = FormMyTitle| " +
				"tooltipTitleColor = #dddddd| " +
				"tooltipWidth = 601 "
		);
		formParamTag.setBodyContent(bodyContent);
		
		TextFieldTag tag = new TextFieldTag();
		tag.setPageContext(pageContext);
		tag.setLabel("MyLabel");
		tag.setId("myId");
		tag.setTooltip("myTooltip");
		
		
		ParamTag textFieldParamTag = new ParamTag();
		textFieldParamTag.setPageContext(pageContext);
		textFieldParamTag.setName("tooltipConfig");
		WebWorkMockBodyContent bodyContent2 = new WebWorkMockBodyContent(new MockJspWriter());
		bodyContent2.setString(
				"tooltipIcon = /webwork/tooltip/myTooltip.gif | " +
				"tooltipAboveMousePointer = true | " +
				"tooltipBgColor = #ffffff | " +
				"tooltipBgImg = /webwork/tooltip/myBgImg.gif " 
		);
		textFieldParamTag.setBodyContent(bodyContent2);
		
		formTag.doStartTag();
		formParamTag.doStartTag();
		formParamTag.doEndTag();
		tag.doStartTag();
		textFieldParamTag.doStartTag();
		textFieldParamTag.doEndTag();
		tag.doEndTag();
		formTag.doEndTag();
		
		verify(TooltipTest.class.getResource("tooltip-3.txt"));
	}
	
}
