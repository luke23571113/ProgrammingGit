import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Scanner;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CommitTester {

    @BeforeAll
    static void setup() throws Exception {

        File dir = new File("./objects");
        dir.mkdir();

        dir = new File ("./folder1");
        dir.mkdirs(); 
        dir = new File ("./folder2");
        dir.mkdirs(); 

        File index = new File("./index");
        index.createNewFile();

        Utils.writeToFile("hello", "./file1.txt"); 
        Utils.writeToFile("i love git", "./file2.txt");
        Utils.writeToFile("i hate git", "./file3.txt");
        Utils.writeToFile("coding is so fun", "./file4.txt");

        Utils.writeToFile("blue", "./folder1/file5.txt");
        Utils.writeToFile("red", "./folder1/file6.txt");

        Utils.writeToFile("purple", "./folder2file7.txt");
        Utils.writeToFile("turtle", "./folder2/file8.txt");
    }

    @AfterAll
    static void delete() throws Exception {

        File objectsFolder = new File("objects/");
        String[] entries = objectsFolder.list();
        for (String s : entries) {
            File currentFile = new File(objectsFolder.getPath(), s);
            currentFile.delete();
        }
        Files.delete(Paths.get("objects"));

        File index = new File("index");
        if (index.exists()) {
            index.delete();
        }

    }

    @Test
    @DisplayName("Test create tree")
    void testCreateTree() throws Exception {

        Commit commit = new Commit("summary", "ryan");
        String treeSha = commit.createTree();

        assertEquals("wrong sha created for tree", "da39a3ee5e6b4b0d3255bfef95601890afd80709", treeSha);
        File treeFile = new File("./objects/da39a3ee5e6b4b0d3255bfef95601890afd80709");
        assertTrue("tree file was not created", treeFile.exists());
    }

    @Test
    @DisplayName("Test get date")
    void testGetDate() throws Exception {
        Commit commit = new Commit("summary", "ryan");

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        assertEquals("commit class generating date wrong", commit.getDate(),
                simpleDateFormat.format(calendar.getTime()));
    }

    @Test
    void testOneCommit() throws Exception {
        Index i1 = new Index();
        i1.init(); 
        i1.add("file1.txt");
        i1.add("file2.txt");

        Commit c1 = new Commit ("summary", "luke");

        assertTrue("Incorrect hash for the commit's tree", c1.tree.equals("f21c3d366bbb5153c4dd35b30ed8ba28bf746817"));
        assertTrue("Incorrect hash for the previous commit", c1.lastCommit.equals(""));
        assertTrue("Incorrect hash for the next commit", c1.nextCommit.equals(""));
    }

    // @Test
    // @DisplayName("Test that write to objects works, which means that generate sha works")
    // void testWriteToObjects() throws Exception {
    //     Commit commit = new Commit("summary", "ryan", "c22b5f9178342609428d6f51b2c5af4c0bde6a42");

    //     commit.writeToObjects();

    //     // Confirm sure the hash of the file created is correct
    //     File file = new File("./objects/" + commit.getHashcode());
    //     assertTrue(file.exists());

    //     DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
    //     LocalDateTime now = LocalDateTime.now();  
    //     String curDate = dtf.format(now);

    //     // Confirm the object file contents match what is expected
    //     assertEquals("da39a3ee5e6b4b0d3255bfef95601890afd80709\n" + "c22b5f9178342609428d6f51b2c5af4c0bde6a42\n" + "\n" + "ryan\n" + curDate + "\nsummary", Files.readString(Path.of("./objects/" + commit.getHashcode())));
    // }

}
