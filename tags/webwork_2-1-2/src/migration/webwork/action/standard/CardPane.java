/*
 * WebWork, Web Application Framework
 *
 * Distributable under Apache license.
 * See terms of license at opensource.org
 */
package webwork.action.standard;

import webwork.action.ActionSupport;
import webwork.action.ParameterAware;
import webwork.action.SingleValueMap;

import java.util.Map;

/**
 * Implement functionality similar to how java.awt.CardLayout works for templating web pages.
 *
 * @author Rickard Öberg (rickard@middleware-company.com)
 * @version $Revision$
 */
public class CardPane
        extends ActionSupport
        implements ParameterAware {
    // Attributes ----------------------------------------------------
    String defaultName;
    String paneName;
    Map parameters;

    // Static --------------------------------------------------------

    // Public --------------------------------------------------------
    /**
     * Default pane
     */
    public void setDefaultName(String aName) {
        defaultName = aName;
    }

    public String getDefaultName() {
        return defaultName;
    }

    public void setPaneName(String aName) {
        paneName = aName;
    }

    public String getPaneName() {
        return paneName;
    }

    // ParameterAware implementation ---------------------------------
    public void setParameters(Map aMap) {
        parameters = new SingleValueMap(aMap);
    }

    // Action implementation -----------------------------------------
    /**
     * Get chosen pane, or default if no pane is selected
     */
    public String execute()
            throws Exception {
        // Get state of pane by getting parameter with name=pane name
        String name = (String) parameters.get(paneName);

        // Return name of chosen pane prefixed with the name of this pane set
        return paneName + "." + (name == null ? defaultName : name);
    }
}

