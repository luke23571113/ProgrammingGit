import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class IndexTester {

    static File file1;
    static File file2;
    static Index index;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        /*
         * Utils.writeStringToFile("junit_example_file_data.txt", "test file contents");
         * Utils.deleteFile("index");
         * Utils.deleteDirectory("objects");
         */

        Utils.writeToFile("lol\nlollol","./file1.txt");
        Utils.writeToFile("lol\nlollol\nlol","./file2.txt");

        index = new Index();
        index.init();
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
        /*
         * Utils.deleteFile("junit_example_file_data.txt");
         * Utils.deleteFile("index");
         * Utils.deleteDirectory("objects");
         */
    }

    @Test
    @DisplayName("[8] Test if initialize and objects are created correctly")
    void testInitIndex() throws Exception {

        // Run the person's code
        // TestHelper.runTestSuiteMethods("testInitialize");

        // check if the file exists
        File file = new File("index");
        Path path = Paths.get("objects");

        assertTrue("Index exists", file.exists());
        assertTrue("Object exists", Files.exists(path));
    }

    @Test
    @DisplayName("[8] Test if initialize and objects are created correctly")
    void testAddIndex() throws Exception {

        // Run the person's code
        // TestHelper.runTestSuiteMethods("testInitialize");

        index.add("file1.txt");
        index.add("file2.txt");

        // check if the file exists
        File file = new File("./objects/f3ff62c55a22ff8a20567318e316ef9da8b01b98");

        assertTrue("File with hash name is in objects", file.exists());

        //index is empty for some reason 
        String indexContent = Utils.readFromFile("./index");

        String firstLine = indexContent.substring(0, indexContent.indexOf("\n"));

        assertTrue("Index was not properly updated", firstLine.equals("blob : f3ff62c55a22ff8a20567318e316ef9da8b01b98 : file1.txt"));
    }

    @Test
    @DisplayName("[8] Test if initialize and objects are created correctly")
    void testRemoveIndex() throws Exception {

        // Run the person's code
        // TestHelper.runTestSuiteMethods("testInitialize");

        index.add("file1.txt");
        index.add("file2.txt");
        index.remove("file2.txt");

        String indexContents = Utils.readFromFile("./index");

        assertTrue("Index was not updated properly", indexContents.equals("blob : f3ff62c55a22ff8a20567318e316ef9da8b01b98 : file1.txt"));
    }
}
