import java.io.*;
import java.util.*; 

public class Utils {
 
    public static String readFromFile (String file) throws IOException
    {
        BufferedReader br = new BufferedReader (new FileReader (file));

        StringBuilder sb = new StringBuilder();
        while (br.ready())
        {
            sb.append((char) br.read());
        }

        br.close();
        return sb.toString(); 
    }
}
