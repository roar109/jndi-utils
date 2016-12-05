package org.rageco.test;

import org.rageco.test.util.ReadProperties;
import org.rageco.test.util.ValidateProperties;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


/**
 * JNDICheck
 * 
 * @author Hector Mendoza
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
         final String jndi = properties.readJndiName ();
         final String server = properties.readServer ();

         // Simple validations
         ValidateProperties.validateServerAndJndi (server, jndi);

         // Create a Properties object and set properties appropriately
         final Properties props = new Properties ();
         props.put (Context.INITIAL_CONTEXT_FACTORY, properties.readInitialContext ());
         props.put (Context.URL_PKG_PREFIXES, properties.readPkgPrefix ());
         props.put (Context.PROVIDER_URL, server);

         // Create the initial context from the properties we just created
         final Context initialContext = new InitialContext (props);

         // Look up the object
         final String[] definitions = ValidateProperties.splitJndis (jndi);
         for (final String jndiDefinition : definitions)
         {
            searchAndPrintJndi (initialContext, jndiDefinition);
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


   /**
    * Do the look up of the jndi definition and print its value
    * */
   private static void searchAndPrintJndi (final Context initialContext, final String jndi)
   {
      Object obj;
      try
      {
         obj = initialContext.lookup (jndi);
         if (obj != null)
         {
            System.out.println ("JNDI Name:\t" + jndi);
            System.out.println ("Value:\t\t" + obj + "\n");
         }
         else
         {
            System.out.println ("Null Value");
         }
      }
      catch (final javax.naming.NameNotFoundException nnf)
      {
         System.err.println ("JNDI definition '" + jndi + "' not found.\n");
      }
      catch (final Exception e)
      {
         System.err.println (e.getMessage ());
         e.printStackTrace ();
      }
   }
}
