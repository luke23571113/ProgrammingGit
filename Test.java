import java.util.*;
import java.io.*; 

public class Test {
    public static void main (String[] args) throws Exception
    {
        // Utils.replaceLineInFile("test.txt", 1, "luke");
        // Utils.writeToFile()

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

        Index i1 = new Index();
        i1.init();
        i1.add("file1.txt");
        i1.add("file2.txt");
        Commit c1 = new Commit ("commit one", "luke");

        i1.init();
        i1.add("folder1/file5.txt");
        i1.add("folder1/file6.txt");
        Commit c2 = new Commit("commit two", "ryan", c1.getHashcode()); 
    }
}
