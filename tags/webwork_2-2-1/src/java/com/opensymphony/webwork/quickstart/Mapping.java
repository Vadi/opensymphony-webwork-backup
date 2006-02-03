package com.opensymphony.webwork.quickstart;

/**
 * User: patrick
 * Date: Oct 19, 2005
 * Time: 9:50:34 AM
 */
public class Mapping {
    String path;
    String dir;

    public Mapping(String path, String dir) {
        this.path = path;
        this.dir = dir;
    }

    public Mapping() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }
}
