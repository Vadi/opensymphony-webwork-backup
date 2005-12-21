package org.hibernate.auction.command;

import java.rmi.RemoteException;

/**
 * A generic handler for EJB commands.
 *
 * @author Christian Bauer <christian@hibernate.org>
 */
public interface CommandHandler {

	public Command executeCommand(Command command)
		throws RemoteException, CommandException;

}
