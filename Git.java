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

    public void add (String file) throws Exception
    {
        try
        {
            Blob blob = new Blob("file");

            hashCodes.put(file, blob.getHashcode());
            updateIndex();
        }
        catch (Exception e)
        {
            throw e; 
        }
    }

    public void remove (String file) throws Exception
    {
        try
        {
            //first check if the file is even added
            if (!hashCodes.containsKey(file)) throw new Exception ("Error: File not found");

            //remove it from the hashmap
            hashCodes.remove(file);
            updateIndex(); //update the index file

            //nb: do not delete the file from objects
        }
        catch (Exception e)
        {
            throw e;
        }
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
