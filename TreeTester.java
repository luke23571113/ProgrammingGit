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

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*; 

public class TreeTester {
    
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

    @Test
    void testAdd() throws Exception {
        Tree tree = new Tree();

        tree.add("blob : f3ff62c55a22ff8a20567318e316ef9da8b01b98 : file1.txt");
        tree.add("blob : f3ff62c55a22ff8a20567318e316ef9da8b01b98 : file1.txt");
        tree.add("blob : 9c33b7f16cdaee196a2079256ccf381b09b69c7a : file2.txt");

        tree.save();

        File temp = new File("./objects/4b016e0b9d977b1b2626cefedad981b761cba2ae");

        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(temp));

        while (br.ready()) {
            sb.append ((char) br.read());
        }
        br.close();

        assertTrue("Tree was properly added", sb.toString().equals("blob : f3ff62c55a22ff8a20567318e316ef9da8b01b98 : file1.txt\nblob : 9c33b7f16cdaee196a2079256ccf381b09b69c7a : file2.txt"));
    }

    @Test
    void testRemove() throws Exception {
        Tree tree = new Tree();

        tree.add("blob : f3ff62c55a22ff8a20567318e316ef9da8b01b98 : file1.txt");
        tree.add("blob : 9c33b7f16cdaee196a2079256ccf381b09b69c7a : file2.txt");
        tree.remove("file2.txt");

        tree.save();

        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader("./objects/d5fada904500b4bb41775f04d9dc4ca01a2596b1"));

        while (br.ready()) {
            sb.append ((char) br.read());
        }
        br.close();

        assertTrue("Tree properly removed", sb.toString().equals("blob : f3ff62c55a22ff8a20567318e316ef9da8b01b98 : file1.txt"));
    }

    @Test
    void testSave() throws Exception {
        Tree tree = new Tree();

        tree.values.add("lol");

        tree.save();

        Path path = Paths.get("objects/403926033d001b5279df37cbbe5287b7c7c267fa");

        assertTrue("Tree save addes file to Tree", Files.exists(path));
    }

    // @Test
    // void testAddDirectory () throws Exception
    // {
    //     //first create some files that we can use
    //     FileWriter fw = new FileWriter("examplefile1.txt");
    //     fw.write("the sha of this is ... ?");
    //     fw.close();
    //     fw = new FileWriter("examplefile2.txt");
    //     fw.write("zomg wut are u doing. LAWL");
    //     fw.close(); 
    //     fw = new FileWriter("examplefile3.txt");
    //     fw.write("LOL please dont read this.  Good job being thorough tho!");
    //     fw.close(); 

    //     Tree t = new Tree();
    //     t.addDirectory("./");

    //     String hash = t.save();
    //     String contents = Utils.readFromFile("./objects/" + hash);

    //     assertTrue ("tree is missing expected files", contents.contains("objects/examplefile1.txt"));
    // }
}
