package com.opensymphony.webwork.sitegraph;

import com.opensymphony.util.FileUtils;
import com.opensymphony.util.ClassLoaderUtil;
import com.opensymphony.webwork.WebWorkTestCase;

import java.io.File;
import java.io.StringWriter;
import java.io.InputStream;
import java.net.URL;

/**
 * User: plightbo
 * Date: Jun 25, 2005
 * Time: 4:18:28 PM
 */
public class SiteGraphTest extends WebWorkTestCase {
    public void no_testWebFlow() throws Exception {
        // use the classloader rather than relying on the
        // working directory being an assumed value when
        // running the test:  so let's get this class's parent dir
        URL url = ClassLoaderUtil.getResource("com/opensymphony/webwork/sitegraph/xwork.xml", SiteGraphTest.class);

        File file = new File(url.toString().substring(5));
        String dir = file.getParent();

        SiteGraph siteGraph = new SiteGraph(dir, dir, dir, "");
        StringWriter writer = new StringWriter();
        siteGraph.setWriter(writer);
        siteGraph.prepare();

        URL compare = SiteGraphTest.class.getResource("out.txt");
        StringBuffer buffer = new StringBuffer(128);
        InputStream in = compare.openStream();
        byte[] buf = new byte[4096];
        int nbytes;

        while ((nbytes = in.read(buf)) > 0) {
            buffer.append(new String(buf, 0, nbytes));
        }

        in.close();
        assertEquals(buffer.toString().replaceAll("\r\n", "\n"), writer.toString().replaceAll("\r\n", "\n"));
    }
}
