/*
 * WebWork, Web Application Framework
 *
 * Distributable under Apache license.
 * See terms of license at opensource.org
 */
package webwork.action.factory;

import webwork.action.Action;

/**
 * Default implementation of an action factory facade that creates the default
 * set of action factory proxies.  This class delegates to the proxies as
 * implementation, thus acting as a facade to them.
 *
 * <p>The order in which factory proxies are executed is as follows:</p>
 * <ol>
 *   <li>{@link ContextActionFactoryProxy} -
 *       Establishes the action's context by executing methods for all
 *       implemented <code>Aware*</code> interfaces.</li>
 *   <li>{@link CommandActionFactoryProxy} -
 *       Executes a specified "command" method if the action implements
 *       the {@link webwork.action.CommandDriven} interface.</li>
 *   <li>{@link AliasingActionFactoryProxy} -
 *       Locates an action from the configuration substituting the alias
 *       with the associated action string.</li>
 *   <li>{@link CommandActionFactoryProxy} -
 *       Executes a specified "command" method again in the event the
 *       original action name was originally an alias.</li>
 *   <li>{@link JspActionFactoryProxy} -
 *       Returns the JSP action if its suffix is ".jsp".</li>
 *   <li>{@link PrefixActionFactoryProxy} -
 *       Returns an action using a configured list of packages to prefix
 *       its name.</li>
 *   <li>{@link XMLActionFactoryProxy} -
 *       Returns the XML action if its suffix is ".xml".</li>
 *   <li>{@link ScriptActionFactoryProxy} -
 *       Returns the Script action if its suffix matches that of a supported
 *       scripting language.</li>
 *   <li>{@link JavaActionFactory} -
 *       Returns an action object from a fully qualified classname.</li>
 * </ol>
 *
 * @author Rickard Öberg (rickard@middleware-company.com)
 * @version $Revision$
 */
public class MigrationActionFactory
   extends ActionFactory
{
   // Attributes ----------------------------------------------------
   protected ActionFactory factory;

   /**
    * Initialize action factory proxy delegation chain.
    */
   public MigrationActionFactory()
   {
        factory = new JavaActionFactory();
        factory = new ScriptActionFactoryProxy(factory);
        factory = new XMLActionFactoryProxy(factory);
        factory = new PrefixActionFactoryProxy(factory);
        factory = new JspActionFactoryProxy(factory);
        factory = new CommandActionFactoryProxy(factory);
        factory = new AliasingActionFactoryProxy(factory);
        factory = new CommandActionFactoryProxy(factory);
        factory = new ContextActionFactoryProxy(factory);
      
   }

   /**
    * Get an action object. The name should be the fully qualified classname of
    * the action. Returns an instance of the matching action class by
    * searching through the action factory proxy delegation chain.
    *
    * @param   name classname of the action to be created
    * @return   the action corresponding to the given name
    * @exception   Exception
    */
   public Action getActionImpl(String name)
     throws Exception
   {
      return factory.getActionImpl(name);
   }
   
}
