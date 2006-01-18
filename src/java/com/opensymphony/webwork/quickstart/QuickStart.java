package com.opensymphony.webwork.quickstart;

import com.opensymphony.webwork.util.classloader.CompilingClassLoader;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.mortbay.http.SocketListener;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.WebApplicationContext;
import org.mortbay.util.FileResource;
import org.mortbay.util.JarResource;
import org.mortbay.util.Resource;

import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.MalformedURLException;
import java.util.*;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

/**
 * User: plightbo
 * Date: Aug 7, 2005
 * Time: 7:14:01 PM
 */
public class QuickStart {
    public static void main(String[] args) throws FileNotFoundException, MalformedURLException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (args.length != 3 && args.length != 1) {
            System.err.println("QuickStart must be invoked with three argumenets:");
            System.err.println("[contextPath] [webapp] [sources]");
            System.err.println("");
            System.err.println("Ex: java -jar webwork.jar \\");
            System.err.println("    quickstart /sandbox webapps/sandbox/src/webapp webapps/sandbox/src/java");
            return;
        }

        Configuration c;
        if (args.length == 1) {
            XStream xstream = new XStream(new DomDriver());
            xstream.alias("configuration", Configuration.class);
            xstream.alias("extendsConfig", String.class);
            xstream.alias("port", int.class);
            xstream.alias("context", String.class);
            xstream.alias("dir", String.class);
            xstream.alias("path", String.class);
            xstream.alias("webDir", Mapping.class);
            File config = new File(args[0] + "/quickstart.xml");
            c = (Configuration) xstream.fromXML(new FileReader(config));
            c.resolveDirs(config.getParent());
            c.resolveExtensions(config.getParent(), xstream);
        } else {
            c = new Configuration();
            c.setContext(args[0]);
            c.setPort(new Integer(8080));
            ArrayList webDirs = new ArrayList();
            webDirs.add(new Mapping("/", args[1]));
            c.setWebDirs(webDirs);
            ArrayList sources = new ArrayList();
            sources.add(args[2]);
            c.setSources(sources);
            ArrayList classDirs = new ArrayList();
            classDirs.add(args[1] + "/WEB-INF/classes");
            c.setClassDirs(classDirs);
            ArrayList libs = new ArrayList();
            libs.add("lib");
            c.setLibs(libs);

            c.resolveDirs(new File(".").getParent());
        }

        // validate the configuration
        if (c.validate()) {
            return;
        }

        // explain what is being executed
        System.out.println("Launching Jetty with the following configuration:");
        System.out.println("Jars/Directory of jars:");
        for (Iterator iterator = c.getLibs().iterator(); iterator.hasNext();) {
            String s = (String) iterator.next();
            System.out.println("    " + s);
        }
        System.out.println("Directories of classes:");
        for (Iterator iterator = c.getClassDirs().iterator(); iterator.hasNext();) {
            String s = (String) iterator.next();
            System.out.println("    " + s);
        }
        if (c.getSources() != null) {
            System.out.println("Sources:");
            for (Iterator iterator = c.getSources().iterator(); iterator.hasNext();) {
                String s = (String) iterator.next();
                System.out.println("    " + s);
            }
        }
        System.out.println("WebApp directories:");
        for (Iterator iterator = c.getMappings().entrySet().iterator(); iterator.hasNext();) {
            Map.Entry entry = (Map.Entry) iterator.next();
            System.out.println(entry.getKey() + "  ->  " + entry.getValue());
        }

        // prepare the classloader
        List libs = c.getLibs();
        List classDirs = c.getClassDirs();
        ClassLoader parent = new MultiDirClassLoader((String[]) libs.toArray(new String[libs.size()]),
                (String[]) classDirs.toArray(new String[classDirs.size()]),
                Thread.currentThread().getContextClassLoader());

        if (c.getSources() != null) {
            for (Iterator iterator = c.getSources().iterator(); iterator.hasNext();) {
                String source = (String) iterator.next();
                File file = new File(source);
                CompilingClassLoader ccl = new CompilingClassLoader(parent, file);
                ccl.start();
                parent = ccl;
            }
        }
        URLClassLoader url = new MyURLClassLoader(parent);
        Thread.currentThread().setContextClassLoader(url);

        Class clazz = url.loadClass("com.opensymphony.webwork.quickstart.JettyServer");
        Method method = clazz.getDeclaredMethod("startServer",
                new Class[]{int.class, String.class, List.class, Map.class, String.class});
        method.invoke(null, new Object[]{c.port, c.getContext(), c.getPathPriority(), c.getMappings(), c.getResolver()});

        System.out.println("");
        System.out.println("********************************************************");
        System.out.println("Quick-started at http://localhost:" + c.getPort() + c.getContext());
        System.out.println("You may now edit your Java source files and web files.");
        System.out.println("********************************************************");
    }

    static class QuickStartWebAppContext extends WebApplicationContext {
        public QuickStartWebAppContext() {
        }

        public QuickStartWebAppContext(String string) {
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

        public MyURLClassLoader(ClassLoader parent) {
            super(new URL[0], parent);
            this.parent = parent;
        }

        public Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
            if (name.startsWith("java.") || name.startsWith("sun.")) {
                return super.loadClass(name, resolve);
            }

            ClassLoader parent = getParent();
            // First, check if the class has already been loaded
            Class c = findLoadedClass(name);
            if (c == null) {
                try {
                    c = findClass(name);
                } catch (Throwable t) {
                    // If still not found, only then ask the parent
                    c = parent.loadClass(name);
                }
            }
            if (resolve) {
                resolveClass(c);
            }

            return c;
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
