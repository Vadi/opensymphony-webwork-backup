package com.opensymphony.webwork.webFlow.model;

import java.io.IOException;
import java.io.Writer;

/**
 * User: plightbo
 * Date: Jun 26, 2005
 * Time: 5:06:03 PM
 */
public interface Render {
    public void render(Writer writer) throws IOException;
}
