package World;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class txtFileCreator {
    private String path;

    public txtFileCreator(String file_path){
        path = file_path;
    }

    public void toTxt(String text) throws IOException {
        FileWriter write = new FileWriter(path,false);
        PrintWriter line = new PrintWriter(write);
        line.printf("%s" + "%n", text);
        line.close();
    }

}
