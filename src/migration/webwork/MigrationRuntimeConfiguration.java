package webwork;

import com.opensymphony.xwork.config.ConfigurationException;
import com.opensymphony.xwork.config.RuntimeConfiguration;
import com.opensymphony.xwork.config.entities.ActionConfig;
import com.opensymphony.xwork.config.entities.PackageConfig;
import com.opensymphony.xwork.config.entities.InterceptorStackConfig;
import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlRuntime;
import webwork.action.Action;
import webwork.action.CommandDriven;
import webwork.action.factory.*;
import webwork.config.Configuration;
import webwork.dispatcher.DefaultViewMapping;
import webwork.dispatcher.ViewMapping;

import java.beans.Beans;
import java.util.*;

/**
 * MigrationRuntimeConfiguration
 * @author Jason Carreira
 * Date: Nov 6, 2003 1:11:58 AM
 */
class MigrationRuntimeConfiguration implements RuntimeConfiguration {
    private static final String MIGRATION_PACKAGE = "webwork-migration";
    private RuntimeConfiguration runtime;
    private MigrationConfiguration configuration;
    private ActionFactory factory;
    private ViewMapping mapping;

    public MigrationRuntimeConfiguration(MigrationConfiguration migrationConfiguration, RuntimeConfiguration configuration) {
        this.configuration = migrationConfiguration;
        this.runtime = configuration;

        factory = new JavaActionFactory();
        factory = new ScriptActionFactoryProxy(factory);
        factory = new XMLActionFactoryProxy(factory);
        factory = new PrefixActionFactoryProxy(factory);
        factory = new JspActionFactoryProxy(factory);
        factory = new CommandActionFactoryProxy(factory);
        factory = new AliasingActionFactoryProxy(factory);
        factory = new CommandActionFactoryProxy(factory);
        factory = new ContextActionFactoryProxy(factory);

        mapping = buildViewMapping();
    }

    private ViewMapping buildViewMapping() {
        // Choose classloader
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        // Initialize view mapping
        String mappingName;
        try {
            mappingName = Configuration.getString("webwork.viewmapping");
        } catch (IllegalArgumentException e) {
            // Default
            mappingName = DefaultViewMapping.class.getName();
        }

        try {
            return (ViewMapping) Beans.instantiate(classLoader, mappingName);
        } catch (Throwable t) {
            throw new ConfigurationException("Could not create ViewMapping object of class: " + mappingName, t);
        }
    }

    public ActionConfig getActionConfig(String namespace, String name) {
        ActionConfig config = runtime.getActionConfig(namespace, name);
        if (config == null) {
            PackageConfig packageConfig = configuration.getPackageConfig(MIGRATION_PACKAGE);
            if (packageConfig == null) {
                throw new ConfigurationException("Unable to find the " + MIGRATION_PACKAGE + " package which is defined in the webwork-migration.xml.");
            }
            config = buildMigratedConfig(name, packageConfig);
            if (config != null) {
                packageConfig.addActionConfig(name, config);
            }
        }
        return config;
    }

    private ActionConfig buildMigratedConfig(String name, PackageConfig packageConfig) {
        ActionConfig actionConfig = null;
        try {
            Action action = factory.getActionImpl(name);
            if (action == null) {
                return null;
            }
            Map params = buildObjectParams(action);
            Map results = new LazyResultMap(name, mapping);
            String method = null;
            if ((action instanceof CommandDriven) && (params.containsKey("command"))) {
                StringBuffer sb = new StringBuffer("do");
                sb.append(params.get("command"));
                sb.setCharAt(2, Character.toUpperCase(sb.charAt(2)));
                method = sb.toString();
            }
            actionConfig = new ActionConfig(method, action.getClass(), params, results, null);
            String defaultInterceptorRef = packageConfig.getDefaultInterceptorRef();
            InterceptorStackConfig interceptors = (InterceptorStackConfig) packageConfig.getInterceptorConfigs().get(defaultInterceptorRef);
            actionConfig.addInterceptors((List)interceptors.getInterceptors());
        } catch (Exception e) {
            throw new ConfigurationException("Unable to create a migrated Action with name " + name, e);
        }
        return actionConfig;
    }

    private Map buildObjectParams(Object obj) {
        OgnlContext context = (OgnlContext) Ognl.createDefaultContext(obj);
        Set fieldNames = OgnlRuntime.getFields(obj.getClass()).keySet();
        Map params = new HashMap();
        for (Iterator iterator = fieldNames.iterator(); iterator.hasNext();) {
            String fieldName = iterator.next().toString();
            Object value = null;
            try {
                value = OgnlRuntime.getFieldValue(context, obj, fieldName);
            } catch (NoSuchFieldException e) {
                //ignore
            }
            if (value != null) {
                params.put(fieldName, value);
            }
        }

        return params;
    }

    public Map getActionConfigs() {
        return runtime.getActionConfigs();
    }

}
