package com.opensymphony.webwork.plugin;

import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.GlobalSearchScope;
import com.opensymphony.xwork.config.ConfigurationManager;
import com.opensymphony.xwork.config.RuntimeConfiguration;
import com.opensymphony.xwork.config.entities.ActionConfig;
import com.opensymphony.xwork.config.entities.ResultConfig;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.Map;

public class ActionsPanel extends JPanel {
    JTree tree;
    Project project;
    String[] webroots;

    public ActionsPanel(final Project project, final String[] webroots) {
        this.project = project;
        this.webroots = webroots;

        final DefaultMutableTreeNode root = new DefaultMutableTreeNode("WebWork Actions", true);
        tree = new JTree(root);
        tree.expandRow(0);
        reloadTree(root);
        this.setLayout(new BorderLayout());
        this.add(tree, BorderLayout.CENTER);

        final JButton button = new JButton("Reload");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Do something here.
                DefaultTreeModel treeModel = (DefaultTreeModel) tree.getModel();
                root.removeAllChildren();
                reloadTree((root));

                treeModel.nodeStructureChanged(root);

            }
        });
        this.add(button, BorderLayout.NORTH);

        // set up mouse listener
        tree.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                TreePath path = tree.getPathForLocation(e.getX(), e.getY());
                if (path != null) {
                    tree.setSelectionPath(path);
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                    Object obj = node.getUserObject();
                    if (obj == null) {
                        return;
                    }

                    if (obj instanceof TN) {
                        TN tn = (TN) obj;
                        System.out.println("Opening: " + tn.ac.getClassName());

                        PsiManager manager = PsiManager.getInstance(project);
                            PsiClass psi = manager.findClass(tn.ac.getClassName(), GlobalSearchScope.allScope(project));
                            if (psi != null) {
                                psi = (PsiClass) psi.getNavigationElement();
                                OpenFileDescriptor file = new
                                        OpenFileDescriptor(psi.getContainingFile().getVirtualFile(), 0);
                                FileEditorManager.getInstance(project).openTextEditor(file, true);
                                WindowManager.getInstance().suggestParentWindow(project).toFront();
                            }
                    } else if (obj instanceof RN) {
                        RN rn = (RN) obj;
                        String location = (String) rn.rc.getParams().get("location");

                        if (!location.startsWith("/")) {
                            // prepend the namespace
                            String namespace = path.getParentPath().getParentPath().getLastPathComponent().toString();
                            location = namespace + "/" + location;
                        }

                        System.out.println("Opening: " + location);

                        for (int i = 0; i < webroots.length; i++) {
                            String webroot = webroots[i];
                            VirtualFile file = FileUtils.findFile(project, webroot + location);
                            if (file != null) {
                                OpenFileDescriptor ofd = new OpenFileDescriptor(file);
                                FileEditorManager.getInstance(project).openTextEditor(ofd, true);
                                WindowManager.getInstance().suggestParentWindow(project).toFront();
                            }
                        }
                    }
                }
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }
        });
    }

    private void reloadTree(DefaultMutableTreeNode root) {
        RuntimeConfiguration rc = ConfigurationManager.getConfiguration().getRuntimeConfiguration();
        ConfigurationManager.getConfiguration().reload();
        Map acs = rc.getActionConfigs();
        for (Iterator iterator = acs.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String namespace = (String) entry.getKey();
            Map configs = (Map) entry.getValue();
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(namespace, true);

            if (!configs.isEmpty()) {
                for (Iterator iterator1 = configs.entrySet().iterator(); iterator1.hasNext();) {
                    Map.Entry entry1 = (Map.Entry) iterator1.next();
                    String name = (String) entry1.getKey();
                    ActionConfig ac = (ActionConfig) entry1.getValue();
                    DefaultMutableTreeNode actionNode = new DefaultMutableTreeNode(new TN(name, ac));
                    node.add(actionNode);

                    for (Iterator iterator2 = ac.getResults().entrySet().iterator(); iterator2.hasNext();) {
                        Map.Entry re = (Map.Entry) iterator2.next();
                        String resultName = (String) re.getKey();
                        ResultConfig result = (ResultConfig) re.getValue();
                        DefaultMutableTreeNode rn = new DefaultMutableTreeNode(new RN(resultName, result), false);
                        actionNode.add(rn);
                    }
                }

                root.add(node);
            }
        }
    }

    class TN {
        String name;
        ActionConfig ac;

        public TN(String name, ActionConfig ac) {
            this.name = name;
            this.ac = ac;
        }

        public String toString() {
            return name;
        }
    }

    class RN {
        String name;
        ResultConfig rc;

        public RN(String name, ResultConfig rc) {
            this.name = name;
            this.rc = rc;
        }

        public String toString() {
            return name + "->" + rc.getParams().get("location");
        }
    }
}
