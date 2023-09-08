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

    public void init () throws Exception
    {
        try
        {
            //will make the objects folder if doesn't exist already 
            Blob blob = new Blob("./");

            //will create an index file
            updateIndex();
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void add (String file)
    {

    }

    public void remove (String file)
    {

    }

    //method that will fill index with all the entries in hashCodes
    private void updateIndex () throws Exception
    {
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
            throw e;
        }
    }
}
