import java.io.File;
import java.nio.BufferOverflowException;
import java.util.*; 
import java.io.*; 

public class Git {
    
    private HashMap<String, String> hashCodes;

    public Git ()
    {
        hashCodes = new HashMap<String, String>();
    }    

    public void init ()
    {
        try
        {
            //make the objects folder if it doesn't exist
            File objects = new File ("./objects");
            objects.mkdirs();

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void add (String file)
    {

    }

    public void remove (String file)
    {

    }

    //method that will fill index with all the entries in hashCodes
    private void updateIndex ()
    {
        hashCodes.put("a", "b");
        hashCodes.put("b", "c");
        try
        {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("index")));

            for (String key : hashCodes.keySet())
            {
                String hash = hashCodes.get(key);
                pw.println(key + " : " + hash);
            }

            pw.close();
        }
        catch (Exception e)
        {
            System.out.println("Exception : " + e);
        }
    }
}
