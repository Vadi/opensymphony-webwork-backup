package webwork;

import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.xwork.config.ConfigurationException;
import com.opensymphony.xwork.config.ConfigurationManager;
import com.opensymphony.xwork.config.RuntimeConfiguration;
import com.opensymphony.xwork.config.impl.DefaultConfiguration;
import com.opensymphony.xwork.config.providers.XmlConfigurationProvider;
import webwork.dispatcher.ViewMapping;

/**
 * webwork.MigrationConfiguration
 *
 * @author Jason Carreira
 *         Date: Nov 6, 2003 12:28:04 AM
 */
public class MigrationConfiguration extends DefaultConfiguration {
    ViewMapping mapping;

    public MigrationConfiguration() {
        Configuration.setConfiguration(new webwork.config.DefaultConfiguration());
        ConfigurationManager.addConfigurationProvider(new XmlConfigurationProvider("webwork/webwork-migration.xml"));
    }

    protected synchronized RuntimeConfiguration buildRuntimeConfiguration() throws ConfigurationException {
        return new MigrationRuntimeConfiguration(this, super.buildRuntimeConfiguration());
    }
}
