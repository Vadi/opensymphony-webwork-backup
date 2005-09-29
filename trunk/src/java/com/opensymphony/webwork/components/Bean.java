package com.opensymphony.webwork.components;

import com.opensymphony.util.ClassLoaderUtil;
import com.opensymphony.xwork.ObjectFactory;
import com.opensymphony.xwork.util.OgnlUtil;
import com.opensymphony.xwork.util.OgnlValueStack;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Writer;

/**
 * <p>Instantiates a class that conforms to the JavaBeans specification. This tag has a body which can contain
 * a number of {@link Param} elements to set any mutator methods on that class.</p>
 * <p/>
 * <p>If the id attribute is set on the BeanTag, it will place the instantiated bean into the PageContext and the
 * ActionContext.</p>
 * <p/>
 * <p>Examples:</p>
 * <p/>
 * <pre>
 * [ww.bean name="com.opensymphony.webwork.example.counter.SimpleCounter" id="counter"]
 *   [ww:param name="foo" value="BAR"/]
 *   The value of foo is : [ww:property value="foo"/], when inside the bean tag.<br />
 * [/ww:bean]
 * </pre>
 * <p/>
 * <p>This example instantiates a bean called SimpleCounter and sets the foo property (setFoo('BAR')). The
 * SimpleCounter object is then pushed onto the Valuestack, which means that we can called its accessor methods (getFoo())
 * with the Property tag and get their values.</p>
 * <p/>
 * <p>In the above example, the id has been set to a value of <i>counter</i>. This means that the SimpleCounter class
 * will be placed into the PageContext and ActionContext. You can access the SimpleCounter class using JSTL:</p>
 * <p/>
 * <pre>
 * [c:out value="${counter.foo}"/]
 * </pre>
 * <p/>
 * <p> or using the webwork Property tag:</p>
 * <p/>
 * <pre>
 * [ww:property value="#counter.foo"/]
 * </pre>
 * <p/>
 * <p>In the property tag example, the <i>#</i> tells Ognl to search the context for the SimpleCounter class which has
 * an id(key) of <i>counter</i></p>
 *
 * @author $author$
 * @author Rick Salsa (rsal@mb.sympatico.ca)
 * @author Brock Bulger
 * @version $Revision$
 */
public class Bean extends Component {
    protected static Log log = LogFactory.getLog(Bean.class);

    protected Object bean;
    protected String name;

    public Bean(OgnlValueStack stack) {
        super(stack);
    }

    public void start(Writer writer) {
        super.start(writer);

        try {
            String beanName = findString(name, "name", "Bean name is required. Example: com.acme.FooBean");
            bean = ObjectFactory.getObjectFactory().buildBean(ClassLoaderUtil.loadClass(beanName, getClass()));
        } catch (Exception e) {
            log.error("Could not instantiate bean", e);

            return;
        }

        OgnlValueStack stack = getStack();

        // push bean on stack
        stack.push(bean);

        // store for reference later
        if (getId() != null) {
            getStack().getContext().put(getId(), bean);
        }

    }

    public void end(Writer writer, String body) {
        OgnlValueStack stack = getStack();
        stack.pop();

        super.end(writer, body);
    }

    public void addParameter(String key, Object value) {
        OgnlUtil.setProperty(key, value, bean, getStack().getContext());
    }

    public void setName(String name) {
        this.name = name;
    }
}
