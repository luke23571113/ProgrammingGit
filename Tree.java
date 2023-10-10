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

    public void add(String input) {
        try {
            if (!(values.contains(input))) {
                values.add(input);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void remove(String input) {
        try {
            for (int i = values.size() - 1; i >= 0; i--) {
                if (values.get(i).contains(input))
                {
                    values.remove(i);
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public String save() throws Exception {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < values.size(); i++) {
            if (i < values.size() - 1) {
                sb.append(values.get(i) + "\n");
            } else {
                sb.append(values.get(i));
            }
        }

        //hash file contents 
        hashcode = Utils.getHashFromString(sb.toString());

        //safe to file
        File newFile = new File("./objects/" + hashcode);
        newFile.createNewFile();
        FileWriter writer = new FileWriter(newFile);
        writer.write(sb.toString());
        writer.flush();
        writer.close();

        return hashcode;
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
                String hash = subTree.save();

                add("tree : " + hash + " : " + tempPath);
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
