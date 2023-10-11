import java.io.File;
import java.io.FileWriter;
import java.security.MessageDigest;
import java.util.ArrayList;

public class Tree {
    private ArrayList<String> values;
    private String hashcode = "";
    private String directoryPath; 

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
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < values.size(); i++) {
            sb.append(values.get(i));
        }
        if (values.size() > 0) sb.deleteCharAt(sb.length() - 1);

        //hash file contents 
        hashcode = Utils.getHashFromString(sb.toString());

        // write contents to a file
        Utils.writeToFile("./objects/" + hashcode, sb.toString());
    }

    public void addDirectory (String directory) throws Exception
    {
        directoryPath = directory; 
        File dir = new File (this.directoryPath);
        if (!dir.isDirectory())
        {
            throw new Exception ("Invalid directory path");
        }

        File[] fileList = dir.listFiles();  //list of all the files

        for (File f : fileList) //loop through each file and add tree
        {
            if (f.isFile ())
            {
                String filePath = directoryPath + "/" + f.getName();
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

                add("tree : " + hashcode + " : " + tempPath);
            }
        }
    }

    public String getHashcode ()
    {
        return hashcode;
    }

    public String getDiretoryPath ()
    {
        return directoryPath; 
    }
}
