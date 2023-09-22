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

        File objects = new File("objects");
        if (!objects.exists()) {
            objects.mkdir();
        }

        File index = new File("index");
        if (!index.exists()) {
            index.createNewFile();
        }
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

        Commit commit = new Commit("summary", "ryan", "lastcommit");
        String treeSha = commit.createTree();

        assertEquals("wrong sha created for tree", "da39a3ee5e6b4b0d3255bfef95601890afd80709", treeSha);
        File treeFile = new File("./objects/da39a3ee5e6b4b0d3255bfef95601890afd80709");
        assertTrue("tree file was not created", treeFile.exists());
    }

    @Test
    @DisplayName("Test get date")
    void testGetDate() throws Exception {
        Commit commit = new Commit("summary", "ryan", "lastcommit");

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        assertEquals("commit class generating date wrong", commit.getDate(),
                simpleDateFormat.format(calendar.getTime()));
    }

    @Test
    @DisplayName("Test that write to objects works, which means that generate sha works")
    void testWriteToObjects() throws Exception {
        Commit commit = new Commit("summary", "ryan", "c22b5f9178342609428d6f51b2c5af4c0bde6a42");

        commit.writeToObjects();

        // Confirm sure the hash of the file created is correct
        File file = new File("objects/b42de14e4a34f084f69dcbfe29b266b10fe10719");
        assertTrue(file.exists());

        // Confirm the object file contents match what is expected
        assertEquals("da39a3ee5e6b4b0d3255bfef95601890afd80709\n" + "c22b5f9178342609428d6f51b2c5af4c0bde6a42\n" + "\n" + "ryan\n" + "2023-09-22\n" + "summary", Files.readString(Path.of("objects/b42de14e4a34f084f69dcbfe29b266b10fe10719")));
    }

}
