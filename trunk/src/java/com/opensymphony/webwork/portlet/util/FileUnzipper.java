package com.opensymphony.webwork.portlet.util;


import com.opensymphony.util.TextUtils;

import java.io.File;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileUnzipper extends AbstractUnzipper {
    private File zipFile;

    public FileUnzipper(File zipFile, File destDir) {
        this.zipFile = zipFile;
        super.destDir = destDir;
    }

    public void unzip() throws Exception {
        if (zipFile == null || !zipFile.isFile())
            return;
        ZipFile zf = new ZipFile(zipFile);
        ZipEntry zipEntry;
        for (Enumeration zipEntries = zf.entries(); zipEntries.hasMoreElements(); saveEntry(
                zf.getInputStream(zipEntry), zipEntry))
            zipEntry = (ZipEntry) zipEntries.nextElement();

        zf.close();
    }

    public File unzipFileInArchive(String fileName) throws Exception {
        File result = null;
        if (zipFile == null || !zipFile.isFile() || !TextUtils.stringSet(fileName))
            return result;
        boolean fileFound = false;
        ZipFile zf = new ZipFile(zipFile);
        Enumeration zipEntries = zf.entries();
        do {
            if (!zipEntries.hasMoreElements())
                break;
            ZipEntry entry = (ZipEntry) zipEntries.nextElement();
            String entryName = entry.getName();
            if (TextUtils.stringSet(entryName) && entryName.startsWith("/"))
                entryName = entryName.substring(1);
            if (fileName.equals(entryName)) {
                fileFound = true;
                result = saveEntry(zf.getInputStream(entry), entry);
            }
        } while (true);
        if (!fileFound)
            AbstractUnzipper.log.error("The file: " + fileName + " could not be found in the archive: "
                    + zipFile.getAbsolutePath());
        zf.close();
        return result;
    }

}