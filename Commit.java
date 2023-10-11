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
    String lastCommit;
    String nextCommit;
    String tree;

    DateTimeFormatter dateTimeFormatter;

    public Commit(String summary, String author, String lastCommit) throws Exception {

        this.summary = summary;
        this.author = author;
        this.lastCommit = lastCommit;
        this.nextCommit = "";

        createTree();
        this.date = getDate();

    }

    public Commit(String summary, String author) throws Exception {

        this.summary = summary;
        this.author = author;
        this.nextCommit = "";

        createTree();
        this.date = getDate();

    }

    //this now needs to just read everything from Index
    private void createTree () throws Exception
    {
        ArrayList<String> indexContents = Utils.readFromFileToArrayList("./index");

        Tree currentIndexTree = new Tree();

        for (String s : indexContents)
        {
            String type = Utils.getFirstWordOfString(s);
            if (type.equals("blob")) currentIndexTree.add(s);
            else currentIndexTree.addDirectory( Utils.getLastWordOfString(s) ); //are we meant to use addDirectory or just add here?
        }

        currentIndexTree.add("tree : " + lastCommit);
        currentIndexTree.save(); 
        tree = currentIndexTree.getHashcode(); 
    }

    public String getDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(calendar.getTime());
    }

    public String writeToObjects() throws Exception {

        StringBuilder stringBuilder = new StringBuilder(tree + "\n" + lastCommit + "\n" + author + "\n" + date + "\n" + summary);
        String hash = Utils.getHashFromString(stringBuilder.toString());
        stringBuilder.insert(stringBuilder.indexOf("\n", stringBuilder.indexOf("\n") + 1), nextCommit + "\n");

        File file = new File("objects/" + hash);
        if (!file.exists())
            file.createNewFile();

        FileWriter fileWriter = new FileWriter("objects/" + hash, false);
        fileWriter.write(stringBuilder.toString());
        fileWriter.close();

        return hash; 
    }

    public String generateSha() throws Exception {
        String fileContents = tree + "\n" + lastCommit + "\n" + nextCommit + "\n" + author + "\n" + date + "\n" + summary;
        return Utils.getHashFromString(fileContents);
    }
}
