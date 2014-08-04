package org.rageco.test.util;


import org.apache.commons.lang3.StringUtils;


/**
 * ValidateProperties represents some simple tests
 * 
 * @author <a href="mailto:roar109@gmail.com">Hector Mendoza</a>
 * @version $Id$
 * @since Jul 30, 2014
 * 
 */
public final class ValidateProperties
{

   /**
    * Represents validateServerAndJndi
    * 
    * @param server
    * @param jndi
    * @throws IllegalArgumentException
    * @since Jul 30, 2014
    * 
    */
   public static void validateServerAndJndi (final String server, final String jndi) throws IllegalArgumentException
   {
      if (StringUtils.isBlank (jndi) && StringUtils.isBlank (server))
      {
         throw new IllegalArgumentException ("JNDI and Server properties must be a valid strings");
      }
   }


   /**
    * Represents splitJndis
    * 
    * @param jndiProperty
    * @return String[]
    * @since Aug 4, 2014
    * 
    */
   public static String[] splitJndis (final String jndiProperty)
   {
      return StringUtils.split (jndiProperty, ",");
   }
}
