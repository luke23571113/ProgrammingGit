import java.util.*;
import java.io.*; 

public class Test {
    public static void main (String[] args) throws Exception
    {
        //FIXME: i still think something is weird with the index clearing ... 


        /* 
        Index index = new Index(); 
        index.add("file1.txt");
        index.add("file2.txt");

        Commit c1 = new Commit("first commit!", "luke"); 

        index = new Index(); 
        index.add("file3.txt");
        index.add("file4.txt");
        Utils.writeToFile("new file 1 content", "file1.txt");
        index.edit("file1.txt");

        Commit c2 = new Commit ("second commit", "jake", c1.getHashcode());
        */

        Tree t = new Tree(); 
        t.addDirectory("folder2");

        String entry = "tree : " + t.getHashcode() + " : " + "folder2";
    }
}
