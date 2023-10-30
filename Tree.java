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

    public void remove (String input) throws Exception {
        for (int i = values.size() - 1; i >= 0; i--) {
            if (values.get(i).contains(input))
            {
                values.remove(i);
            }
        }
    }

    public void save() throws Exception {
        //handle all of the removes first
        for (int i = values.size() - 1; i >= 0; i--)
        {
            String curEntry = values.get(i);
            String firstWord = Utils.getFirstWordOfString(curEntry);
            if (firstWord.equals("*deleted*")) deletePreviousFile(Utils.getLastWordOfString(curEntry));
            else if (firstWord.equals("*edited*")) editPreviousFile(Utils.getLastWordOfString(curEntry));
            

        }
        
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

    /* STUFF FOR A  */

    private void deletePreviousFile (String fileToDelete) throws Exception
    {
        //FIXME: upon deletion, hashcode won't be known ... 
        String treeToSearch = getTreeWhichContainsFile(fileToDelete, hashcode); //so i guess this only works if the tree is saved first?

        ArrayList<String> treeContents = Utils.readFromFileToArrayList("./objects/" + treeToSearch);

        //copy over all the contents except for the file we want to delete
        for (String s : treeContents)
        {
            String fileName = Utils.getLastWordOfString(s);
            if (!fileName.equals(fileToDelete))
            {
                values.add(s);
            }
        }
    }

    private void editPreviousFile (String fileToEdit)
    {

    }

    private String getTreeWhichContainsFile (String fileToGet, String curTree) throws Exception
    {
        // base case : current tree contains the file we're looking for
        ArrayList<String> blobList = getBlobList(); 

        for (String s : blobList)
        {
            String fileName = Utils.getLastWordOfString(s);
            if (fileToGet.equals(fileName)) return curTree;
        }

        ArrayList<String> treeList = getTreeList();
        for (String s : treeList)
        {
            String treeName = Utils.getSHAofLine(s); 
            return getTreeWhichContainsFile(fileToGet, treeName);
        }

        throw new Exception ("could not find a tree that contains the requested file");
    }


    private ArrayList<String> getBlobList ()
    {
        ArrayList<String> list = new ArrayList<String>(); 
        for (String s : values)
        {
            if (Utils.getFirstWordOfString(s).equals("blob")) list.add(s);
        }
        return list;
    }

    private ArrayList<String> getTreeList ()
    {
        ArrayList<String> list = new ArrayList<String>(); 
        for (String s : values)
        {
            if (Utils.getFirstWordOfString(s).equals("tree")) list.add(s);
        }
        return list;
    }



    public String getHashcode ()
    {
        return hashcode;
    }
}
