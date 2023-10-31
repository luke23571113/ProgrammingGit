import java.io.File;
import java.util.*; 
import java.io.*; 

public class Index {
    private static ArrayList<String> entries;

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
        entries = new ArrayList<String>();
        updateIndex();
    }

    public void add (String fileName) throws Exception
    {
        Blob b = new Blob(fileName);

        String entry = "blob : " + b.getHashcode() + " : " + fileName;

        if (!entries.contains(entry)) entries.add(entry);

        updateIndex();
    }

    public void addDirectory (String folderName) throws Exception
    {
        Tree t = new Tree ();
        t.addDirectory(folderName);

        String entry = "tree : " + t.getHashcode() + " : " + folderName;

        if (!entries.contains(entry)) entries.add(entry);
        updateIndex(); 
    }

    public void delete (String fileName) throws Exception
    {
        String entry = "*deleted* " + fileName;
        if (!entries.contains(entry)) entries.add(entry);
        updateIndex(); 
    }

    public void edit (String fileName) throws Exception
    {
        String entry = "*edited* " + fileName;
        if (!entries.contains(entry)) entries.add(entry);
        updateIndex(); 
    }

    public static void resetIndexFile () throws Exception
    {
        Utils.writeToFile("", "./index");
        entries = new ArrayList<String>(); 
    }

    //method that will fill index with all the entries in hashCodes
    private void updateIndex () throws Exception
    {
        String s = Utils.arrayListToFileText(entries);

        Utils.writeToFile(s.toString(), "./index");
    }
}
