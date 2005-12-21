package org.hibernate.auction.persistence;

import org.hibernate.*;
import org.hibernate.usertype.UserType;
import org.hibernate.auction.model.*;

import java.sql.*;
import java.io.Serializable;

/**
 * Hibernate custom mapping type for ItemState.
 * <p>
 * This mapping type persists item state to a <tt>VARCHAR</tt>
 * database column.
 *
 * @see org.hibernate.auction.model.ItemState
 * @author Christian Bauer <christian@hibernate.org>
 */
public class ItemStateUserType implements UserType {

    private static final int[] SQL_TYPES = {Types.CHAR};

    public int[] sqlTypes() { return SQL_TYPES; }
    public Class returnedClass() { return ItemState.class; }
    public boolean equals(Object x, Object y) { return x == y; }
    public Object deepCopy(Object value) { return value; }
    public boolean isMutable() { return false; }

    public Object nullSafeGet(ResultSet resultSet,
                              String[] names,
                              Object owner)
            throws HibernateException, SQLException {

      String name = resultSet.getString(names[0]);
      return resultSet.wasNull() ?
                null :
                ItemState.getInstance(name.charAt(0));
    }

    public void nullSafeSet(PreparedStatement statement,
                            Object value,
                            int index)
            throws HibernateException, SQLException {

        if (value == null) {
            statement.setNull(index, Types.CHAR);
        } else {
            statement.setString(index, value.toString());
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