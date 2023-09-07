import java.util.*;
import java.io.*;
import java.security.*;


public class Blob {

    //nb: do not add multiple entries

    public Blob (String path)
    {
        try
        {
            //read in the file contents
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(path));
            while (br.ready())
            {
                sb.append ((char) br.read());
            }
            br.close();

            String hash = getHashString(sb.toString());
            File newFile = new File ("./objects/" + hash);
            newFile.createNewFile();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public String getHashString (String input)    
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
            System.out.println(e.getMessage());
            return ""; 
        }
    }
}
