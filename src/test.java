import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class test {
    public static void main(String[] args) {
        // try {
        //     String filePath =  "maps/" + "fileName" + ".txt";
        //     File file = new File(filePath);
        //     file.createNewFile();
		// 	FileWriter fw = new FileWriter(filePath, true);
        //     BufferedWriter bw = new BufferedWriter(fw);
        //     bw.append("在已有的基础上添加字符串");
        //     bw.write("abc");
        //     bw.write("def");
        //     bw.write("hijk");
        //     bw.close();
        //     fw.close();
        // } catch (Exception e) {
        //     // TODO: 
        //     // Auto-generated catch block
        //     e.printStackTrace();
        // }

        String content = "Hello World \r\nJava!\r\n";

        try {
            String path = "maps/file1.txt";

            // Java 11 , default StandardCharsets.UTF_8
            Files.writeString(Paths.get(path), content);

            // encoding
            // Files.writeString(Paths.get(path), content, StandardCharsets.US_ASCII);

            // extra options
            // Files.writeString(Paths.get(path), content, 
			//		StandardOpenOption.CREATE, StandardOpenOption.APPEND);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}