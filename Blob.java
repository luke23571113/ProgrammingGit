import java.io.*;
import java.security.*;


public class Blob {

    //nb: do not add multiple entries

    private String file;
    private String hashcode;

    public Blob (String path) throws Exception
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
            hashcode = Utils.getHashFromString(sb.toString());
            File newFile = new File ("./objects/" + hashcode);
            newFile.createNewFile();

            PrintWriter pw = new PrintWriter(new BufferedWriter( new FileWriter ("./objects/" + hashcode)));
            pw.print(sb.toString());
            pw.close();
    }

    public String getHashcode ()
    {
        return hashcode;
    }

    public String getFile () 
    {
        return file; 
    }
    
}
