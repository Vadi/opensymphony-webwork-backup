/*
 * WebWork, Web Application Framework
 *
 * Distributable under Apache license.
 * See terms of license at opensource.org
 */
package webwork.action;

import com.opensymphony.xwork.Preparable;

/**
 * An Action wanting to prepare its state before receiving parameters
 * should implement this interface.
 * <p/>
 * A common use is to get current state from a database, which then
 * is overwritten partially by input parameters. The new state,
 * including the old that was not overwritten, may then be sent
 * back to the database
 *
 * @author Rickard Öberg (rickard@middleware-company.com)
 * @version $Revision$
 * @see Action
 */
public interface PrepareAction extends Preparable {
    // Public --------------------------------------------------------
}
