import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BlobTester {
    static File file1;
    static File file2;

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
        writer.write("hello");
        writer.flush();
        writer.close();
        FileWriter writer2 = new FileWriter(file2);
        writer2.write("lol\nlollol\nlol");
        writer2.flush();
        writer2.close();
    }

    @Test
    @DisplayName("Test Get Hashcode")
    void getHashcode() throws Exception {
        Blob x = new Blob("file1.txt");
        
        StringBuilder sb = new StringBuilder();

        sb.append(x.getHashcode());

        assertTrue("Correct sha1 is returned", sb.toString().equals("aaf4c61ddcc5e8a2dabede0f3b482cd9aea9434d"));
    }

    @Test
    @DisplayName("Test Create Blob")
    void createBlob() throws Exception {
        Blob x = new Blob("file1.txt");
        
        File temp = new File("./objects/aaf4c61ddcc5e8a2dabede0f3b482cd9aea9434d");

        assertTrue ("Blob file exists", temp.isFile());

        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(temp));

        while (br.ready()) {
            sb.append ((char) br.read());
        }
        br.close();

        assertTrue("Blob file was written correctly", sb.toString().equals("hello"));
    }
}
