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

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*; 

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

        Utils.writeToFile("purple", "./folder2/file7.txt");
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

    // @Test
    // @DisplayName("Test create tree")
    // void testCreateTree() throws Exception {

    //     Commit commit = new Commit("summary", "ryan");
    //     String treeSha = commit.createTree();

    //     assertEquals("wrong sha created for tree", "da39a3ee5e6b4b0d3255bfef95601890afd80709", treeSha);
    //     File treeFile = new File("./objects/da39a3ee5e6b4b0d3255bfef95601890afd80709");
    //     assertTrue("tree file was not created", treeFile.exists());
    // }

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
        // assertTrue ("incorrect hash for the current commit", c1.getHashcode().equals("3d184c8a5835101c10f14afdc72dad65aebc1384")); 

    }

    @Test
    void testTwoCommits() throws Exception
    {
        Index i1 = new Index();
        i1.init();
        i1.add("file1.txt");
        i1.add("file2.txt");
        Commit c1 = new Commit ("commit one", "luke");

        i1.init();
        i1.addDirectory("./folder1");
        Commit c2 = new Commit("commit two", "ryan", c1.getHashcode()); 

        ArrayList<String> c1List = Utils.readFromFileToArrayList("./objects/" + c1.getHashcode());

        assertTrue("Incorrect hash for the commit's tree", c1List.get(0).equals("f21c3d366bbb5153c4dd35b30ed8ba28bf746817"));
        assertTrue("Incorrect hash for the previous commit", c1.lastCommit.equals(""));
        assertTrue("Incorrect hash for the next commit", c1List.get(2).equals(c2.getHashcode()));
        
        assertTrue("Incorrect hash for the commit's tree", c2.tree.equals("598ca566833c502878697379ce7c84ec2c11d7d3"));
        assertTrue("Incorrect hash for the previous commit", c2.lastCommit.equals(c1.getHashcode()));
        assertTrue("Incorrect hash for the next commit", c2.nextCommit.equals(""));

    }

    @Test
    void test4Commits () throws Exception
    {
        Index i1 = new Index();
        i1.init();
        i1.add("file1.txt");
        i1.add("file2.txt");
        Commit c1 = new Commit ("commit one", "luke");

        i1.init();
        i1.addDirectory("./folder1");
        Commit c2 = new Commit("commit two", "ryan", c1.getHashcode()); 

        i1.init();
        i1.addDirectory("./folder2");
        Commit c3 = new Commit ("commit three", "jake", c2.getHashcode()); 

        i1.init();
        i1.add("file3.txt");
        i1.add("file4.txt");
        Commit c4 = new Commit("commit four", "gpt", c3.getHashcode());

        //test tree contents 
        assertTrue("Incorrect hash for the commit's tree", c1.tree.equals("f21c3d366bbb5153c4dd35b30ed8ba28bf746817"));
        assertTrue("Incorrect hash for the commit's tree", c2.tree.equals("598ca566833c502878697379ce7c84ec2c11d7d3"));
        assertTrue("Incorrect hash for the commit's tree", c3.tree.equals("3c19b509bdb5fb7f16f033fe0e4ec5d6a37f5e39"));

        //test prev Commit & its tree
        ArrayList<String> c3Content = Utils.readFromFileToArrayList("./objects/" + c3.getHashcode());

        assertTrue ("Did not link from previous commit to current commit", c3Content.get(2).equals(c4.getHashcode()));
        assertTrue ("did not link from current commit to previous commit", c3Content.get(1).equals(c2.getHashcode()));
        String prev = Utils.readLineFromFile("./objects/" + c4.lastCommit, 0);  

        //test next Commit & its tree
        ArrayList<String> c1Content = Utils.readFromFileToArrayList("./objects/" + c1.getHashcode());
        assertTrue("Did not link to the next commit", c1Content.get(2).equals(c2.getHashcode()));
        //check to see if it updated the "tree : previouscommit" in its tree
        String c4PrevCommit = Utils.getLastWordOfString( Utils.readFromFile("./objects/" + c4.tree)); 
        assertTrue("incorrect next tree contents", c4PrevCommit.equals(c3.tree));
    }

    @Test
    void testCommitDeleteNoLink () throws Exception
    {
        Index i1 = new Index();
        i1.init(); 
        i1.add("file1.txt");
        i1.add("file3.txt");
        Commit c1 = new Commit ("first commit", "luke");

        i1.init(); 
        i1.add("file2.txt"); 
        i1.delete("file1.txt"); 

        Commit c2 = new Commit ("second commit", "ryan", c1.getHashcode());

        ArrayList<String> treeContent = Utils.readFromFileToArrayList("./objects/" + c2.tree);

        assertTrue ("did not copy over previous files", Utils.arrayListContains(treeContent,"blob : fd22cb7ca77445e0e501d5655919fa39624df7dd : file3.txt"));
        assertTrue("did not delete the tree", !Utils.arrayListContains(treeContent, "file1.txt"));
    }

    @Test
    void testCommitEditSimple () throws Exception
    {
        Index i1 = new Index();
        i1.init(); 
        i1.add("file1.txt");
        i1.add("file3.txt");
        Commit c1 = new Commit ("first commit", "luke");

        i1.init(); 
        i1.add("file2.txt"); 
        Utils.writeToFile("new content", "file1.txt"); 
        i1.edit("file1.txt");     

        Commit c2 = new Commit ("second commit", "ryan", c1.getHashcode());

        ArrayList<String> treeContent = Utils.readFromFileToArrayList("./objects/" + c2.tree);

        assertTrue("doens't contain correct blob for edited file", Utils.arrayListContains(treeContent, "blob : ca527369d9e8c1e081558bd92f90f65c4eb77e21 : file1.txt"));
        assertTrue("doesn't contain previous commits blobs", Utils.arrayListContains(treeContent,"blob : fd22cb7ca77445e0e501d5655919fa39624df7dd : file3.txt"));
    }

    @Test
    void test5CommitsAndTraversal () throws Exception {
        Index i1 = new Index();
        i1.init(); 
        i1.add("file1.txt");

        Commit c1 = new Commit ("first commit", "luke");
        ArrayList<String> c1TreeContent = Utils.readFromFileToArrayList("./objects/" + c1.tree); 


        i1.init(); 
        i1.add("file2.txt"); 

        Commit c2 = new Commit ("second commit", "ryan", c1.getHashcode());
        ArrayList<String> c2TreeContent = Utils.readFromFileToArrayList("./objects/" + c2.tree);

        i1.init(); 
        i1.add("file3.txt");
        i1.addDirectory("folder1");

        Commit c3 = new Commit ("third commity", "jake", c2.getHashcode());
        ArrayList<String> c3TreeContent = Utils.readFromFileToArrayList("./objects/" + c3.tree);

        
        i1.init();
        Utils.writeToFile("file2.txt", "updated file 2"); 
        i1.add("file4.txt");

        Commit c4 = new Commit ("fourth commity", "luke", c3.getHashcode());
        ArrayList<String> c4TreeContent = Utils.readFromFileToArrayList("./objects/" + c4.tree);

        i1.init();
        i1.delete("file4.txt");
        i1.addDirectory("folder2");

        Commit c5 = new Commit ("fifth commit", "ryan", c4.getHashcode());
        ArrayList<String> c5TreeContent = Utils.readFromFileToArrayList("./objects/" + c5.tree);

        System.out.println(); 
    }

    @Test
    @DisplayName("Test that write to objects works, which means that generate sha works")
    void testWriteToObjects() throws Exception {
        Commit commit = new Commit("summary", "ryan"); 

        // Confirm sure the hash of the file created is correct
        File file = new File("./objects/" + commit.getHashcode());
        assertTrue(file.exists());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
        LocalDateTime now = LocalDateTime.now();  
        String curDate = dtf.format(now);

        // Confirm the object file contents match what is expected
        assertEquals("da39a3ee5e6b4b0d3255bfef95601890afd80709\n" + "\n" + "\n" + "ryan\n" + curDate + "\nsummary", Files.readString(Path.of("./objects/" + commit.getHashcode())));
    }

}
