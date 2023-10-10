import java.io.*;
import java.security.MessageDigest;
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

    public static String getHashFromString (String input) throws Exception 
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(input.getBytes());
            byte[] b = md.digest();

            char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            StringBuffer buf = new StringBuffer();
            for (int j=0; j<b.length; j++) {
            buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
            buf.append(hexDigit[b[j] & 0x0f]);
            }
            return buf.toString();
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public static void writeToFile (String fileContent, String fileName)
    {
        File f = new File (fileName);
        f.createNewFile(); //does this work
    }
}
