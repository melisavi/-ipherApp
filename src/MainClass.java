import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainClass {
    public static void main(String[] args) throws IOException, IllegalArgumentException {
        //args = "-f -a -o /some/path -p sample- in1.txt in2.txt".split(" ");
        List<String> inputFiles = new ArrayList<>();
        String outPath = new java.io.File(".").getCanonicalPath();
        String outPrefix = "";
        boolean addToExistingFiles = false;
        int statistics = 0;

        for (int i = 0; i < args.length; i++) {
            if (args[i].length() > 4 && ".txt".equals(args[i].substring(args[i].length() - 4))) {
                inputFiles.add(new java.io.File(".").getCanonicalPath() + "/" + args[i]);
            } else {
                if ("-o".equals(args[i])) {
                    outPath = new java.io.File(".") + args[++i];
                } else if ("-p".equals(args[i])) {
                    outPrefix = args[++i];
                } else if ("-a".equals(args[i])) {
                    addToExistingFiles = true;
                } else if ("-s".equals(args[i])) {
                    statistics = 1;
                } else if ("-f".equals(args[i])) {
                    statistics = 2;
                }
            }
        }

        new FileReaderByType(outPath + "/" + outPrefix, addToExistingFiles, statistics).readFiles(inputFiles);
    }
}