import java.io.*;
import java.security.*;


public class Blob {

    //nb: do not add multiple entries

    private String file;
    private String hashcode;

    public Blob (String path) throws Exception
    {
        try
        {
            path = file;
            //read in the file contents
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(path));
            while (br.ready())
            {
                sb.append ((char) br.read());
            }
            br.close();

            hashcode = getHashString(sb.toString());
            File newFile = new File ("./objects/" + hashcode);
            newFile.createNewFile();
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    private String getHashString (String input) throws Exception
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
}
