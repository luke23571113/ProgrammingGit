import java.util.*;
import java.io.*; 

public class Test {
    public static void main (String[] args) throws Exception
    {
        String s = "blob : alskjalkjalkjsd : file1.txt";
        System.out.println(Utils.getSHAofLine(s));
    }
}
