import java.io.File;
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
            System.out.println("  java -jar webwork-launcher.jar [command] (optional command args)");
            System.out.println("");
            System.out.println("Where [command] is one of the following:");
            System.out.println("  prototype");
            System.out.println("  prototype:webapp");
            System.out.println("  webflow");
            System.out.println("  webflow:webapp");
            System.out.println("");
            System.out.println("Execute the commands for additional usage instructions.");
            System.out.println("Note: the *:webapp commands are just shortcuts for ");
            System.out.println("      running the command on a webapp in the webapps dir.");
            System.out.println("      For example, 'prototype:webapp sandbox' will start");
            System.out.println("      prototype automatically for the webapp 'sandbox'");
            return;
        }

        // check the JDK version
        String version = System.getProperty("java.version");
        boolean jdk15 = version.indexOf("1.5") != -1;

        ArrayList urls = new ArrayList();
        try {
            findJars(new File("lib"), urls);
            urls.add(new File("webwork-2.2.jar").toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("Could not find URLs -- see stack trace.");
        }

        String command = args[0];
        String[] programArgs = new String[args.length - 1];
        System.arraycopy(args, 1, programArgs, 0, programArgs.length);
        if ("prototype:webapp".equals(command)) {
            command = "prototype";
            checkWebAppArgs(args);

            String name = args[1];
            programArgs = new String[]{"/" + name,
                    "webapps/" + name + "/src/webapp",
                    "webapps/" + name + "/src/java"};
        }

        if ("prototype".equals(command)) {
            if (!jdk15) {
                System.out.println("Sorry, but prototype only runs on Java 1.5.");
                System.out.println("You are running: " + version);
                System.out.println("Please try again with Java 1.5, or deploy");
                System.out.println("  as a normal J2EE webapp to use Java 1.4.");
                return;
            }

            launch("com.opensymphony.webwork.Prototype", programArgs, urls);
            return;
        }

        if ("webflow:webapp".equals(command)) {
            command = "webflow";
            checkWebAppArgs(args);

            String name = args[1];
            programArgs = new String[]{"-config", "webapps/" + name + "/src/webapp/WEB-INF/classes",
                    "-views", "webapps/" + name + "/src/webapp",
                    "-output", "."};
        }

        if ("webflow".equals(command)) {
            launch("com.opensymphony.webwork.webFlow.WebFlow", programArgs, urls);
            return;
        }
    }

    private static void checkWebAppArgs(String[] args) {
        if (args.length != 2) {
            System.out.println("Error: you must specify the webapp you wish");
            System.out.println("       to deploy. The webapp name must be the");
            System.out.println("       name of the directory found in webapps/.");
            System.exit(1);
        }
    }

    private static void launch(String program, String[] programArgs, ArrayList urls) {
        URLClassLoader cl = new URLClassLoader((URL[]) urls.toArray(new URL[urls.size()]),
                Main.class.getClassLoader());
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
                urls.add(f.toURL());
            }
        }
    }
}
