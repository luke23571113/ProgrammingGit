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

        //first create some files that we can use
        File subDir = new File("./test1");
        subDir.mkdirs();

        Utils.writeToFile("the sha of this is ... ?", "./test1/examplefile1.txt");
        Utils.writeToFile("zomg wut are u doing. LAWL", "./test1/examplefile2.txt");
        Utils.writeToFile("LOL please dont read this.  Good job being thorough tho!", "./test1/examplefile3.txt");


        f = new File("oneFileFolder");
        f.mkdirs();
        Utils.writeToFile("my one file","./oneFileFolder/file.txt"); 

        f = new File("twoFileFolder");
        f.mkdirs();
        Utils.writeToFile("hello","./twoFileFolder/file1.txt"); 
        Utils.writeToFile("hello hello", "twoFileFolder/file2.txt");

        f = new File("emptyFolderFolder/asd");
        f.mkdirs(); 
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
    void testAddDirectoryEmpty () throws Exception
    {
        File f = new File ("emptyFolder");
        f.mkdirs(); 

        String hashCode = Tree.addDirectory("./emptyFolder");

        String contents = Utils.readFromFile("./objects/" + hashCode);


        assertTrue("tree content should be empty but is not", contents.equals(""));
        assertTrue("tree does not have correct sha", hashCode.equals("da39a3ee5e6b4b0d3255bfef95601890afd80709"));
    }

    @Test
    void testAddDirectoryOneFile () throws Exception
    {
        String hashCode = Tree.addDirectory("oneFileFolder");

        String contents = Utils.readFromFile("./objects/" + hashCode);

        assertTrue("tree content is incorrect", contents.equals("blob : 694703de9573b5183021bf045db51f07166aa524 : oneFileFolder/file.txt"));
        assertTrue("tree does not have correct sha", hashCode.equals("fb28e730bd4929762d4ac8350fc192cce965e11c"));
    }

    @Test
    void testAddDirectoryTwoFile () throws Exception
    {

        String hashCode = Tree.addDirectory("twoFileFolder");

        ArrayList<String> contents = Utils.readFromFileToArrayList("./objects/" + hashCode);

        assertTrue("tree content is incorrect", contents.get(1).equals("blob : aaf4c61ddcc5e8a2dabede0f3b482cd9aea9434d : twoFileFolder/file1.txt"));
        assertTrue("tree content is incorrect", contents.get(0).equals("blob : dccf719f8dad2d6f4d4d9c9e1eb6592ed8acbf24 : twoFileFolder/file2.txt"));

        assertTrue("tree does not have correct sha", hashCode.equals("0d465f5839abce550e09f3442288f2694fc21ecd"));
    }

    @Test
    void testAddDirectoryEmptyFolder () throws Exception
    {
        String hashCode = Tree.addDirectory("emptyFolderFolder");

        ArrayList<String> contents = Utils.readFromFileToArrayList("./objects/" + hashCode);

        assertTrue ("tree content is incorrect", contents.get(0).equals("tree : da39a3ee5e6b4b0d3255bfef95601890afd80709 : emptyFolderFolder/asd"));

        File subFolder = new File ("./objects/" + Utils.getSHAofLine(contents.get(0)));

        assertTrue("successfully added sub folder into objects", subFolder.exists());
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
        Utils.writeToFile("zomg wut are u doing. LAWL", "folder/examplefile2.txt");
        Utils.writeToFile("LOL please dont read this.  Good job being thorough tho!", "./folder/examplefile3.txt");

        Utils.writeToFile("ribbit", "./folder/test3/asd.txt");

        String hashCode = Tree.addDirectory("./folder");

        String contents = Utils.readFromFile("./objects/" + hashCode);

        assertTrue ("tree does not contain files from the sub directories", contents.contains ("tree : 06275a0db4c2fea08154ec7aeca605ca861c535f : ./folder/test3"));
    } 
}
