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

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        /*
         * Utils.writeStringToFile("junit_example_file_data.txt", "test file contents");
         * Utils.deleteFile("index");
         * Utils.deleteDirectory("objects");
         */

        File f = new File ("./objects");
        f.mkdirs(); 
        f = new File ("./index");
        f.createNewFile();

        Utils.writeToFile("lol\nlollol", "./file1.txt");
        Utils.writeToFile("lol\nlollol\nlol", "./file2.txt");
    }

    @Test
    void testAdd() throws Exception {
        Tree tree = new Tree();

        tree.add("blob : f3ff62c55a22ff8a20567318e316ef9da8b01b98 : file1.txt");
        tree.add("blob : f3ff62c55a22ff8a20567318e316ef9da8b01b98 : file1.txt");
        tree.add("blob : 9c33b7f16cdaee196a2079256ccf381b09b69c7a : file2.txt");

        tree.save();

        String treeContents = Utils.readFromFile("./objects/" + tree.getHashcode()); 

        assertTrue("Tree was properly added", treeContents.equals("blob : f3ff62c55a22ff8a20567318e316ef9da8b01b98 : file1.txt\nblob : 9c33b7f16cdaee196a2079256ccf381b09b69c7a : file2.txt"));
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

        tree.add("lol");

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

        t.save();
        String contents = Utils.readFromFile("./objects/" + t.getHashcode());

        assertTrue ("tree is missing expected files", contents.contains("./test1/examplefile1.txt"));
        assertTrue ("tree did not hash correctly", contents.contains("6cecd98f685b1c9bfce96f2bbf3f8f381bcc717e"));
    }

    @Test
    void testAddDirectoryAdvanced () throws Exception
    {
        //create all the stuff that we need
        File subDir = new File("./folder");
        subDir.mkdirs();
        subDir = new File ("./folder/test2");
        subDir.mkdirs();
        subDir = new File ("./folder/test3");
        subDir.mkdirs();

        Utils.writeToFile("the sha of this is ... ?", "./folder/examplefile1.txt");
        Utils.writeToFile("zomg wut are u doing. LAWL", "./folder/examplefile2.txt");
        Utils.writeToFile("LOL please dont read this.  Good job being thorough tho!", "./folder/examplefile3.txt");

        Utils.writeToFile("ribbit", "./folder/test3/asd.txt");

        Tree t = new Tree();
        t.addDirectory("./folder");

        t.save();
        String contents = Utils.readFromFile("./objects/" + t.getHashcode());

        assertTrue ("tree does not contain files from first folder", contents.contains("./folder/examplefile1.txt"));
        assertTrue ("tree does not contain files from the sub directories", contents.contains ("tree : 06275a0db4c2fea08154ec7aeca605ca861c535f : ./folder/test3"));
    } 
}
