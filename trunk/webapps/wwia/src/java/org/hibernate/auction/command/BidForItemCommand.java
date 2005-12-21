package org.hibernate.auction.command;

import org.hibernate.auction.model.*;
import org.hibernate.auction.dao.*;
import org.hibernate.auction.exceptions.*;
import org.hibernate.auction.persistence.HibernateUtil;
import org.hibernate.auction.user.UserSession;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * An example of the EJB command pattern.
 * <p>
 * Some parameters are passed in, the control logic is executed, the
 * result comes back.
 *
 * @author Christian Bauer <christian@hibernate.org>
 */
public class BidForItemCommand implements Command {

	private Long userId;
	private Long itemId;
	private BigDecimal bidAmount;

	private Bid newBid;

	public BidForItemCommand(Long userId,
							 Long itemId,
							 BigDecimal bidAmount) {
		this.userId = userId;
		this.itemId = itemId;
		this.bidAmount = bidAmount;
	}

	public Bid getNewBid() {
		return newBid;
	}

	public void execute() throws CommandException {

		try {
			ItemDAO itemDAO = new ItemDAO();
			UserDAO userDAO = new HibernateUserDAO();

			MonetaryAmount newAmount =
			        new MonetaryAmount(bidAmount, (Currency)UserSession.get("currency"));
			Bid currentMaxBid = itemDAO.getMaxBid(itemId);
			Bid currentMinBid = itemDAO.getMinBid(itemId);

			Item item = itemDAO.getItemById(itemId, true);
			newBid = item.placeBid(userDAO.getUserById(userId, false),
									newAmount,
									currentMaxBid,
			                        currentMinBid);

			HibernateUtil.commitTransaction();

		} catch (InfrastructureException ex) {
			// Rethrow as a checked exception
			HibernateUtil.rollbackTransaction();
			throw new CommandException(ex);

		} catch (BusinessException ex) {
			// Rethrow as a checked exception
			throw new CommandException(ex);

		} finally {
			HibernateUtil.closeSession();
		}
	}

}
