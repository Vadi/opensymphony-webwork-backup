package org.hibernate.auction.persistence.audit;

import java.util.Date;
import java.io.Serializable;

/**
 * A trivial audit log record.
 * <p>
 * This simple value class represents a single audit event.
 *
 * @author Christian Bauer <christian@hibernate.org>
 */
public class AuditLogRecord {

	public String message;
	public Long entityId;
	public Class entityClass;
	public Long userId;
	public Date created;

	AuditLogRecord() {}

	public AuditLogRecord(String message,
						  Long entityId,
						  Class entityClass,
						  Long userId) {
		this.message = message;
		this.entityId = entityId;
		this.entityClass = entityClass;
		this.userId = userId;
		this.created = new Date();
	}
}
