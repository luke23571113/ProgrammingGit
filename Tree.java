import java.io.*;
import java.util.*;

public class Tree {
    private ArrayList<String> values;
    private String hashcode = "";
    // private String directoryPath; 

    public Tree() {
        values = new ArrayList<>();
    }

    public void add(String fullLine) throws Exception {
        if (!(values.contains(fullLine))) {
            values.add(fullLine);
        }
    }

    public void remove(String input) throws Exception {
        for (int i = values.size() - 1; i >= 0; i--) {
            if (values.get(i).contains(input))
            {
                values.remove(i);
            }
        }
    }

    public void save() throws Exception {
        String treeFileContent = Utils.arrayListToFileText(values);

        hashcode = Utils.getHashFromString(treeFileContent); 

        // write contents to a file
        Utils.writeToFile(treeFileContent, "./objects/" + hashcode); 
    }

    public void addDirectory (String directory) throws Exception
    {
        File dir = new File (directory);
        if (!dir.isDirectory())
        {
            throw new Exception ("Invalid directory path");
        }

        File[] fileList = dir.listFiles();  //list of all the files

        for (File f : fileList) //loop through each file and add tree
        {
            if (f.isFile ())
            {
                String filePath = directory + "/" + f.getName();
                Blob b = new Blob (filePath); //are you meant to make the files a blob? ig it can't hurt ...

                add("blob : " + b.getHashcode() + " : " + filePath);
            }
            else if (f.isDirectory())
            { 
                //need to recurse 
                Tree subTree = new Tree();
                String tempPath = f.getPath();
                subTree.addDirectory(tempPath);
                subTree.save();

                add("tree : " + subTree.getHashcode() + " : " + tempPath);
            }
        }
        
        save(); 
    }

    public String getHashcode ()
    {
        return hashcode;
    }
}
