import java.io.File;
import java.util.*; 
import java.io.*; 

public class Index {
    private ArrayList<String> entries;

    public Index ()
    {
        entries = new ArrayList<String>(); 
    }    

    public void init () throws Exception
    {
        File objects = new File ("./objects");
        objects.mkdirs();
        File index = new File("./index");
        index.createNewFile();

        //will create an index file
        updateIndex();
    }

    public void add (Tree tree) throws Exception
    {
        String hashcode = tree.getHashcode(); 
        String folderName = tree.getDiretoryPath();
        entries.add("tree : " + hashcode + " : " + folderName);
        updateIndex();
    }

    public void add (Blob b ) throws Exception
    {
        String hashCode = b.getHashcode();
        String fileName = b.getFile();
        entries.add("blob : " + hashCode + " : " + fileName);
    }

    public void remove (String file) throws Exception
    {
        //remove from entries
        boolean contains = false; 
        for (int i = entries.size() - 1; i >= 0; i--)
        {
            if (entries.get(i).contains(file)) 
            {
                entries.remove(i);
            }
        }

        //check if it exists
        if (!contains)
        {
            throw new Exception ("File does not exist in index");
        }

        //update the actual file
        updateIndex(); 
    }

    //method that will fill index with all the entries in hashCodes
    private void updateIndex () throws Exception
    {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < entries.size(); i++)
        {
            if (i == entries.size() - 1) sb.append(entries.get(i) + "\n");
            else sb.append (entries.get(i));
        }

        Utils.writeToFile(sb.toString(), "./index");
    }
}
