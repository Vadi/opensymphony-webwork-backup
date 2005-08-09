package com.opensymphony.webwork;

import org.apache.commons.jci.CompilingClassLoader;
import org.apache.commons.jci.compilers.eclipse.EclipseJavaCompiler;
import org.mortbay.http.SocketListener;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.WebApplicationContext;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * User: plightbo
 * Date: Aug 7, 2005
 * Time: 7:14:01 PM
 */
public class Prototype {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("prototype must be invoked with three argumenets:");
            System.err.println("[contextPath] [webapp] [sources]");
            System.err.println("");
            System.err.println("Ex: java -jar webwork-launcher.jar \\");
            System.err.println("    prototype /sandbox webapps/sandbox/src/webapp webapps/sandbox/src/java");
            return;
        }

        String contextPath = args[0];
        String webapp = args[1];

        if (webapp == null) {
            System.out.println("webapp must be specified as an exploded war");
            return;
        }

        String sources = args[2];
        if (sources == null) {
            System.out.println("-Dsources must be specified as a comma-separated list of Java source paths.");
            return;
        }

        Server server = new Server();
        SocketListener socketListener = new SocketListener();
        socketListener.setPort(8080);
        server.addListener(socketListener);
        try {
            WebApplicationContext ctx = server.addWebApplication("localhost", contextPath, webapp);

            // set up files and urls
            StringTokenizer st = new StringTokenizer(sources, ",");
            ArrayList fileList = new ArrayList();
            while (st.hasMoreTokens()) {
                String token = st.nextToken();
                if (!token.endsWith("/")) {
                    token = token + "/";
                }
                fileList.add(new File(token));
            }
            fileList.add(new File(webapp + "/WEB-INF/classes/"));
            File[] files = (File[]) fileList.toArray(new File[fileList.size()]);

            URL[] urls = new URL[files.length];
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (!file.exists()) {
                    throw new RuntimeException("Dir does not exist!");
                }
                urls[i] = file.getCanonicalFile().toURL();
            }

            // deal with classloader
            ClassLoader parent = Thread.currentThread().getContextClassLoader();

            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                parent = new CompilingClassLoader(parent, file, new EclipseJavaCompiler());
//                Field famField = parent.getClass().getDeclaredField("fam");
//                famField.setAccessible(true);
//                FilesystemAlterationMonitor fam = (FilesystemAlterationMonitor) famField.get(parent);
//                fam.doRun();
//
//                Thread.sleep(1500);
            }
            URLClassLoader url = new MyURLClassLoader(urls, parent);
            ctx.setClassLoader(url);
            Thread.currentThread().setContextClassLoader(url);

            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static class MyURLClassLoader extends URLClassLoader {
        private ClassLoader parent;

        public MyURLClassLoader(URL[] urls, ClassLoader parent) {
            super(urls, parent);
            this.parent = parent;
        }

        public Class loadClass(String name) throws ClassNotFoundException {
            Class aClass = null;

            try {
                aClass = parent.loadClass(name);
                if (aClass != null) {
                    return aClass;
                }
            } catch (ClassNotFoundException e) {
            }

            return super.loadClass(name);
        }

        public URL getResource(String name) {
            URL url = findResource(name);
            if (url == null && parent != null) {
                url = parent.getResource(name);
            }

            return url;
        }
    }
}
