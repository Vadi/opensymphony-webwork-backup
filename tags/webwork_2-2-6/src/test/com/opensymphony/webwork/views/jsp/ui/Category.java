package com.opensymphony.webwork.views.jsp.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Used by Tree Component Test. Copied from showcase.
 */
public class Category {
	private static Map catMap = new HashMap();

    static {
        new Category(1, "Root",
        		new Category[] {
                new Category(2, "Java",
                		new Category[] {
                        new Category(3, "Web Frameworks",
                        	    new Category[] {
                                new Category(4, "WebWork"),
                                new Category(5, "Struts Action"),
                                new Category(6, "Struts Shale"),
                                new Category(7, "Stripes"),
                                new Category(8, "Rife")
                                })
                		,
                        new Category(9, "Persistence",
                        		new Category[] {
                                new Category(10, "iBatis"),
                                new Category(11, "Hibernate"),
                                new Category(12, "JDO"),
                                new Category(13, "JDBC")
                        		})
                		}),
                new Category(14, "JavaScript",
                		new Category[] {
                        new Category(15, "Dojo"),
                        new Category(16, "Prototype"),
                        new Category(17, "Scriptaculous"),
                        new Category(18, "OpenRico"),
                        new Category(19, "DWR")
                		})
                });
    }

    public static Category getById(long id) {
        return (Category) catMap.get(new Long(id));
    }

    private long id;
    private String name;
    private List children;
    private boolean toggle;

    public Category(long id, String name) {
    	this(id, name, new Category[0]);
    }
    
    public Category(long id, String name, Category[] children) {
        this.id = id;
        this.name = name;
        this.children = new ArrayList();
        for (int a=0; a< children.length; a++) {
            this.children.add(children[a]);
        }

        catMap.put(new Long(id), this);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List getChildren() {
        return children;
    }

    public void setChildren(List children) {
        this.children = children;
    }

    public void toggle() {
        toggle = !toggle;
    }

    public boolean isToggle() {
        return toggle;
    }
}

