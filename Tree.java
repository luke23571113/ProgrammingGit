import java.io.File;
import java.io.FileWriter;
import java.security.MessageDigest;
import java.util.ArrayList;

public class Tree {
    ArrayList<String> values;

    public Tree() {
        values = new ArrayList<>();
    }

    private String getHashFromString(String input) throws Exception {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(input.getBytes());
            byte[] b = md.digest();

            char hexDigit[] = { '0', '1', '2', '3', '4', '5', '6', '7',
                    '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
            StringBuffer buf = new StringBuffer();
            for (int j = 0; j < b.length; j++) {
                buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
                buf.append(hexDigit[b[j] & 0x0f]);
            }
            return buf.toString();
        } catch (Exception e) {
            throw e;
        }
    }

    public void add(String input) {
        try {
            if (!(values.contains(input))) {
                values.add(input);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void remove(String input) {
        try {
            for (int i = values.size() - 1; i >= 0; i--) {
                // if (input.contains(".txt")) {
                //     if (values.get(i).substring(0, 4).equals("blob")) {
                //         if (values.contains(input)) {
                //             values.remove(i);
                //             break;
                //         }
                //     }
                // } else {
                //     if (values.get(i).substring(0, 4).equals("tree")) {
                //         if (values.contains(input)) {
                //             values.remove(i);
                //             break;
                //         }
                //     }
                // }
                if (values.get(i).contains(input))
                {
                    values.remove(i);
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public String save() throws Exception {
        try {
            // read in the file contents
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < values.size(); i++) {
                if (i < values.size() - 1) {
                    sb.append(values.get(i) + "\n");
                } else {
                    sb.append(values.get(i));
                }
            }

            // then hash it and make the file
            String hashcode = getHashFromString(sb.toString());
            File newFile = new File("./objects/" + hashcode);
            newFile.createNewFile();
            FileWriter writer = new FileWriter(newFile);
            writer.write(sb.toString());
            writer.flush();
            writer.close();

            return hashcode;
        } catch (Exception e) {
            throw e;
        }
    }

    public void addDirectory (String directoryPath) throws Exception
    {
        File dir = new File (directoryPath);
        if (!dir.isDirectory())
        {
            throw new Exception ("Invalid directory path");
        }

        File[] fileList = dir.listFiles();  //list of all the files

        for (File f : fileList) //loop through each file and add tree
        {
            String filePath = directoryPath + "/" + f.getName();
            Blob b = new Blob (filePath); //are you meant to make the files a blob? ig it can't hurt ...

            this.add("blob : " + b.getHashcode() + " : " + filePath);
        }
    }

}
