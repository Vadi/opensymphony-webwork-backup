package com.opensymphony.webwork.portlet.util;

import com.opensymphony.util.TextUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    private static Log log;

    private static final int BUFFER_SIZE = 1024;

    static {
        log = LogFactory.getLog(com.opensymphony.webwork.portlet.util.FileUtils.class);
    }

    public FileUtils() {
    }

    public static void copyDirectory(File srcDir, File destDir) throws IOException {
        copyDirectory(srcDir, destDir, false);
    }

    public static void copyDirectory(File srcDir, File destDir, boolean overwrite) throws IOException {
        File files[] = srcDir.listFiles();
        if (!destDir.exists())
            destDir.mkdirs();
        else
            log.debug(destDir.getAbsolutePath() + " already exists");
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            File dest = new File(destDir, file.getName());
            if (file.isFile())
                copyFile(new FileInputStream(file), dest, overwrite);
            else
                copyDirectory(file, dest, overwrite);
        }

    }

    public static void copyFile(File srcFile, File destFile) throws IOException {
        InputStream input = new FileInputStream(srcFile);
        copyFile(input, destFile);
    }

    public static void copyFile(InputStream srcStream, File destFile) throws IOException {
        copyFile(srcStream, destFile, false);
    }

    public static void copyFile(InputStream srcStream, File destFile, boolean overwrite) throws IOException {
        File parentFile = destFile.getParentFile();
        if (!parentFile.isDirectory())
            parentFile.mkdirs();
        if (destFile.exists()) {
            if (overwrite) {
                log.debug("Overwriting file at: " + destFile.getAbsolutePath());
                writeStreamToFile(srcStream, destFile);
            } else {
                log.warn(destFile.getAbsolutePath() + " already exists");
            }
        } else {
            destFile.createNewFile();
            writeStreamToFile(srcStream, destFile);
        }
    }

    private static void writeStreamToFile(InputStream srcStream, File destFile) throws IOException {
        OutputStream output;
        Exception exception;
        InputStream input = null;
        output = null;
        try {
            input = new BufferedInputStream(srcStream);
            output = new BufferedOutputStream(new FileOutputStream(destFile));
            int ch;
            while ((ch = input.read()) != -1) output.write(ch);
        } catch (IOException e) {
            log.error("Error writing stream to file: " + destFile.getAbsolutePath());
            throw e;
        } finally {
            input.close();
        }
        output.close();

    }

    public static void saveTextFile(String stringContent, File destFile) throws IOException {
        ensureFileAndPathExist(destFile);
        FileWriter writer = new FileWriter(destFile);
        writer.write(stringContent);
        writer.close();
    }

    public static void ensureFileAndPathExist(File file) throws IOException {
        file.getParentFile().mkdirs();
        file.createNewFile();
    }

    public static void createZipFile(File baseDir, File zipFile) throws Exception {
        FolderArchiver compressor = new FolderArchiver(baseDir, zipFile);
        compressor.doArchive();
    }

    public static List readResourcesAsList(String resource) {
        List result = new ArrayList();
        try {
            InputStream is = ClassLoaderUtils.getResourceAsStream(resource, com.opensymphony.webwork.portlet.util.FileUtils.class);
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            do {
                String s;
                if ((s = in.readLine()) == null)
                    break;
                String niceS = TextUtils.noNull(s).trim();
                if (TextUtils.stringSet(niceS) && niceS.charAt(0) != '#')
                    result.add(s);
            } while (true);
            is.close();
        } catch (IOException e) {
            log.error("IOException reading stream: " + e, e);
        }
        return result;
    }

    public static String getResourceContent(String resource) {
        InputStream is = ClassLoaderUtils.getResourceAsStream(resource, com.opensymphony.webwork.portlet.util.FileUtils.class);
        return getInputStreamTextContent(is);
    }

    public static String getResourceContent(HttpServletRequest req, String resource) {
        InputStream is = req.getSession().getServletContext().getResourceAsStream(resource);
        String result = getInputStreamTextContent(is);
        if (result == null)
            result = "";
        return result;
    }

    public static String getInputStreamTextContent(InputStream is) {
        if (is == null)
            return null;
        String result = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(is.available());
            pump(is, baos);
            result = new String(baos.toByteArray());
            is.close();
        } catch (IOException e) {
            log.error("IOException reading stream: " + e, e);
        }
        return result;
    }

    private static void pump(InputStream is, OutputStream os) throws IOException {
        byte buffer[] = new byte[1024];
        int lengthRead;
        while ((lengthRead = is.read(buffer)) >= 0) os.write(buffer, 0, lengthRead);
    }

    public static boolean deleteDir(File dir) {
        if (dir == null)
            return false;
        File candir;
        try {
            candir = dir.getCanonicalFile();
        } catch (IOException e) {
            return false;
        }
        if (!candir.equals(dir.getAbsoluteFile()))
            return false;
        File files[] = candir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                boolean deleted = !file.delete();
                if (deleted && file.isDirectory())
                    deleteDir(file);
            }

        }
        return dir.delete();
    }

}

