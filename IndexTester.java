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

        file1 = new File("./file1.txt");
        file2 = new File("./file2.txt");
        FileWriter writer = new FileWriter(file1);
        writer.write("lol\nlollol");
        writer.flush();
        writer.close();
        FileWriter writer2 = new FileWriter(file2);
        writer2.write("lol\nlollol\nlol");
        writer2.flush();
        writer2.close();
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
        index.add("file1.txt");

        // check if the file exists
        File file = new File("./objects/f3ff62c55a22ff8a20567318e316ef9da8b01b98");

        assertTrue("File with hash name is in objects", file.exists());

        File indexFile = new File("index");

        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(indexFile));

        while (br.ready()) {
            sb.append ((char) br.read());
        }
        br.close();

        assertTrue("Index was properly updated", sb.toString().equals("file1.txt : f3ff62c55a22ff8a20567318e316ef9da8b01b98\n"));
    }

    @Test
    @DisplayName("[8] Test if initialize and objects are created correctly")
    void testRemoveIndex() throws Exception {

        // Run the person's code
        // TestHelper.runTestSuiteMethods("testInitialize");

        index.add("file1.txt");
        index.add("file2.txt");
        index.remove("file2.txt");

        // check if the file exists
        File fileT1 = new File("./objects/f3ff62c55a22ff8a20567318e316ef9da8b01b98");
        File fileT2 = new File("./objects/9c33b7f16cdaee196a2079256ccf381b09b69c7a");

        File indexFile = new File("./index");

        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(indexFile));

        while (br.ready()) {
            sb.append ((char) br.read());
        }
        br.close();

        assertTrue("Index was updated properly", sb.toString().equals("file1.txt : f3ff62c55a22ff8a20567318e316ef9da8b01b98\n"));
    }
}
