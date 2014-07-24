/*
 * Copyright 2000 - 2008 24 Hour Fitness. All rights reserved.
 */
package org.rageco.test;


import org.rageco.test.util.ReadProperties;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


/**
 * JNDICheck
 * 
 * @author <a href="mailto:roar109@gmail.com">Hector Mendoza</a>
 * @version $Id$
 * @since Jul 23, 2014
 * 
 */
public class JNDICheck
{
   /**
    * Represents main
    * 
    * @param args
    * @since Feb 5, 2014
    * 
    */
   public static void main (final String[] args)
   {
      try
      {
         final ReadProperties properties = ReadProperties.instance ();
         final String name = properties.readJndiName ();

         // Create a Properties object and set properties appropriately
         final Properties props = new Properties ();
         props.put (Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
         props.put (Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
         props.put (Context.PROVIDER_URL, properties.readServer ());

         // Create the initial context from the properties we just created
         final Context initialContext = new InitialContext (props);

         // Look up the object
         final Object obj = initialContext.lookup (name);
         if (name.equals (""))
         {
            System.out.println ("Looked up the initial context");
         }
         else
         {
            System.out.println ("JNDI Name:\t" + name);
            System.out.println ("Value:\t\t" + obj);
         }
      }
      catch (final NamingException nnfe)
      {
         System.err.println (nnfe.getMessage ());
         nnfe.printStackTrace ();
      }
      catch (final Exception e)
      {
         System.err.println (e.getMessage ());
         e.printStackTrace ();
      }
   }
}
