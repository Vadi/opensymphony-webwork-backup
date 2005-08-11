package com.opensymphony.webwork.components.template;

import java.util.ArrayList;
import java.util.List;

/**
 * User: plightbo
 * Date: Aug 10, 2005
 * Time: 8:06:51 PM
 */
public class Template implements Cloneable {
    String dir;
    String theme;
    String name;

    public Template(String dir, String theme, String name) {
        this.dir = dir;
        this.theme = theme;
        this.name = name;
    }

    public String getDir() {
        return dir;
    }

    public String getTheme() {
        return theme;
    }

    public String getName() {
        return name;
    }

    public List getPossibleTemplates(TemplateEngine engine) {
        ArrayList list = new ArrayList(3);
        Template template = this;
        String parentTheme;
        list.add(template);
        while ((parentTheme = (String) engine.getThemeProps(template).get("parent")) != null) {
            try {
                template = (Template) template.clone();
                template.theme = parentTheme;
                list.add(template);
            } catch (CloneNotSupportedException e) {
            }
        }

        return list;
    }

    public String toString() {
        return "/" + dir + "/" + theme + "/" + name;
    }

    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
