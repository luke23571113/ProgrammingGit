import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
        LocalDate localDate = LocalDate.now();
        return dateTimeFormatter.format(localDate);
    }
}
