/*
 * WebWork, Web Application Framework
 *
 * Distributable under Apache license.
 * See terms of license at opensource.org
 */
package webwork.action;

/**
 * Provides multiple execution paths or "commands" for actions implementing
 * this interface.
 *
 * @see webwork.action.factory.MigrationActionFactory
 * @see webwork.action.factory.CommandActionFactoryProxy
 */
public interface CommandDriven {
    // Public Interfaces ---------------------------------------------

    public void setCommand(String command);
}
