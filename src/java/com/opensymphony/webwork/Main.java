package com.opensymphony.webwork;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

/**
 * User: plightbo
 * Date: Aug 9, 2005
 * Time: 12:22:06 AM
 */
public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage:");
            System.out.println("  java -jar webwork.jar [command] (optional command args)");
            System.out.println("");
            System.out.println("Where [command] is one of the following:");
            System.out.println("  quickstart");
            System.out.println("  quickstart:xxx");
            System.out.println("  sitegraph");
            System.out.println("  sitegraph:xxx");
            System.out.println("");
            System.out.println("Execute the commands for additional usage instructions.");
            System.out.println("Note: the *:xxx commands are just shortcuts for ");
            System.out.println("      running the command on a webapp in the webapps dir.");
            System.out.println("      For example, 'quickstart:sandbox' will start QuickStart");
            System.out.println("      automatically for the webapp 'sandbox'.");
            return;
        }

        // check the JDK version
        String version = System.getProperty("java.version");
        boolean jdk15 = version.indexOf("1.5") != -1;

        String javaHome = System.getProperty("java.home");
        ArrayList urls = new ArrayList();
        try {
            findJars(new File("lib"), urls);

            // use all the jars in the current that start with "webwork" and end with ".jar", but aren't the src jar
            File wd = new File(".");
            File[] jars = wd.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    if (name.startsWith("webwork") && name.endsWith(".jar") && name.indexOf("-src.") == -1) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            for (int i = 0; i < jars.length; i++) {
                File jar = jars[i];
                urls.add(jar.toURL());
            }

            // ... but there might not be any (ie: we're in development in IDEA), so use this as backup
            urls.add(new File("build/java/").toURL());
            urls.add(new File("../xwork/build/java/").toURL());

            // load tools.jar from JAVA_HOME
            File tools = new File(javaHome, "lib/tools.jar");
            if (!tools.exists()) {
                // hmm, not there, how about java.home?
                tools = new File(javaHome, "../lib/tools.jar");
            }
            if (!tools.exists()) {
                // try the OS X common path
                tools = new File(javaHome, "../Classes/classes.jar");
            }
            if (!tools.exists()) {
                // try the other OS X common path
                tools = new File(javaHome, "../Classes/classes.jar");
            }
            if (!tools.exists()) {
                // did the user specify it by hand?
                String prop = System.getProperty("tools");
                if (prop != null) {
                    tools = new File(prop);
                }
            }
            if (!tools.exists()) {
                System.out.println("Error: Could not find tools.jar! Please do one of the following: ");
                System.out.println("");
                System.out.println("        - Use the JDK's JVM (ie: c:\\jdk1.5.0\\bin\\java)");
                System.out.println("        - Specify JAVA_HOME to point to your JDK 1.5 home");
                System.out.println("        - Specify a direct path to tools.jar via, as shown below:");
                System.out.println("");
                System.out.println("       java -Dtools=/path/to/tools.jar -jar webwork-launcher.jar ...");
                return;
            }

            // finally, add the verified tools.jar
            urls.add(tools.toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("Could not find URLs -- see stack trace.");
        }

        String command = args[0];
        String[] programArgs = new String[args.length - 1];
        System.arraycopy(args, 1, programArgs, 0, programArgs.length);
        if (command.startsWith("quickstart:")) {
            command = "quickstart";
            String name = checkWebAppArgs(args);
            programArgs = new String[]{"/" + name,
                    "webapps/" + name + "/src/webapp",
                    "webapps/" + name + "/src/java"};
        }

        if ("quickstart".equals(command)) {
            if (!jdk15) {
                System.out.println("Sorry, but QuickStart only runs on Java 1.5.");
                System.out.println("You are running: " + version);
                System.out.println("Please try again with Java 1.5, or deploy");
                System.out.println("  as a normal J2EE webapp to use Java 1.4.");
                return;
            }

            launch("com.opensymphony.webwork.QuickStart", programArgs, urls);
            return;
        }

        if (command.startsWith("sitegraph:")) {
            command = "sitegraph";
            String name = checkWebAppArgs(args);
            programArgs = new String[]{"-config", "webapps/" + name + "/src/webapp/WEB-INF/classes",
                    "-views", "webapps/" + name + "/src/webapp",
                    "-output", "."};
        }

        if ("sitegraph".equals(command)) {
            launch("com.opensymphony.webwork.sitegraph.SiteGraph", programArgs, urls);
        }
    }

    private static String checkWebAppArgs(String[] args) {
        int colon = args[0].indexOf(':');
        String name = null;
        try {
            name = args[0].substring(colon + 1);
        } catch (Exception e) {
        }
        if (name == null || name.equals("")) {
            System.out.println("Error: you must specify the webapp you wish");
            System.out.println("       to deploy. The webapp name must be the");
            System.out.println("       name of the directory found in webapps/.");
            System.out.println("");
            System.out.println("Example: java -jar webwork.jar quickstart:sandbox");
            System.exit(1);
        }

        return name;
    }

    private static void launch(String program, String[] programArgs, ArrayList urls) {
        URLClassLoader cl = new MainClassLoader((URL[]) urls.toArray(new URL[urls.size()]));
        Thread.currentThread().setContextClassLoader(cl);
        try {
            Class clazz = cl.loadClass(program);
            Method main = clazz.getDeclaredMethod("main", new Class[]{String[].class});
            main.invoke(null, new Object[]{programArgs});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void findJars(File file, ArrayList urls) throws MalformedURLException {
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            if (f.isDirectory()) {
                findJars(f, urls);
            } else if (f.getName().endsWith(".jar")) {
                if (isValid(f.getName())) {
                    urls.add(f.toURL());
                }
            }
        }
    }

    private static boolean isValid(String name) {
        if (!"dom.jar".equals(name)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Reverses the typical order of classloading to defer only to the parent if the
     * current class loader can't be found. This is required to allow for the launcher
     * to be embedded within webwork.jar (otherwise the dependencies wouldn't be found
     * by the system ClassLoader when invoking using "java -jar webwork.jar ...").
     */
    public static class MainClassLoader extends URLClassLoader {
        public MainClassLoader(URL[] urls) {
            super(urls);
        }

        public Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
            ClassLoader parent = getParent();
            // First, check if the class has already been loaded
            Class c = findLoadedClass(name);
            if (c == null) {
                try {
                    c = findClass(name);
                } catch (ClassNotFoundException e) {
                    // If still not found, only then ask the parent
                    c = parent.loadClass(name);
                }
            }
            if (resolve) {
                resolveClass(c);
            }

            return c;
        }
    }
}
