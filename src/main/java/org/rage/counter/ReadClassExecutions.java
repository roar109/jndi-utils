package org.rage.counter;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/**
 * ReadClassExecutions represents ...
 * 
 * @author Hector Mendoza
 * @version $Id$
 * @since Sep 12, 2014
 * 
 */
public class ReadClassExecutions
{
   private final String className;


   /**
    * Represents main
    * 
    * @param args
    * @since Sep 11, 2014
    * 
    */
   public static void main (final String[] args)
   {
      // TODO filename
      final ReadClassExecutions s = new ReadClassExecutions (args[0]);
      if (args.length > 1)
      {
         for (int i = 1; i < args.length; i++)
         {
            s.findInLogs (args[i]);
         }
      }
   }


   /**
    * Represents findInLogs
    * 
    * @param methodName
    * @since Sep 12, 2014
    * 
    */
   public void findInLogs (final String methodName)
   {
      int totalCount = 0;
      int count = 0;
      long max = 0;
      long min = 120l;
      long sum = 0;

      // sequentially read lines from log file
      try
      {
         final BufferedReader br = new BufferedReader (new FileReader ("C:\\server.log.2014-09-10"));
         String line = br.readLine ();
         System.out.println (this.className + "." + methodName);
         while (line != null)
         {
            if (line.contains (className + "." + methodName))
            {
               totalCount++;
               // calculate time to complete
               // if > 120 seconds
               final long time = calculateTimeToComplete (line, methodName);
               if (time > 120l)
               {
                  count++;
               }
               sum += time;
               if (time > max)
               {
                  max = time;
               }

               if (time < min)
               {
                  min = time;
               }
            }
            line = br.readLine ();
         }

         System.out.printf (" %d Calls\n", totalCount);

         System.out.println (" Max time was: " + max + " secs");
         System.out.println (" Min time was: " + min + " secs");
         if (count > 0)
         {
            System.out.printf (" %d times took more than 120 seconds\n", count);
         }

         System.out.println (" Average time was: " + ( (totalCount != 0) ? (sum / totalCount) : 0) + " secs\n\n");
      }
      catch (final IOException ioe)
      {
         // do nothing
         ioe.printStackTrace ();
      }
   }


   private static long calculateTimeToComplete (final String line, final String methodName)
   {
      final String aux = line.substring (line.lastIndexOf (methodName), line.length ());
      Long result = 0l;

      final String[] time = aux.split (",");

      if ( (time != null) && (time.length > 2))
      {
         result = new Long (time[1].trim ()) / 1000l;

      }
      return result;
   }


   /**
    * Constructs an instance of ReadClassExecutions object.
    * 
    * @param value
    */
   public ReadClassExecutions (final String value)
   {
      this.className = value;
   }
}
