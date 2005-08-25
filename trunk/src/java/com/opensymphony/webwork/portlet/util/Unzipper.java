package com.opensymphony.webwork.portlet.util;

import java.io.File;

public interface Unzipper {

    public abstract void unzip()
            throws Exception;

    public abstract File unzipFileInArchive(String s)
            throws Exception;
}
