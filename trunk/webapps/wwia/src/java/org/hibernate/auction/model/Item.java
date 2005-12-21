package org.hibernate.auction.model;

import org.hibernate.auction.exceptions.*;

import java.io.Serializable;
import java.util.*;

/**
 * An item for sale.
 *
 * @author Christian Bauer <christian@hibernate.org>
 */
public class Item implements Serializable, Comparable, Auditable {

    private Long id = null;
    private int version;
    private String name;
    private User seller;
    private String description;
    private MonetaryAmount initialPrice;
    private MonetaryAmount reservePrice;
    private Date startDate;
    private Date endDate;
    private Set categorizedItems = new HashSet();
    private Collection bids = new ArrayList();
    private Bid successfulBid;
    private ItemState state;
    private User approvedBy;
    private Date approvalDatetime;
    private Date created = new Date();

    /**
     * No-arg constructor for JavaBean tools.
     */
    public Item() {
    }

    /**
     * Full constructor.
     */
    public Item(String name, String description, User seller,
                MonetaryAmount initialPrice, MonetaryAmount reservePrice,
                Date startDate, Date endDate,
                Set categories, List bids, Bid successfulBid) {
        this.name = name;
        this.seller = seller;
        this.description = description;
        this.initialPrice = initialPrice;
        this.reservePrice = reservePrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.categorizedItems = categories;
        this.bids = bids;
        this.successfulBid = successfulBid;
        this.state = ItemState.DRAFT;
    }

    /**
     * Simple properties only constructor.
     */
    public Item(String name, String description, User seller,
                MonetaryAmount initialPrice, MonetaryAmount reservePrice,
                Date startDate, Date endDate) {
        this.name = name;
        this.seller = seller;
        this.description = description;
        this.initialPrice = initialPrice;
        this.reservePrice = reservePrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = ItemState.DRAFT;
    }

    // ********************** Accessor Methods ********************** //

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MonetaryAmount getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(MonetaryAmount initialPrice) {
        this.initialPrice = initialPrice;
    }

    public MonetaryAmount getReservePrice() {
        return reservePrice;
    }

    public void setReservePrice(MonetaryAmount reservePrice) {
        this.reservePrice = reservePrice;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Set getCategorizedItems() {
        return categorizedItems;
    }

    public void addCategorizedItem(CategorizedItem catItem) {
        if (catItem == null)
            throw new IllegalArgumentException("Can't add a null CategorizedItem.");
        this.getCategorizedItems().add(catItem);
    }

    public Collection getBids() {
        return bids;
    }

    public void addBid(Bid bid) {
        if (bid == null)
            throw new IllegalArgumentException("Can't add a null Bid.");
        this.getBids().add(bid);
    }

    public Bid getSuccessfulBid() {
        return successfulBid;
    }

    public void setSuccessfulBid(Bid successfulBid) {
        this.successfulBid = successfulBid;
    }

    public ItemState getState() {
        return state;
    }

    public void setState(ItemState state) {
        this.state = state;
    }

    public User getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(User approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Date getApprovalDatetime() {
        return approvalDatetime;
    }

    public void setApprovalDatetime(Date approvalDatetime) {
        this.approvalDatetime = approvalDatetime;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    // ********************** Common Methods ********************** //

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        final Item item = (Item) o;

        if (!created.equals(item.created)) return false;
        if (name != null ? !name.equals(item.name) : item.name != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (name != null ? name.hashCode() : 0);
        result = 29 * result + created.hashCode();
        return result;
    }

    public String toString() {
        return "Item ('" + getId() + "'), " +
                "Name: '" + getName() + "' " +
                "Initial Price: '" + getInitialPrice() + "'";
    }

    public int compareTo(Object o) {
        if (o instanceof Item) {
            return this.getCreated().compareTo(((Item) o).getCreated());
        }
        return 0;
    }

    // ********************** Business Methods ********************** //

    /**
     * Places a bid while checking business constraints.
     * <p/>
     * This method may throw a BusinessException if one of the requirements
     * for the bid placement wasn't met, e.g. if the auction already ended.
     *
     * @param bidder
     * @param bidAmount
     * @param currentMaxBid the most valuable bid for this item
     * @param currentMinBid the least valuable bid for this item
     * @return
     * @throws BusinessException
     */
    public Bid placeBid(User bidder, MonetaryAmount bidAmount,
                        Bid currentMaxBid, Bid currentMinBid)
            throws BusinessException {

        // Check highest bid (can also be a different Strategy (pattern))
        if (currentMaxBid != null && currentMaxBid.getAmount().compareTo(bidAmount) > 0) {
            throw new BusinessException("Bid too low.");
        }

        // Auction is active
        if (!state.equals(ItemState.ACTIVE))
            throw new BusinessException("Auction is not active yet.");

        // Auction still valid
        if (this.getEndDate().before(new Date()))
            throw new BusinessException("Can't place new bid, auction already ended.");

        // Create new Bid
        Bid newBid = new Bid(bidAmount, this, bidder);

        // Place bid for this Item
        this.addBid(newBid);

        return newBid;
    }

    /**
     * Anyone can set this item into PENDING state for approval.
     */
    public void setPendingForApproval() {
        state = ItemState.PENDING;
    }

    /**
     * Approve this item for auction and set its state to active.
     *
     * @param byUser
     * @throws BusinessException
     */
    public void approve(User byUser) throws BusinessException {

        if (!byUser.isAdmin())
            throw new PermissionException("Not an administrator.");

        if (!state.equals(ItemState.PENDING))
            throw new IllegalStateException("Item still in draft.");

        state = ItemState.ACTIVE;
        approvedBy = byUser;
        approvalDatetime = new Date();
    }

}
