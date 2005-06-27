package com.opensymphony.webwork.webFlow;

import com.opensymphony.util.FileUtils;
import com.opensymphony.webwork.WebWorkTestCase;

import java.io.File;
import java.io.StringWriter;

/**
 * User: plightbo
 * Date: Jun 25, 2005
 * Time: 4:18:28 PM
 */
public class WebFlowTest extends WebWorkTestCase {
    public void testWebFlow() {
        String dir = "src/test/com/opensymphony/webwork/webFlow";
        WebFlow webFlow = new WebFlow(dir, dir, dir, "/");
        StringWriter writer = new StringWriter();
        webFlow.setWriter(writer);
        webFlow.prepare();

        String out = "src/test/com/opensymphony/webwork/webFlow/out.txt";
        assertEquals(FileUtils.readFile(new File(out)), writer.toString());
    }
}
