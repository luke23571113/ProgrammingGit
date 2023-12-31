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
        String treeFileContent = Utils.arrayListToFileText(values);

        hashcode = Utils.getHashFromString(treeFileContent); 

        // write contents to a file
        Utils.writeToFile(treeFileContent, "./objects/" + hashcode); 
    }


    public static String addDirectory (String directory) 
    {
        try
        {
            File dir = new File (directory);

            if (!dir.exists())
            {
                throw new Exception ("directory doesn't exist");
            }

            Tree dirTree = new Tree(); 

            File[] fileList = dir.listFiles(); 

            for (File f : fileList)
            {
                if (f.isFile())
                {
                    String filePath = directory + "/" + f.getName(); 
                    Blob b = new Blob(filePath);

                    dirTree.add("blob : " + b.getHashcode() + " : " + filePath);
                }
                else if (f.isDirectory())
                {
                    String tempPath = f.getPath(); 
                    String subTreeHashCode = Tree.addDirectory(tempPath); 
                    dirTree.add("tree : " + subTreeHashCode + " : " + tempPath);
                }
            }

            dirTree.save(); 

            return dirTree.getHashcode();
        }
        catch(Exception e)
        {
            return "";
        }
    }

    /* STUFF FOR A  */

    public void deletePreviousFile (String fileToDelete, String previousCommitSHA) throws Exception
    {
        String commitSHA = getCommitWhichContainsFile(fileToDelete, previousCommitSHA);


        ArrayList<String> commitContent = Utils.readFromFileToArrayList("./objects/" + commitSHA);

        //since we already copied over all of the old files, we just need to link to the past tree

        //remove the old link to the past commit ... 
        for (int i = values.size() -1 ; i >= 0; i--)
        {
            String[] split = values.get(i).split(" : ");
            if (split.length == 2) 
            {
                values.remove(i);
            }
        }

        if (!commitContent.get(1).equals(""))
        {
            ArrayList<String> pastCommitContent = Utils.readFromFileToArrayList("./objects/" + commitContent.get(1));

            add ("tree : " + pastCommitContent.get(0)); //link to the commit before the one with the file we are removing
        }
    }

    public void editPreviousFile (String fileToEdit, String previousCommitSHA) throws Exception 
    { 
        String commitSHA = getCommitWhichContainsFile(fileToEdit, previousCommitSHA);
        ArrayList<String> commitContent = Utils.readFromFileToArrayList("./objects/" + commitSHA);

        //remove the old link to the past commit ... 
        for (int i = values.size() -1 ; i >= 0; i--)
        {
            String[] split = values.get(i).split(" : ");
            if (split.length == 2) 
            {
                values.remove(i);
            }
        }

        if (!commitContent.get(1).equals(""))
        {
            ArrayList<String> pastCommitContent = Utils.readFromFileToArrayList("./objects/" + commitContent.get(1));

            add ("tree : " + pastCommitContent.get(0)); //link to the commit before the one with the file we are removing
        }

        //now need to add the updated file 
        Blob b = new Blob(fileToEdit);

        add ("blob : " + b.getHashcode() + " : " + fileToEdit);
    }

    private String getCommitWhichContainsFile (String fileToGet, String previousCommitSHA) throws Exception
    {
        ArrayList<String> commitContent = Utils.readFromFileToArrayList("./objects/" + previousCommitSHA);

        ArrayList<String> treeContent = Utils.readFromFileToArrayList("./objects/" + commitContent.get(0));

        //boolean is needed so that we can add all the previous files to the current tree
        boolean isInCurrentTree = false;  

        for (String s : treeContent)
        {
            if (Utils.getFirstWordOfString(s).equals("blob"))
            {
                if (Utils.getLastWordOfString(s).equals(fileToGet))
                {
                    isInCurrentTree = true; 
                }
                else
                {
                    add(s); //all all the previous files besides from the one we want to remove to the current tree
                }
            }
        }

        if (isInCurrentTree)
        {
            return previousCommitSHA; 
        }


        if (commitContent.get(1).equals(""))
        {
            throw new Exception("reached the end of the tree without finding the file");
        }

        return getCommitWhichContainsFile(fileToGet, commitContent.get(1));
    }

    public String getHashcode ()
    {
        return hashcode;
    }
}
