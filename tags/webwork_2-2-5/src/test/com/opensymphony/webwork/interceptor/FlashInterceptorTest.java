/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.interceptor;

import java.util.LinkedHashMap;
import java.util.Map;

import com.opensymphony.webwork.WebWorkTestCase;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionProxy;
import com.opensymphony.xwork.ActionProxyFactory;
import com.opensymphony.xwork.DefaultTextProvider;
import com.opensymphony.xwork.config.Configuration;
import com.opensymphony.xwork.config.ConfigurationException;
import com.opensymphony.xwork.config.ConfigurationManager;
import com.opensymphony.xwork.config.ConfigurationProvider;
import com.opensymphony.xwork.config.entities.ActionConfig;
import com.opensymphony.xwork.config.entities.InterceptorMapping;
import com.opensymphony.xwork.config.entities.PackageConfig;
import com.opensymphony.xwork.config.entities.ResultConfig;
import com.opensymphony.xwork.config.impl.DefaultConfiguration;
import com.opensymphony.xwork.util.OgnlValueStack;

/**
 * @author tmjee
 * @version $Date$ $Id$
 */
public class FlashInterceptorTest extends WebWorkTestCase {

	public void testRetrieveActionOk() throws Exception {
		FlashInterceptor flashInterceptor = new FlashInterceptor();
		flashInterceptor.setOperation(FlashInterceptor.RETRIEVE);
		
		Action action = new InternalAction2();
		final OgnlValueStack stack = new OgnlValueStack();
		final Map sessionMap = new LinkedHashMap();
		sessionMap.put(FlashInterceptor.DEFAULT_KEY, action);
		
		ResultConfig resultConfig = new ResultConfig();
		resultConfig.setName("success");
		resultConfig.setClassName("com.opensymphony.xwork.mock.MockResult");
		
		ActionConfig actionConfig = new ActionConfig();
		actionConfig.setPackageName("myPackage");
		actionConfig.setClassName("com.opensymphony.webwork.interceptor.FlashInterceptorTest$InternalAction");
		actionConfig.addInterceptor(new InterceptorMapping("flash", flashInterceptor));
		actionConfig.addResultConfig(resultConfig);
		
		final PackageConfig packageConfig = new PackageConfig();
		packageConfig.setName("myPackage");
		packageConfig.setNamespace("/namespace");
		packageConfig.addActionConfig("action", actionConfig);
		
		
		DefaultConfiguration configuration = new DefaultConfiguration();
		ConfigurationManager.setConfiguration(configuration);
		ConfigurationManager.addConfigurationProvider(new ConfigurationProvider() {
			public void destroy() {}
			public void init(Configuration configuration) throws ConfigurationException {
				configuration.addPackageConfig("myPackage", packageConfig);
			}
			public boolean needsReload() {
				return false;
			}
		});
		configuration.reload();
		
		ActionProxy actionProxy = ActionProxyFactory.getFactory().createActionProxy("/namespace", "action", 
				new LinkedHashMap() {
					private static final long serialVersionUID = 2184375524248962956L;
					{put(ActionContext.SESSION, sessionMap);
					  put(ActionContext.VALUE_STACK, stack);}
				}, true, true);
		
		actionProxy.execute();
		
		// 1] our flash action
		// 2] our current action
		// 3] DefaultTextProvider (pushed in by default to provide i18n msgs)
		assertEquals(stack.size(), 3); 
		assertEquals(stack.pop().getClass(), InternalAction2.class);
		assertEquals(stack.pop().getClass(), InternalAction.class);
		assertEquals(stack.pop().getClass(), DefaultTextProvider.class);
		
		// make sure we clean up the session after pushing it to the stack
		assertEquals(sessionMap.size(), 0);
		assertFalse(sessionMap.containsKey(FlashInterceptor.DEFAULT_KEY));
	}
	
	public void testSaveActionOk() throws Exception {
		FlashInterceptor flashInterceptor = new FlashInterceptor();
		flashInterceptor.setOperation(FlashInterceptor.STORE);
		final Map sessionMap = new LinkedHashMap();
		
		ResultConfig resultConfig = new ResultConfig();
		resultConfig.setName("success");
		resultConfig.setClassName("com.opensymphony.xwork.mock.MockResult");
		
		ActionConfig actionConfig = new ActionConfig();
		actionConfig.setPackageName("myPackage");
		actionConfig.setClassName("com.opensymphony.webwork.interceptor.FlashInterceptorTest$InternalAction");
		actionConfig.addInterceptor(new InterceptorMapping("flash", flashInterceptor));
		actionConfig.addResultConfig(resultConfig);
		
		final PackageConfig packageConfig = new PackageConfig();
		packageConfig.setName("myPackage");
		packageConfig.setNamespace("/namespace");
		packageConfig.addActionConfig("action", actionConfig);
		
		
		DefaultConfiguration configuration = new DefaultConfiguration();
		ConfigurationManager.setConfiguration(configuration);
		ConfigurationManager.addConfigurationProvider(new ConfigurationProvider() {
			public void destroy() {}
			public void init(Configuration configuration) throws ConfigurationException {
				configuration.addPackageConfig("myPackage", packageConfig);
			}
			public boolean needsReload() {
				return false;
			}
		});
		configuration.reload();
		
		ActionProxy actionProxy = ActionProxyFactory.getFactory().createActionProxy("/namespace", "action", 
				new LinkedHashMap() {
					private static final long serialVersionUID = 2184375524248962956L;
					{put(ActionContext.SESSION, sessionMap);}
				}, true, true);
		
		actionProxy.execute();
		
		
		assertEquals(sessionMap.size(), 1);
		assertTrue(sessionMap.containsKey(FlashInterceptor.DEFAULT_KEY));
		assertEquals(sessionMap.get(FlashInterceptor.DEFAULT_KEY).getClass(), InternalAction.class);
	}
	
	
	public static class InternalAction implements Action {
		public String execute() throws Exception {
			return SUCCESS;
		}
	}
	
	public static class InternalAction2 implements Action {
		public String execute() throws Exception {
			return SUCCESS;
		}
	}
}
