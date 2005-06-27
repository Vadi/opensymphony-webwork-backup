package com.opensymphony.webwork.webFlow.entities;

import com.opensymphony.util.FileUtils;
import com.opensymphony.webwork.config.Configuration;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: plightbo
 * Date: Jun 25, 2005
 * Time: 2:07:43 PM
 */
public abstract class FileBasedView implements View {
    private String name;
    private String contents;

    public FileBasedView(File file) {
        this.name = file.getName();
        // get the contents as a single line
        this.contents = FileUtils.readFile(file).replaceAll("[\r\n ]+", " ");
    }

    public String getName() {
        return name;
    }

    public Set getTargets() {
        HashSet targets = new HashSet();

        // links
        matchPatterns(getLinkPattern(), targets);

        // actions
        matchPatterns(getActionPattern(), targets);

        // forms
        matchPatterns(getFormPattern(), targets);

        return targets;
    }

    protected Pattern getLinkPattern() {
        Object ext = Configuration.get("webwork.action.extension");
        String actionRegex = "([A-Za-z0-9\\._\\-\\!]+\\." + ext + ")";
        return Pattern.compile(actionRegex);
    }

    private void matchPatterns(Pattern pattern, Set targets) {
        Matcher matcher = pattern.matcher(contents);
        while (matcher.find()) {
            String target = matcher.group(1);
            targets.add(Target.link(target));
        }
    }

    protected abstract Pattern getActionPattern();

    protected abstract Pattern getFormPattern();
}
