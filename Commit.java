import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

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

        tree = createTree();
        this.date = getDate();

    }

    public Commit(String summary, String author) throws Exception {

        this.summary = summary;
        this.author = author;
        this.nextCommit = "";

        tree = createTree();
        this.date = getDate();

    }

    public static String createTree() throws Exception {
        Tree tree = new Tree();
        return tree.save();
    }

    public String getDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(calendar.getTime());
    }

    public void writeToObjects() throws Exception {

        //whatup luke
        StringBuilder stringBuilder = new StringBuilder(tree + "\n" + lastCommit + "\n" + author + "\n" + date + "\n" + summary);
        String hash = Blob.getHashFromString(stringBuilder.toString());
        stringBuilder.insert(stringBuilder.indexOf("\n", stringBuilder.indexOf("\n") + 1), nextCommit + "\n");

        File file = new File("objects/" + hash);
        if (!file.exists())
            file.createNewFile();

        FileWriter fileWriter = new FileWriter("objects/" + hash, false);
        fileWriter.write(stringBuilder.toString());
        fileWriter.close();

    }

    public String generateSha() throws Exception {
        String fileContents = tree + "\n" + lastCommit + "\n" + nextCommit + "\n" + author + "\n" + date + "\n" + summary;
        return Blob.getHashFromString(fileContents);
    }
}
