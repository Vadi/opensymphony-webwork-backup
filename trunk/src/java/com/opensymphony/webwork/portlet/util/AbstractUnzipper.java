package com.opensymphony.webwork.portlet.util;

import org.apache.log4j.Category;

import java.io.*;
import java.util.zip.ZipEntry;

public abstract class AbstractUnzipper implements Unzipper {

    protected static Category log;

    private static final int BUFFER_SIZE = 10240;

    protected File destDir;

    static {
        log = Category.getInstance(com.opensymphony.webwork.portlet.util.FileUnzipper.class);
    }

    public abstract File unzipFileInArchive(String s) throws Exception;

    public abstract void unzip() throws Exception;

    public AbstractUnzipper() {
    }

    protected File saveEntry(InputStream is, ZipEntry entry) throws Exception {
        File file = new File(destDir, entry.getName());
        if (entry.isDirectory()) {
            file.mkdirs();
        } else {
            File dir = new File(file.getParent());
            dir.mkdirs();
            FileOutputStream fos = new FileOutputStream(file);
            pump(is, fos);
            fos.flush();
            fos.close();
        }
        return file;
    }

    private static void pump(InputStream is, OutputStream os) throws IOException {
        byte buffer[] = new byte[10240];
        int lengthRead;
        while ((lengthRead = is.read(buffer)) >= 0) os.write(buffer, 0, lengthRead);
    }

}

