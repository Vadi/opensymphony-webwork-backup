/*
 * WebWork, Web Application Framework
 *
 * Distributable under Apache license.
 * See terms of license at opensource.org
 */
package webwork.action;

import java.util.Locale;

/**
 *	All Actions that want to have access to the users chosen locale
 * must implement this interface.
 *
 *	@author Rickard Öberg (rickard@middleware-company.com)
 *	@version $Revision$
 *
 * @deprecated Use {@link ActionContext#getLocale()} instead.
 */
public interface LocaleAware
{
   // Public --------------------------------------------------------
   public void setLocale(Locale aLocale);
}
