import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
         
        return sb.toString();
    }

    public static void writeToFile (String contents, String filePath) throws Exception 
    {
        File f = new File(filePath);
        f.createNewFile();

        FileWriter fw = new FileWriter(f);
        fw.write(contents);
        fw.close();
    }

    public static ArrayList<String> readFromFileToArrayList (String fileName) throws Exception
    {
        ArrayList<String> list = new ArrayList<String>();

        BufferedReader br = new BufferedReader(new FileReader(fileName));

        while (br.ready()) 
        {
            list.add(br.readLine());
        }

        br.close(); 
        return list; 
    }

    public static String getFirstWordOfString (String s)
    {
        return s.substring(0, s.indexOf(" "));
    }

    public static String getLastWordOfString (String s)
    {
        if (s == null || s .isEmpty()) {
            return "";
        }

        String[] words = s.trim().split("\\s+");
        return words[words.length - 1];
    }

    public static String arrayListToFileText (ArrayList<String> lines) throws Exception
    {
        StringBuilder sb = new StringBuilder();
        for (String s : lines) sb.append(s + "\n");
        if (lines.size() > 0) sb.deleteCharAt(sb.length() - 1);

        return sb.toString(); 
    }

    public static void replaceLineInFile (String file, int line, String newContent) throws Exception
    {
        ArrayList<String> fileContents = Utils.readFromFileToArrayList(file);
        fileContents.remove(line);
        fileContents.add(line, newContent);
        writeToFile(arrayListToFileText(fileContents), file);
    }

    public static String readLineFromFile (String fileName, int line) throws Exception 
    {
        ArrayList<String> content = readFromFileToArrayList(fileName);
        return content.get(line);
    }

    public static String getSHAofLine (String line) throws Exception
    {
        String[] arr = line.split(" : ");
        return arr[1];
    }

    public static int getLineWhichContains (ArrayList<String> list, String s) 
    {
        for (int i = 0; i < list.size(); i++)
        {
            if (list.get(i).contains(s)) return i;
        }
        return -1; 
    }

    public static boolean arrayListContains (ArrayList<String> list, String s)
    {
        for (int i = 0; i < list.size(); i++)
        {
            if (list.get(i).contains(s)) 
            {
                return true; 
            }
        }
        return false; 
    }
}
