import java.util.*;

public class GitTester
{
    public static void main (String[] args) throws Exception
    {
        // Blob blob = new Blob ("example.txt");

        // System.out.println("finished program");
        try
        {
            Git git = new Git ();
            git.init();

            git.add("example.txt");
            
            // Blob b = new Blob("example.txt" ) ;
        }
        catch (Exception e)
        {
            throw e;
        }

    }
}