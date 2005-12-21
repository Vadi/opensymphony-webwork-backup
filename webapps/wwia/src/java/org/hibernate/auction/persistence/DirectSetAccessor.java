package org.hibernate.auction.persistence;

import org.hibernate.property.*;
import org.hibernate.*;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.util.ReflectHelper;
import org.apache.commons.logging.*;

import java.lang.reflect.*;
import java.beans.Introspector;

/**
 * This property accessor uses a getter method a sets the field directly.
 * <p>
 * Much (if not all) of this class has been copied from the Hibernate source.
 *
 * @author Christian Bauer <christian@hibernate.org>
 * @author Gavin King <gavin@hibernate.org>
 */
public class DirectSetAccessor implements PropertyAccessor {

	private static final Log log = LogFactory.getLog(DirectSetAccessor.class);

	public static final class DirectSetter implements Setter {
		private final Field field;
		private final Class clazz;
		private final String name;
		DirectSetter(Field field, Class clazz, String name) {
			this.field = field;
			this.clazz = clazz;
			this.name = name;
		}
		public Method getMethod() {
			return null;
		}
		public String getMethodName() {
			return null;
		}
		public void set(Object target, Object value) throws HibernateException {
			try {
				field.set(target, value);
			}
			catch (Exception e) {
				throw new PropertyAccessException(e, "could not set a field value by reflection", true, clazz, name);
			}
		}

        public void set(Object object, Object object1, SessionFactoryImplementor sessionFactoryImplementor) throws HibernateException {
        }

    }

	public static final class BasicGetter implements Getter {
		private Class clazz;
		private final Method method;
		private final String propertyName;

		private BasicGetter(Class clazz, Method method, String propertyName) {
			this.clazz=clazz;
			this.method=method;
			this.propertyName=propertyName;
		}

		public Object get(Object target) throws HibernateException {
			try {
				return method.invoke(target, null);
			}
			catch (InvocationTargetException ite) {
				throw new PropertyAccessException(ite, "Exception occurred inside", false, clazz, propertyName);
			}
			catch (IllegalAccessException iae) {
				throw new PropertyAccessException(iae, "IllegalAccessException occurred while calling", false, clazz, propertyName);
				//cannot occur
			}
			catch (IllegalArgumentException iae) {
				log.error(
					"IllegalArgumentException in class: " + clazz.getName() +
					", getter method of property: " + propertyName
				);
				throw new PropertyAccessException(iae, "IllegalArgumentException occurred calling", false, clazz, propertyName);
			}
		}

		public Class getReturnType() {
			return method.getReturnType();
		}

		public Method getMethod() {
			return method;
		}

		public String getMethodName() {
			return method.getName();
		}

        public Object getForInsert(Object object, SessionImplementor sessionImplementor) throws HibernateException {
            return null;
        }

    }

	private static Field getField(Class clazz, String name) throws PropertyNotFoundException {
		if ( clazz==null || clazz==Object.class ) throw new PropertyNotFoundException("field not found: " + name);
		Field field;
		try {
			field = clazz.getDeclaredField(name);
		}
		catch (NoSuchFieldException nsfe) {
			field = getField( clazz.getSuperclass(), name );
		}
		if ( !ReflectHelper.isPublic(clazz, field) ) field.setAccessible(true);
		return field;
	}

	private static BasicGetter getGetterOrNull(Class theClass, String propertyName) {

		if (theClass==Object.class || theClass==null) return null;

		Method method = getterMethod(theClass, propertyName);

		if (method!=null) {
			if ( !ReflectHelper.isPublic(theClass, method) ) method.setAccessible(true);
			return new BasicGetter(theClass, method, propertyName);
		}
		else {
			BasicGetter getter = getGetterOrNull( theClass.getSuperclass(), propertyName );
			if (getter==null) {
				Class[] interfaces = theClass.getInterfaces();
				for ( int i=0; getter==null && i<interfaces.length; i++ ) {
					getter=getGetterOrNull( interfaces[i], propertyName );
				}
			}
			return getter;
		}
	}

	private static Method getterMethod(Class theClass, String propertyName) {

		Method[] methods = theClass.getDeclaredMethods();
		for (int i=0; i<methods.length; i++) {
			// only carry on if the method has no parameters
			if ( methods[i].getParameterTypes().length==0 ) {
				String methodName = methods[i].getName();

				// try "get"
				if( methodName.startsWith("get") ) {
					String testStdMethod = Introspector.decapitalize( methodName.substring(3) );
					String testOldMethod = methodName.substring(3);
					if( testStdMethod.equals(propertyName) || testOldMethod.equals(propertyName) ) return methods[i];

				}

				// if not "get" then try "is"
				/*boolean isBoolean = methods[i].getReturnType().equals(Boolean.class) ||
					methods[i].getReturnType().equals(boolean.class);*/
				if( methodName.startsWith("is") ) {
					String testStdMethod = Introspector.decapitalize( methodName.substring(2) );
					String testOldMethod = methodName.substring(2);
					if( testStdMethod.equals(propertyName) || testOldMethod.equals(propertyName) ) return methods[i];
				}
			}
		}
		return null;
	}

	public Setter getSetter(Class theClass, String propertyName)
		throws PropertyNotFoundException {
		return new DirectSetter( getField(theClass, propertyName), theClass, propertyName );
	}

	public Getter getGetter(Class theClass, String propertyName) throws PropertyNotFoundException {
		BasicGetter result = getGetterOrNull(theClass, propertyName);
		if (result==null) throw new PropertyNotFoundException( "Could not find a getter for " + propertyName + " in class " + theClass.getName() );
		return result;

	}

}
