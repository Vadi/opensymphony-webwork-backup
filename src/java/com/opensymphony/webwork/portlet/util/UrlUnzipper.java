package com.opensymphony.webwork.portlet.util;


import java.io.File;
import java.net.URL;
import java.util.zip.ZipInputStream;

public class UrlUnzipper extends AbstractUnzipper {
    private URL zipUrl;


    public UrlUnzipper(URL zipUrl, File destDir) {
        this.zipUrl = zipUrl;
        super.destDir = destDir;
    }

    public void unzip()
            throws Exception {
        ZipInputStream zis = new ZipInputStream(zipUrl.openStream());
        java.util.zip.ZipEntry zipEntry;
        while ((zipEntry = zis.getNextEntry()) != null) saveEntry(zis, zipEntry);
    }

    public File unzipFileInArchive(String fileName)
            throws Exception {
        throw new UnsupportedOperationException("Feature not implemented.");
    }

}
