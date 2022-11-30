package ms.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {

    //read html to string
    public static String getContentFromHtmlFile(String fileName) {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            final BufferedReader in = new BufferedReader(new FileReader(fileName));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }
}
