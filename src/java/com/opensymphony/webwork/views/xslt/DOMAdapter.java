package com.opensymphony.webwork.views.xslt;

import org.w3c.dom.Node;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

/**
 * @author <a href="mailto:meier@meisterbohne.de">Philipp Meier</a>
 * Date: 10.10.2003
 * Time: 19:39:13
 */
public class DOMAdapter {

    public AdapterNode adapt(Object value) throws IllegalAccessException, InstantiationException {
        return new DocumentAdapter(this, null, "result", value);
    }

    public String getPropertyName() {
        return "[Root]";
    }

    public short getNodeType() {
        return Node.PROCESSING_INSTRUCTION_NODE; // What to return, is DOMAdapter a Node at last?
    }

    public AdapterNode adapt(DOMAdapter rootAdapter, Node parent, String propertyName, Object value) {
        Class klass = value.getClass();
        Class adapterClass = findAdapterForClass(klass);

        try {
            if (adapterClass == null) {
                if (klass.isArray()) {
                    adapterClass = ArrayAdapter.class;
                } else if (String.class.isAssignableFrom(klass) || klass.isPrimitive() ||
                           Number.class.isAssignableFrom(klass)) {
                    adapterClass = ToStringAdapter.class;
                } else if (Collection.class.isAssignableFrom(klass)) {
                    adapterClass = CollectionAdapter.class;
                } else {
                    adapterClass = BeanAdapter.class;
                }
            }

            Constructor c = adapterClass.getConstructor(
                    new Class[]{DOMAdapter.class, AdapterNode.class, String.class, Object.class});
            AdapterNode adapter = ((AdapterNode) c.newInstance(new Object[]{this, parent, propertyName, value}));

            return adapter;
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot adapt " + value + " (" + propertyName + ")", e);
        } catch (InstantiationException e) {
            throw new RuntimeException("Cannot adapt " + value + " (" + propertyName + ")", e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(
                    "Adapter Class " + adapterClass.getName() + " must define the right constructor.", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Cannot adapt " + value + " (" + propertyName + ")", e);
        }
    }

    //TODO: implement Configuration option to provide additional adapter classes
    private Class findAdapterForClass(Class klass) {

        return null;
    }

    public AdapterNode adaptNullValue(DOMAdapter rootAdapter, BeanAdapter parent, String propertyName) {
        return new ToStringAdapter(rootAdapter, parent, propertyName, "null");
    }

}
