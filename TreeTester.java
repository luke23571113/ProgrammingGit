import static org.junit.Assert.*;

import java.io.*; 
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

    @Test
    void testAddDirectory () throws Exception
    {
        //first create some files that we can use
        File subDir = new File("./test1");
        subDir.mkdirs();

        FileWriter fw = new FileWriter("./test1/examplefile1.txt");
        fw.write("the sha of this is ... ?");
        fw.close();
        fw = new FileWriter("./test1/examplefile2.txt");
        fw.write("zomg wut are u doing. LAWL");
        fw.close(); 
        fw = new FileWriter("./test1/examplefile3.txt");
        fw.write("LOL please dont read this.  Good job being thorough tho!");
        fw.close(); 

        Tree t = new Tree();
        t.addDirectory("./test1");

        String hash = t.save();
        String contents = Utils.readFromFile("./objects/" + hash);

        assertTrue ("tree is missing expected files", contents.contains("./test1/examplefile1.txt"));
        assertTrue ("tree did not hash correctly", contents.contains("6cecd98f685b1c9bfce96f2bbf3f8f381bcc717e"));
    }

    @Test
    void testAddDirectoryAdvanced () throws Exception
    {
        //create all the stuff that we need
        File subDir = new File("./folder");
        subDir.mkdir();
        subDir = new File ("./folder/test2");
        subDir.mkdir();
        subDir = new File ("./folder/test3");
        subDir.mkdir();

        FileWriter fw = new FileWriter("./folder/examplefile1.txt");
        fw.write("the sha of this is ... ?");
        fw.close();
        fw = new FileWriter("./folder/examplefile2.txt");
        fw.write("zomg wut are u doing. LAWL");
        fw.close(); 
        fw = new FileWriter("./folder/examplefile3.txt");
        fw.write("LOL please dont read this.  Good job being thorough tho!");
        fw.close();

        fw = new FileWriter ("./folder/test3/asd.txt");
        fw.write("ribbit");
        fw.close();

        Tree t = new Tree();
        t.addDirectory("./folder");

        String hash = t.save();
        String contents = Utils.readFromFile("./objects/" + hash);

        assertTrue ("tree does not contain files from first folder", contents.contains("./folder/examplefile1.txt"));
        assertTrue ("tree does not contain files from the sub directories", contents.contains ("tree : 96d7cb7cc8438a6836c7693266fcb886c84b53fa : ./folder/test3"));
        //this creates some weird extra file somewhere, can't exactly figure out what's going on ... 
    }
}
