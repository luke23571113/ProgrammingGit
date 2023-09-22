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
            file = path; 

            //make the objects folder if it doesn't already exist
            File objects = new File ("./objects");
            objects.mkdirs();

            //read in the file contents
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(file));

            while (br.ready())
            {
                sb.append ((char) br.read());
            }
            br.close();

            //then hash it and make the file=
            hashcode = getHashFromString(sb.toString());
            File newFile = new File ("./objects/" + hashcode);
            newFile.createNewFile();
            // FileWriter writer = new FileWriter(newFile);
            // writer.write(sb.toString());
            // writer.flush();
            // writer.close();
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public String getHashcode ()
    {
        return hashcode;
    }

    //luke i made it public static
        //luke i made it public static
            //luke i made it public static
                //luke i made it public static
                    //luke i made it public static
                        //luke i made it public static
                            //luke i made it public static
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
}
