import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.*; 

public class Commit {

    String summary;
    String author;
    String date;
    String lastCommit = "";
    String nextCommit = "";
    String tree;

    DateTimeFormatter dateTimeFormatter;

    private String hashcode; 

    public Commit(String summary, String author, String lastCommit) throws Exception {

        this.summary = summary;
        this.author = author;
        this.lastCommit = lastCommit;
        this.nextCommit = "";

        createTree();
        this.date = getDate();
        hashcode = ""; 
        writeToObjects();
        
    }

    public Commit(String summary, String author) throws Exception {

        this.summary = summary;
        this.author = author;
        this.lastCommit = "";
        this.nextCommit = "";

        //TODO: need to clear index
        //TODO: there is some weird case that I am failing, where Commit doesn't work when I only add a directory?

        createTree();
        this.date = getDate();
        hashcode = ""; 
        writeToObjects();

    }

    //this now needs to just read everything from Index
    private String createTree () throws Exception
    {
        ArrayList<String> indexContents = Utils.readFromFileToArrayList("./index");

        Tree currentIndexTree = new Tree();

        for (String s : indexContents)
        {
            String type = Utils.getFirstWordOfString(s);
            if (type.equals("blob")) currentIndexTree.add(s);
            else if (type.equals("tree"))currentIndexTree.addDirectory( Utils.getLastWordOfString(s) ); //are we meant to use addDirectory or just add here?
            else if (type.equals("*deleted*")) 
            {
                currentIndexTree.
            }
            else if (type.equals("*edited*")) 
            {

            }
        }

        if (!Utils.readFromFile("./index").equals(""))
        {
            System.out.println();
        }

        if (!lastCommit.equals(""))
        {
            currentIndexTree.add("tree : " + getTreeFromCommit(lastCommit));
        }
        currentIndexTree.save(); 
        tree = currentIndexTree.getHashcode(); 
        return currentIndexTree.getHashcode();
    }

    public static String getTreeFromCommit (String curCommit) throws Exception
    {
        File f = new File ("./objects/" + curCommit);
        if (curCommit == "" || !f.exists()) throw new Exception ("commit doesn't exist");

        return Utils.readLineFromFile("./objects/" + curCommit, 0);
    }

    public String getDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(calendar.getTime());
    }

    public void writeToObjects() throws Exception {

        StringBuilder stringBuilder = new StringBuilder(tree + "\n" + lastCommit + "\n" + author + "\n" + date + "\n" + summary);
        hashcode = Utils.getHashFromString(stringBuilder.toString());
        stringBuilder.insert(stringBuilder.indexOf("\n", stringBuilder.indexOf("\n") + 1), nextCommit + "\n");

        Utils.writeToFile (stringBuilder.toString(), "./objects/" + hashcode);

        //UPDATE THE LAST COMMIT TO INCLUDE THIS COMMIT
        
        if (!lastCommit.equals(""))
        {
            Utils.replaceLineInFile( "./objects/" + lastCommit, 2, hashcode);
        }
    }

    public String getHashcode ()
    {
        return hashcode; 
    }
}
