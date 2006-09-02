/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.views.jsp.AbstractUITagTest;

/**
 * 
 * @author tm_jee
 * @version $Date$ $Id$
 */
public class OptGroupTest extends AbstractUITagTest {
	
	
	public void testOptGroupSimple() throws Exception {
		SelectTag selectTag = new SelectTag();
		selectTag.setName("mySelection");
		selectTag.setLabel("My Selection");
		selectTag.setList("%{#{'ONE':'one','TWO':'two','THREE':'three'}}");
		
		OptGroupTag optGroupTag1 = new OptGroupTag();
		optGroupTag1.setLabel("My Label 1");
		optGroupTag1.setList("%{#{'AAA':'aaa','BBB':'bbb','CCC':'ccc'}}");
		
		OptGroupTag optGroupTag2 = new OptGroupTag();
		optGroupTag2.setLabel("My Label 2");
		optGroupTag2.setList("%{#{'DDD':'ddd','EEE':'eee','FFF':'fff'}}");
		
		selectTag.setPageContext(pageContext);
		selectTag.doStartTag();
		optGroupTag1.setPageContext(pageContext);
		optGroupTag1.doStartTag();
		optGroupTag1.doEndTag();
		optGroupTag2.setPageContext(pageContext);
		optGroupTag2.doStartTag();
		optGroupTag2.doEndTag();
		selectTag.doEndTag();
		
		
		//System.out.println(writer.toString());
		verify(SelectTag.class.getResource("OptGroup-1.txt"));
	}
	
	
	public void testOptGroupWithSingleSelect() throws Exception {
		
		SelectTag selectTag = new SelectTag();
		selectTag.setName("mySelection");
		selectTag.setLabel("My Selection");
		selectTag.setList("%{#{'ONE':'one','TWO':'two','THREE':'three'}}");
		selectTag.setValue("%{'EEE'}");
		
		OptGroupTag optGroupTag1 = new OptGroupTag();
		optGroupTag1.setLabel("My Label 1");
		optGroupTag1.setList("%{#{'AAA':'aaa','BBB':'bbb','CCC':'ccc'}}");
		
		OptGroupTag optGroupTag2 = new OptGroupTag();
		optGroupTag2.setLabel("My Label 2");
		optGroupTag2.setList("%{#{'DDD':'ddd','EEE':'eee','FFF':'fff'}}");
		
		selectTag.setPageContext(pageContext);
		selectTag.doStartTag();
		optGroupTag1.setPageContext(pageContext);
		optGroupTag1.doStartTag();
		optGroupTag1.doEndTag();
		optGroupTag2.setPageContext(pageContext);
		optGroupTag2.doStartTag();
		optGroupTag2.doEndTag();
		selectTag.doEndTag();
		
		
		//System.out.println(writer.toString());
		verify(SelectTag.class.getResource("OptGroup-2.txt"));
	}
	
	
	public void testOptGroupWithMultipleSelect() throws Exception {
		SelectTag selectTag = new SelectTag();
		selectTag.setMultiple("true");
		selectTag.setName("mySelection");
		selectTag.setLabel("My Selection");
		selectTag.setList("%{#{'ONE':'one','TWO':'two','THREE':'three'}}");
		selectTag.setValue("%{{'EEE','BBB','TWO'}}");
		
		OptGroupTag optGroupTag1 = new OptGroupTag();
		optGroupTag1.setLabel("My Label 1");
		optGroupTag1.setList("%{#{'AAA':'aaa','BBB':'bbb','CCC':'ccc'}}");
		
		OptGroupTag optGroupTag2 = new OptGroupTag();
		optGroupTag2.setLabel("My Label 2");
		optGroupTag2.setList("%{#{'DDD':'ddd','EEE':'eee','FFF':'fff'}}");
		
		selectTag.setPageContext(pageContext);
		selectTag.doStartTag();
		optGroupTag1.setPageContext(pageContext);
		optGroupTag1.doStartTag();
		optGroupTag1.doEndTag();
		optGroupTag2.setPageContext(pageContext);
		optGroupTag2.doStartTag();
		optGroupTag2.doEndTag();
		selectTag.doEndTag();
		
		
		//System.out.println(writer.toString());
		verify(SelectTag.class.getResource("OptGroup-3.txt"));
	}
}
