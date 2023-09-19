import java.io.File;
import java.util.*; 
import java.io.*; 

public class Index {
    
    private HashMap<String, String> hashCodes;

    public Index ()
    {
        hashCodes = new HashMap<String, String>();
    }    

    public void init () throws Exception
    {
        try
        {
            File objects = new File ("./objects");
            objects.mkdirs();
            File index = new File("./index");
            index.createNewFile();

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
            Blob blob = new Blob(file);

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
            StringBuilder sb = new StringBuilder();
            File indexFile = new File("./index");
            for (Map.Entry<String,String> mapElement : hashCodes.entrySet()) {
                String key = mapElement.getKey();
                String lock = mapElement.getValue();
                sb.append(key + " : " + lock + "\n");
            }
            FileWriter writer = new FileWriter(indexFile);
            writer.write(sb.toString());
            writer.flush();
            writer.close();
        }
        catch (Exception e)
        {
            throw e;
        }
    }
}
