/*
 * WebWork, Web Application Framework
 *
 * Distributable under Apache license.
 * See terms of license at opensource.org
 */
package webwork.util;

/**
 *	A bean that can be used to time execution of pages
 *      
 *	@author Rickard Öberg (rickard@middleware-company.com)
 *	@version $Revision$
 */
public class Timer
{
   // Attributes ----------------------------------------------------
   long current = System.currentTimeMillis();
   long start = current;
   
   // Public --------------------------------------------------------
   
   public long getTime()
   {
      // Return how long time has passed since last check point
      long now = System.currentTimeMillis();
      long time = now - current;
      
      // Reset so that next time we get from this point
      current = now;
      return time;
   }

   public long getTotal()
   {
      // Reset start so that next time we get from this point
      return System.currentTimeMillis() - start;
   }
}
