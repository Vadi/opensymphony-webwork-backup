package com.opensymphony.webwork.plugin;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;

public class FileUtils {
    public static VirtualFile findFile(Project project, String name) {
        Module[] modules = ModuleManager.getInstance(project).getModules();
        for (int i = 0; i < modules.length; i++) {
            Module module = modules[i];
            VirtualFile[] roots = ModuleRootManager.getInstance(module).getContentRoots();
            for (int j = 0; j < roots.length; j++) {
                VirtualFile root = roots[j];
                VirtualFile match = root.findFileByRelativePath(name);
                if (match != null) {
                    return match;
                }
            }
        }

        return null;
    }
}
