/*
 * Copyright 2000 - 2008 24 Hour Fitness. All rights reserved.
 */
package org.rageco.test.util;


import java.io.IOException;

import java.util.Properties;


/**
 * ReadProperties represents a class that load the properties :)
 * 
 * @author <a href="mailto:roar109@gmail.com">Hector Mendoza</a>
 * @version $Id$
 * @since Jul 23, 2014
 * 
 */
public class ReadProperties
{
   private static ReadProperties instance;
   private Properties            props = null;


   private ReadProperties ()
   {
      // Nothing here
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
         props.load (this.getClass ().getResourceAsStream ("/properties.properties"));
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
}
