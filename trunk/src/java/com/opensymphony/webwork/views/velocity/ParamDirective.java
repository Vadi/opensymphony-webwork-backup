/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.velocity;

import com.opensymphony.webwork.views.jsp.ParamTag;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.util.OgnlValueStack;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;

import java.io.IOException;
import java.io.Writer;


/**
 * <p/>
 * The ParamDirective allows for additional parameters to be passed in to a given tag before the tag is rendered.  By
 * default these parameters are not evaluated through the value stack.  However, the user can specify using a third
 * parameter that they are evaluated.
 * </p>
 * <p/>
 * For example:
 * </p>
 * <pre>
 *      #param( hello $world ) - adds the velocity resolved value of world to the tag
 *      #param( hello world true) - adds the ognl resolved value of world to the tag
 *      #param( hello world false) - adds the string world to the tag
 * </pre>
 *
 * @author Matt Ho <a href="mailto:matt@enginegreen.com">&lt;matt@enginegreen.com&gt;</a>
 * @deprecated Automatic JSP tag support doesn't work well and is likely to break. Please use the native Velocity
 *             tags introduced in WebWork 2.2
 */
public class ParamDirective extends Directive {
    public String getName() {
        return "param";
    }

    public int getType() {
        return LINE;
    }

    /**
     * @param contextAdapter
     * @param writer
     * @param node
     * @throws java.io.IOException
     * @throws org.apache.velocity.exception.ResourceNotFoundException
     *
     * @throws org.apache.velocity.exception.ParseErrorException
     *
     * @throws org.apache.velocity.exception.MethodInvocationException
     *
     */
    public boolean render(InternalContextAdapter contextAdapter, Writer writer, Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
        Object object = contextAdapter.get(VelocityManager.TAG);

        //Fixed for Param -- Added by Henry Hu
        /*
         * Param Usage Example:
         * <p>Custom Component Example:</p> 
         	#wwcomponent ("template=components/datefield.vm")
         		#param ("label" "Date") 
         		#param ("name" "mydatefield") 
         		#param ("mysize" 3) 
           #end
           <br/> 
           
           datefield.vm:
           #set ($name = $stack.getContext().get("name"))
           #set ($label = $stack.getContext().get("label")) 
           #set ($size = $stack.getContext().get("mysize")) 
           #set ($yearsize = $size * 2)
			$label:
			<input type="text" name="${name}.day" size="$size" /> / 
			<input type="text" name="${name}.month" size="$size" /> / 
			<input type="text" name="${name}.year" size="$yearsize" /> (dd/mm/yyyy)
           **/

        OgnlValueStack stack = ActionContext.getContext().getValueStack();
        stack.getContext().put(node.jjtGetChild(0).value(contextAdapter).toString(), node.jjtGetChild(1).value(contextAdapter));

        if ((object != null) && (object instanceof ParamTag.Parametric)) {
            if ((node.jjtGetNumChildren() != 2) && (node.jjtGetNumChildren() != 3)) {
                throw new ParseErrorException("#param directive requires two parameters, a key and a value.  an optional flag to evaluate it may be included.");
            }

            Object key = node.jjtGetChild(0).value(contextAdapter);
            Object value = node.jjtGetChild(1).value(contextAdapter);

            // if there are ever 3 params, the we should look up the value against the value stack
            if ((node.jjtGetNumChildren() == 3) && "TRUE".equalsIgnoreCase(node.jjtGetChild(2).value(contextAdapter).toString())) {
                OgnlValueStack valueStack = ActionContext.getContext().getValueStack();
                value = valueStack.findValue(value.toString());
            }

            ParamTag.Parametric parameterizedTag = (ParamTag.Parametric) object;

            if (key != null) {
                parameterizedTag.addParameter(key.toString(), value);
            }

            return true;
        } else {
            return false;
        }
    }
}
