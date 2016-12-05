package org.rageco.test.util;


import java.io.FileInputStream;
import java.io.IOException;

import java.util.Properties;


/**
 * ReadProperties represents a class that load the properties :)
 * 
 * @author Hector Mendoza
 * @version $Id$
 * @since Jul 23, 2014
 * 
 */
public class ReadProperties
{
   private static ReadProperties instance;
   private Properties            props    = null;
   private final String          fileName = "properties.properties";


   private ReadProperties ()
   {
      // nothing to do here
   }


   /**
    * Represents instance
    * 
    * @return instance
    * @since Jul 23, 2014
    * 
    */
   public static ReadProperties instance ()
   {
      if (instance == null)
      {
         instance = new ReadProperties ();
      }
      return instance;
   }


   /**
    * Check if the properties is already loaded
    * */
   private void checkConfigure () throws IOException
   {
      if (props == null)
      {
         props = new Properties ();
         props.load (new FileInputStream (fileName));
      }
   }


   /**
    * Represents readServer
    * 
    * @return string
    * @throws IOException
    * @since Jul 23, 2014
    * 
    */
   public String readServer () throws IOException
   {
      checkConfigure ();
      return String.valueOf (props.get ("server"));
   }


   /**
    * Represents readJndiName
    * 
    * @return string
    * @throws IOException
    * @since Jul 23, 2014
    * 
    */
   public String readJndiName () throws IOException
   {
      checkConfigure ();
      return String.valueOf (props.get ("jndi"));
   }


   /**
    * Represents readInitialContext
    * 
    * @return string
    * @throws IOException
    * @since Jul 29, 2014
    * 
    */
   public String readInitialContext () throws IOException
   {
      checkConfigure ();
      return String.valueOf (props.get ("initial.context.factory"));
   }


   /**
    * Represents readPkgPrefix
    * 
    * @return string
    * @throws IOException
    * @since Jul 29, 2014
    * 
    */
   public String readPkgPrefix () throws IOException
   {
      checkConfigure ();
      return String.valueOf (props.get ("url.pkg.prefixes"));
   }
}
