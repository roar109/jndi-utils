package org.rage.generator;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;


/**
 * LoremIpsum4J generates Lorem Ipsum paragraphs and words. This class uses lorem ipsum web service in www.lipsum.com.
 * 
 * <pre>
 * Copyright (C) 2010 Softabar
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see &lt;http://www.gnu.org/licenses/>.
 * </pre>
 * 
 * @version 1.3.0
 * 
 */
public class LoremIpsum4J
{
   public static final String           version             = "1.3.0";

   private boolean                      startWithLoremIpsum = true;

   private final String                 loremIpsumUrl       = "http://www.lipsum.com/feed/xml";

   private int                          generatedParagraphs = 0;

   private int                          generatedWords      = 0;

   private int                          generatedBytes      = 0;

   private boolean                      cached              = false;

   private final Map <String, String[]> loremIpsumCache     = new Hashtable <String, String[]> ();


   /**
    * Get Lorem Ipsum paragraphs.
    * 
    * @param numberOfParagraphs Number of lorem ipsum paragraphs.
    * @return Array of lorem ipsum paragraphs.
    */
   public String[] getParagraphs (final int numberOfParagraphs)
   {
      return getLoremIpsum ("paras", numberOfParagraphs);
   }


   /**
    * Get Lorem Ipsum words.
    * 
    * @param numberOfWords Number of lorem ipsum words.
    * @return Array of lorem ipsum paragraphs. Total word count equals parameter numberOfParagraphs.
    */
   public String[] getWords (final int numberOfWords)
   {
      return getLoremIpsum ("words", numberOfWords);
   }


   /**
    * Get Lorem Ipsum bytes.
    * 
    * @param numberOfBytes Number of lorem ipsum bytes.
    * @return Array of lorem ipsum paragraphs that contain lorem ipsum words.
    */
   public String[] getBytes (final int numberOfBytes)
   {
      return getLoremIpsum ("bytes", numberOfBytes);
   }


   private String[] getLoremIpsum (final String type, final int numberOf)
   {
      String[] paragraphs = new String[0];
      if (cached)
      {
         // return from cache
         paragraphs = loremIpsumCache.get (type + numberOf);
         if (paragraphs != null)
         {
            return paragraphs;
         }
      }

      try
      {
         final String url = getUrl (type, numberOf);
         final String loremIpsumFragment = getLoremIpsumString (url);
         paragraphs = getParagraphs (loremIpsumFragment);
         if (cached)
         {
            loremIpsumCache.put (type + numberOf, paragraphs);
         }
      }
      catch (final IOException e)
      {
         e.printStackTrace ();
         return paragraphs;
      }
      return paragraphs;
   }


   private String getUrl (final String type, final int numberOf)
   {
      final String start = startWithLoremIpsum ? "yes" : "no";
      final StringBuffer sb = new StringBuffer (loremIpsumUrl);
      sb.append ("?amount=");
      sb.append (numberOf);
      sb.append ("&what=");
      sb.append (type);
      sb.append ("&start=");
      sb.append (start);
      return sb.toString ();
   }


   private String[] getParagraphs (final String loremIpsumFragment)
   {
      String[] paragraphs = new String[0];
      final Vector <String> vr = new Vector <String> ();
      final BufferedReader br = new BufferedReader (new InputStreamReader (new ByteArrayInputStream (loremIpsumFragment
            .getBytes ())));

      try
      {
         for (String line = br.readLine (); line != null; line = br.readLine ())
         {
            vr.add (line);
         }
      }
      catch (final IOException e)
      {
         e.printStackTrace ();
      }

      paragraphs = new String[vr.size ()];
      vr.copyInto (paragraphs);
      return paragraphs;
   }


   private String getLoremIpsumString (final String loremIpsumUrlLocal) throws IOException
   {
      // TODO Remove proxy if is needed
      final Proxy proxy = new Proxy (Proxy.Type.HTTP, new InetSocketAddress ("192.168.64.252", 3128));
      final URL url = new URL (loremIpsumUrlLocal);
      final URLConnection urlcon = url.openConnection (proxy);
      final BufferedInputStream bis = new BufferedInputStream (urlcon.getInputStream ());

      final StringBuffer content = new StringBuffer ();
      for (int b = bis.read (); b > -1; b = bis.read ())
      {
         content.append ((char) b);
      }
      bis.close ();
      final String loremIpsumXML = content.toString ();

      final String loremIpsumFragment = getTag ("lipsum", loremIpsumXML);

      // get generated bytes, paragraphs and words
      final String generated = getTag ("generated", loremIpsumXML);
      final String[] genBytes = generated.split (" ");
      int k = 0;
      for (int i = 0; i < genBytes.length; i++)
      {
         try
         {
            final int value = Integer.parseInt (genBytes[i]);
            switch (k)
            {
               case 0 :
                  generatedParagraphs = value;
                  k++;
                  break;
               case 1 :
                  generatedWords = value;
                  k++;
                  break;
               case 2 :
                  generatedBytes = value;
                  k++;
                  break;
            }
         }
         catch (final NumberFormatException nfe)
         {
            // ignore non-numbers
         }
      }

      return loremIpsumFragment;
   }


   private String getTag (final String tagName, final String loremIpsumXML)
   {
      final String tagStart = "<" + tagName + ">";
      final String tagEnd = "</" + tagName + ">";
      final int tagStartIndex = loremIpsumXML.indexOf (tagStart) + tagStart.length ();
      final int tagEndIndex = loremIpsumXML.indexOf (tagEnd, tagStartIndex);
      final String text = loremIpsumXML.substring (tagStartIndex, tagEndIndex);
      return text;

   }


   private void print (final String[] loremIpsum)
   {
      for (int i = 0; i < loremIpsum.length; i++)
      {
         System.out.println (loremIpsum[i]);
         System.out.println ();
      }

   }


   /**
    * Return current setting of start with "lorem ipsum" option.
    * 
    * @return Current setting of "lorem ipsum" option.
    */
   public boolean isStartWithLoremIpsum ()
   {
      return startWithLoremIpsum;
   }


   /**
    * Set option to start with classic "Lorem ipsum". Default is true.
    * 
    * @param startWithLoremIpsum Set start with "lorem ipsum".
    */
   public void setStartWithLoremIpsum (final boolean value)
   {
      this.startWithLoremIpsum = value;
   }


   public static void main (final String[] args)
   {
      final LoremIpsum4J loremIpsum = new LoremIpsum4J ();

      System.out.println ("LoremIpsum4J v" + version + " Copyright (C) 2006, 2010 Softabar");
      System.out.println ("www.softabar.com & www.lipsum.com");

      System.out.println ();
      try
      {
         final Properties options = loremIpsum.cmdLineOptions (args);
         if (options.get ("help") != null)
         {
            loremIpsum.usage ();
            return;
         }
         loremIpsum.setStartWithLoremIpsum (Boolean.parseBoolean (options.getProperty ("start")));

         final String[] _loremIpsum = loremIpsum.getLoremIpsum (options.getProperty ("type"), Integer.parseInt (options
               .getProperty ("amount")));
         loremIpsum.print (_loremIpsum);
      }
      catch (final Exception e)
      {
         System.out.println (e.toString ());
         System.out.println ();
         loremIpsum.usage ();
      }

   }


   private void usage ()
   {
      System.out.println ("Usage: java " + getClass ().getName ()
            + " [-type <paras | words | bytes>] [-amount number] [-start <true | false>]");
      System.out.println ("Default type is paras, default amount is 5 and default start with lorem ipsum is true.");

   }


   private Properties cmdLineOptions (final String[] args)
   {
      final Properties props = new Properties ();
      props.put ("type", "paras");
      props.put ("amount", "5");
      props.put ("start", "true");
      for (int i = 0; i < args.length; i++)
      {
         final String arg = args[i];
         if (arg.equals ("-type"))
         {
            props.put ("type", args[i + 1]);
         }
         if (arg.equals ("-amount"))
         {
            props.put ("amount", args[i + 1]);
         }
         if (arg.equals ("-start"))
         {
            props.put ("start", args[i + 1]);
         }

         if (arg.equals ("-h") || arg.equals ("--help"))
         {
            props.put ("help", "showHelp");
         }

      }

      return props;
   }


   /**
    * Return number of bytes generated in the last call to getParagraphs(), getBytes() or getWords() method.
    * 
    * @return Number of generated bytes.
    */
   public int getGeneratedBytes ()
   {
      return generatedBytes;
   }


   /**
    * Return number of paragraphs generated in the last call to getParagraphs(), getBytes() or getWords() method.
    * 
    * @return Number of generated paragraphs.
    */
   public int getGeneratedParagraphs ()
   {
      return generatedParagraphs;
   }


   /**
    * Return number of words generated in the last call to getParagraphs(), getBytes() or getWords() method.
    * 
    * @return Number of generated words.
    */

   public int getGeneratedWords ()
   {
      return generatedWords;
   }


   /**
    * Return cache status.
    * 
    * @return True if cache is enabled.
    */
   public boolean isCached ()
   {
      return cached;
   }


   /**
    * Enable/disable cache. Lorem Ipsum paragraphs are cached based on request lorem ipsum (words, paragraphs, bytes)
    * and number of requested lorem ipsum.
    * 
    * @param cached Set cache status.
    */
   public void setCached (final boolean value)
   {
      this.cached = value;
   }

}
