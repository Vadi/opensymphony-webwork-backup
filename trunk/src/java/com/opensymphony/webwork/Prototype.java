package com.opensymphony.webwork;

import com.opensymphony.webwork.util.classloader.CompilingClassLoader;
import org.mortbay.http.SocketListener;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.WebApplicationContext;
import org.mortbay.util.FileResource;
import org.mortbay.util.JarResource;
import org.mortbay.util.Resource;

import java.io.File;
import java.io.IOException;
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

        try {
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
                    throw new RuntimeException("Source dir does not exist: " + file.toString());
                }
                urls[i] = file.getCanonicalFile().toURL();
            }

            // deal with classloader
            ClassLoader parent = Thread.currentThread().getContextClassLoader();
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                CompilingClassLoader ccl = new CompilingClassLoader(parent, file);
                ccl.start();
                parent = ccl;
            }
            URLClassLoader url = new MyURLClassLoader(urls, parent);
            Thread.currentThread().setContextClassLoader(url);

            // finally, start the server!
            Server server = new Server();
            SocketListener socketListener = new SocketListener();
            socketListener.setPort(8080);
            server.addListener(socketListener);

            WebApplicationContext ctx = new PrototypeWebAppContext(webapp);
            ctx.setContextPath(contextPath);
            ctx.setClassLoader(url);
            server.addContext("localhost", ctx);

            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static class PrototypeWebAppContext extends WebApplicationContext {
        public PrototypeWebAppContext() {
        }

        public PrototypeWebAppContext(String string) {
            super(string);
        }

        public Resource getResource(String string) throws IOException {
            if (string.startsWith("/WEB-INF/lib/")) {
                String jar = string.substring("/WEB-INF/lib/".length());
                ClassLoader parent = Thread.currentThread().getContextClassLoader();
                while (parent != null) {
                    if (parent instanceof URLClassLoader) {
                        URL[] urls = ((URLClassLoader) parent).getURLs();
                        for (int i = 0; i < urls.length; i++) {
                            URL url = urls[i];
                            if (url.toExternalForm().endsWith(jar)) {
                                return JarResource.newResource(url);
                            }
                        }
                    }

                    parent = parent.getParent();
                }
            }

            // still haven't found what we're looking for?
            // Alright, let's just hack this to work in IDEA
            if (string.equals("/webwork")) {
                return FileResource.newResource("src/java/META-INF/taglib.tld");
            }

            return super.getResource(string);
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
