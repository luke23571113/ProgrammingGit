import java.util.*;
import java.io.*; 

public class Test {
    public static void main (String[] args) throws Exception
    {
        Tree t = new Tree();
        t.addDirectory("./folder1");
        System.out.println(t.getHashcode());
    }
}
