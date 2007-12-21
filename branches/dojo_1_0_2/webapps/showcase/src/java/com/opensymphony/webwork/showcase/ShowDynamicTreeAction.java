package com.opensymphony.webwork.showcase;

import com.opensymphony.webwork.showcase.ajax.tree.Category;

import com.opensymphony.xwork.ActionSupport;

// START SNIPPET: treeExampleDynamicJavaShow 

public class ShowDynamicTreeAction extends ActionSupport {
	
	public Category getTreeRootNode() {
		return Category.getById(1);
	}
}

// END SNIPPET: treeExampleDynamicJavaShow

