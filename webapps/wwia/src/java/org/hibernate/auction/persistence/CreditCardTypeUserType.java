package org.hibernate.auction.persistence;

import org.hibernate.*;
import org.hibernate.usertype.UserType;
import org.hibernate.auction.model.CreditCardType;

import java.sql.*;
import java.io.Serializable;

/**
 * Hibernate custom mapping type for CreditCardType.
 * <p>
 * This mapping type persists credit card type information to a
 * <tt>SMALLINT</tt> database column.
 *
 * @see CreditCardType
 * @author Christian Bauer <christian@hibernate.org>
 */
public class CreditCardTypeUserType implements UserType {

    private static final int[] SQL_TYPES = {Types.SMALLINT};
    public int[] sqlTypes() {  return SQL_TYPES; }
    public Class returnedClass() { return CreditCardType.class; }
    public boolean equals(Object x, Object y) { return x == y; }
    public Object deepCopy(Object value) { return value; }
    public boolean isMutable() { return false; }

    public Object nullSafeGet(ResultSet resultSet,
                              String[] names,
                              Object owner)
            throws HibernateException, SQLException {

        int typeCode = resultSet.getInt(names[0]);
        return resultSet.wasNull() ? null : CreditCardType.getInstance(typeCode);
    }

    public void nullSafeSet(PreparedStatement statement,
                            Object value,
                            int index)
            throws HibernateException, SQLException {

        if (value == null) {
            statement.setNull(index, Types.SMALLINT);
        } else {
            statement.setInt(index, ((CreditCardType)value).toInteger().intValue());
        }
    }

    public int hashCode(Object object) throws HibernateException {
        return 0;
    }

    public Serializable disassemble(Object object) throws HibernateException {
        return null;
    }

    public Object assemble(Serializable serializable, Object object) throws HibernateException {
        return null;
    }

    public Object replace(Object object, Object object1, Object object2) throws HibernateException {
        return null;
    }

}
